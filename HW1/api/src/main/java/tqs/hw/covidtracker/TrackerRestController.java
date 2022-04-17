package tqs.hw.covidtracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    @GetMapping(path = "/incidence")
    public ResponseEntity<IncidenceData> getCovidData(
        @RequestParam(name = "country") Optional<String> countryName,
        @RequestParam(name = "iso")     Optional<String> countryIso,
        @RequestParam(name = "start")   Optional<String> startDate,
        @RequestParam(name = "end")     Optional<String> endDate,
        @RequestParam(name = "date")    Optional<String> date
    ) {

        IncidenceData response = null;

        if (date.isPresent()) {
            Optional<IncidenceData> res;
            if (countryIso.isPresent())
                res = apiService.getCountryDataForDayByIso(countryIso.get(), LocalDate.parse(date.get()));
            else
                res = apiService.getGlobalDataForDay(LocalDate.parse(date.get()));
            if (res.isPresent()) response = res.get();
        }
        else if (startDate.isPresent() && endDate.isPresent()) {
            LocalDate startAsLocalDate = LocalDate.parse(startDate.get());
            LocalDate endAsLocalDate = LocalDate.parse(endDate.get());
            if (startAsLocalDate.isAfter(endAsLocalDate))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
            List<IncidenceData> res;
            if (countryIso.isPresent())
                res = apiService.getCountryDataForPeriodByIso(countryIso.get(), startAsLocalDate, endAsLocalDate);
            else
                res = apiService.getGlobalDataForPeriod(startAsLocalDate, endAsLocalDate);

            // update the last entry of the list with the sum of all difference stats 
            for (int i = 0; i < res.size()-1; i++) {
                res.get(res.size()-1).setNewCases      (res.get(res.size()-1).getNewCases()     + res.get(i).getNewCases());
                res.get(res.size()-1).setNewActiveCases(res.get(res.size()-1).getActiveCases()  + res.get(i).getActiveCases());
                res.get(res.size()-1).setNewDeaths     (res.get(res.size()-1).getNewDeaths()    + res.get(i).getNewDeaths());
                res.get(res.size()-1).setNewRecovered  (res.get(res.size()-1).getNewRecovered() + res.get(i).getNewRecovered());
            }
            if (!res.isEmpty()) response = res.get(res.size()-1);
        }
        else {
            Optional<IncidenceData> res;
            if (countryIso.isPresent())
                res = apiService.getLatestCountryDataByIso(countryIso.get());
            else
                res = apiService.getLatestGlobalData();

            if (res.isPresent()) response = res.get();
        }

        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/cache_stats")
    public ResponseEntity<Map<String, String>> getCacheStats() {
        return null;
    }

}
