package tqs.hw.covidtracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TrackerRestControllerTemplateIT {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void whenGetIncidence_thenReturnData_thenStatus200() {
        ResponseEntity<IncidenceData> response = restTemplate
            .exchange("/api/v1/incidence", HttpMethod.GET, null, new ParameterizedTypeReference<IncidenceData>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(IncidenceData.class);
    }

    @Test
    void whenGetIncidenceWithIso_thenReturnData_thenStatus200() {
        ResponseEntity<IncidenceData> response = restTemplate
            .exchange("/api/v1/incidence?iso=PRT", HttpMethod.GET, null, new ParameterizedTypeReference<IncidenceData>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(IncidenceData.class);
    }

    @Test
    void whenGetIncidenceWithDate_thenReturnDataForDate_thenStatus200() {
        ResponseEntity<IncidenceData> response = restTemplate
            .exchange("/api/v1/incidence?date=2022-04-15", HttpMethod.GET, null, new ParameterizedTypeReference<IncidenceData>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(IncidenceData.class);
        assertThat(response.getBody().getDay()).isEqualTo("2022-04-15");
    }

    @Test
    void whenGetIncidenceWithStartDateLaterThanEndDate_thenStatus400() {
        ResponseEntity<IncidenceData> response = restTemplate
            .exchange("/api/v1/incidence?start=2022-04-15&end=2022-04-03", HttpMethod.GET, null, new ParameterizedTypeReference<IncidenceData>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenGetCacheStats_thenReturnStats_thenStatus200() {
        ResponseEntity<Map<String, String>> response = restTemplate
            .exchange("/api/v1/cache_stats", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, String>>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKeys("requests", "hits", "misses");
    }

}
