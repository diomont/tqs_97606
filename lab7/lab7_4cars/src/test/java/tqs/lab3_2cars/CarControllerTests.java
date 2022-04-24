package tqs.lab3_2cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
//import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
public class CarControllerTests {

    @LocalServerPort
    private int localPort;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository repository;

    @SuppressWarnings("rawtypes")
    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:12")
        .withUsername("test")
        .withPassword("password")
        .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
    

    @BeforeEach
    void setup() {
        //standaloneSetup(new CarController());
        mockMvc(mvc);
    }


    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car renault = new Car("Renault", "Megane");

        String uri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(localPort)
            .pathSegment("api", "cars")
            .build()
            .toUriString();

        given()
            .contentType("application/json")
            .body(renault)
        .when()
            .post(uri)
        .then()
            .status(HttpStatus.CREATED)
            .body("maker", equalTo("Renault"))
            .body("model", equalTo("Megane"));
    }

    @Test
    void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car renault = new Car("Renault", "Megane");
        Car seat = new Car("Seat", "Ibiza");
        Car toyota = new Car("Toyota", "Corolla");
        List<Car> cars = Arrays.asList(renault, seat, toyota);

        repository.saveAllAndFlush(cars);

        String uri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(localPort)
            .pathSegment("api", "cars")
            .build()
            .toUriString();

        given()
        .when()
            .get(uri)
        .then()
            .status(HttpStatus.OK)
            .body("", hasSize(3))
            .body("[0].maker", equalTo("Renault"))
            .body("[0].model", equalTo("Megane"))
            .body("[1].maker", equalTo("Seat"))
            .body("[1].model", equalTo("Ibiza"))
            .body("[2].maker", equalTo("Toyota"))
            .body("[2].model", equalTo("Corolla"));
    }

    @Test
    void givenCars_whenGetCar_thenReturnJson() throws Exception {
        Car seat = new Car("Seat", "Ibiza");

        repository.saveAndFlush(seat);

        String uri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(localPort)
            .pathSegment("api", "cars", seat.getCarId().toString())
            .build()
            .toUriString();

        given()
        .when()
            .get(uri)
        .then()
            .status(HttpStatus.OK)
            .body("maker", equalTo("Seat"))
            .body("model", equalTo("Ibiza"));
    }

    @Test
    void whenGetInvalidCar_thenReturn404() throws Exception {
        String uri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(localPort)
            .pathSegment("api", "cars", "-999")
            .build()
            .toUriString();

        given()
        .when()
            .get(uri)
        .then()
            .status(HttpStatus.NOT_FOUND);
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

}
