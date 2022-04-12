package tqs.hw.covidtracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TrackerRestController {

    @GetMapping(path = "/")
    public ResponseEntity<IncidenceData> getDataOnCountry() {

        return null;
    }
}
