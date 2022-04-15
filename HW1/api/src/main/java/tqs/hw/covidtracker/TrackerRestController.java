package tqs.hw.covidtracker;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping(path = "/")
    public ResponseEntity<IncidenceData> getCovidData(
        @RequestParam(name = "country") Optional<String> countryName,
        @RequestParam(name = "iso")     Optional<String> countryIso,
        @RequestParam(name = "start")   Optional<String> startDate,
        @RequestParam(name = "end")     Optional<String> endDate,
        @RequestParam(name = "date")    Optional<String> date
    ) {
        return null;
    }

    @GetMapping(path = "/cache_stats")
    public ResponseEntity<Map<String, String>> getCacheStats() {
        return null;
    }

}
