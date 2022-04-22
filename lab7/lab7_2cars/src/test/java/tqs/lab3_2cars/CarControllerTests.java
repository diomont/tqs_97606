package tqs.lab3_2cars;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;


    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car renault = new Car("Renault", "Megane");

        when(service.save(Mockito.any())).thenReturn(renault);

        mvc.perform(
            post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(renault))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.maker", is("Renault")))
        .andExpect(jsonPath("$.model", is("Megane")));

        verify(service).save(Mockito.any());
    }

    @Test
    void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car renault = new Car("Renault", "Megane");
        Car seat = new Car("Seat", "Ibiza");
        Car toyota = new Car("Toyota", "Corolla");

        List<Car> cars = Arrays.asList(renault, seat, toyota);

        when(service.getAllCars()).thenReturn(cars);

        mvc.perform(
            get("/api/cars").contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].maker", is("Renault")))
        .andExpect(jsonPath("$[0].model", is("Megane")))
        .andExpect(jsonPath("$[1].maker", is("Seat")))
        .andExpect(jsonPath("$[1].model", is("Ibiza")))
        .andExpect(jsonPath("$[2].maker", is("Toyota")))
        .andExpect(jsonPath("$[2].model", is("Corolla")));

        verify(service).getAllCars();
    }

    @Test
    void givenCars_whenGetCar_thenReturnJson() throws Exception {
        Car seat = new Car("Seat", "Ibiza");
        seat.setCarId(222L);

        when(service.getCarDetails(seat.getCarId())).thenReturn( Optional.of(seat) );

        mvc.perform(
            get("/api/cars/" + seat.getCarId()).contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.maker", is("Seat")))
        .andExpect(jsonPath("$.model", is("Ibiza")));

        verify(service).getCarDetails(seat.getCarId());
    }

    @Test
    void whenGetInvalidCar_thenReturn404() throws Exception {
        when(service.getCarDetails(Mockito.anyLong())).thenReturn(Optional.empty());

        mvc.perform(
            get("/api/cars/-999").contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());

        verify(service).getCarDetails(Mockito.anyLong());
    }

}
