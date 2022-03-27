package tqs.lab3_2cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
@TestPropertySource( locations = "application-integrationtest.properties")
public class CarControllerIT {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
    void whenPostCar_thenCreateCar() {
        Car renault = new Car("Renault", "Megane");

        restTemplate.postForEntity("/api/cars", renault, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).hasSize(1).extracting(Car::getMaker).containsOnly("Renault");
    
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        Car renault = new Car("Renault", "Megane");
        Car seat = new Car("Seat", "Ibiza");

        repository.saveAndFlush(renault);
        repository.saveAndFlush(seat);

        ResponseEntity<List<Car>> response = restTemplate.exchange(
            "/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getCarId).containsExactly(renault.getCarId(), seat.getCarId());
    }

    @Test
    void givenCars_whenGetCar_thenStatus200() {
        Car renault = new Car("Renault", "Megane");

        repository.saveAndFlush(renault);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/" + renault.getCarId(), Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(renault);
    }

    @Test
    void whenGetInvalidCar_thenStatus404() {
        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/-999", Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
