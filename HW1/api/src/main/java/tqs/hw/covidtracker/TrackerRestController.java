package tqs.hw.covidtracker;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TrackerRestController {

    @Autowired
    private ApiRequestService apiService;

    final static Logger logger = LoggerFactory.getLogger(TrackerRestController.class);

    @GetMapping(path = "/incidence")
    public ResponseEntity<IncidenceData> getCovidData(
        @RequestParam(name = "country") Optional<String> countryName,
        @RequestParam(name = "iso")     Optional<String> countryIso,
        @RequestParam(name = "start")   Optional<String> startDate,
        @RequestParam(name = "end")     Optional<String> endDate,
        @RequestParam(name = "date")    Optional<String> date
    ) {
        logger.info("GET request for /incidence");
        IncidenceData response = null;

        try {
            if (date.isPresent()) {
                response = getDataForDay(countryIso, date.get());
            }
            else if (startDate.isPresent() && endDate.isPresent()) {
                LocalDate startAsLocalDate = LocalDate.parse(startDate.get());
                LocalDate endAsLocalDate = LocalDate.parse(endDate.get());
                if (startAsLocalDate.isAfter(endAsLocalDate))
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                
                response = getDataForPeriod(countryIso, startAsLocalDate, endAsLocalDate);
            }
            else {
                response = getLatestData(countryIso);
            }

            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/regions")
    public ResponseEntity<List<String[]>> getRegions() {
        logger.info("GET request for /regions");
        return new ResponseEntity<>(apiService.getRegions(), HttpStatus.OK);
    }

    @GetMapping(path = "/cache_stats")
    public ResponseEntity<Map<String, Long>> getCacheStats() {
        logger.info("GET request for /cache_stats");
        return new ResponseEntity<>(apiService.getCacheStats(), HttpStatus.OK);
    }


    private IncidenceData getLatestData(Optional<String> countryIso) {
        Optional<IncidenceData> res;
        if (countryIso.isPresent())
            res = apiService.getLatestCountryDataByIso(countryIso.get());
        else
            res = apiService.getLatestGlobalData();

        if (res.isPresent())
            return res.get();
        else
            return null;
    }

    private IncidenceData getDataForDay(Optional<String> countryIso, String datestring) throws DateTimeParseException {
        Optional<IncidenceData> res;
        if (countryIso.isPresent())
            res = apiService.getCountryDataForDayByIso(countryIso.get(), LocalDate.parse(datestring));
        else
            res = apiService.getGlobalDataForDay(LocalDate.parse(datestring));
        if (res.isPresent())
            return res.get();
        else
            return null;
    }

    private IncidenceData getDataForPeriod(Optional<String> countryIso, LocalDate startDate, LocalDate endDate) {
        List<IncidenceData> res;
        if (countryIso.isPresent())
            res = apiService.getCountryDataForPeriodByIso(countryIso.get(), startDate, endDate);
        else
            res = apiService.getGlobalDataForPeriod(startDate, endDate);

        // update the last entry of the list with the sum of all difference stats 
        for (int i = 0; i < res.size()-1; i++) {
            res.get(res.size()-1).setNewCases      (res.get(res.size()-1).getNewCases()     + res.get(i).getNewCases());
            res.get(res.size()-1).setNewActiveCases(res.get(res.size()-1).getActiveCases()  + res.get(i).getActiveCases());
            res.get(res.size()-1).setNewDeaths     (res.get(res.size()-1).getNewDeaths()    + res.get(i).getNewDeaths());
            res.get(res.size()-1).setNewRecovered  (res.get(res.size()-1).getNewRecovered() + res.get(i).getNewRecovered());
        }
        if (res.isEmpty())
            return null;
        else
            return res.get(res.size()-1);
    }

}
