package tqs.lab3_2cars;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Mock(lenient = true)
    public CarRepository repository;

    @InjectMocks
    public CarService carService;

    @BeforeEach
    public void setup() {
        Car renault = new Car("Renault", "Megane");
        renault.setCarId(111L);
        Car seat = new Car("Seat", "Ibiza");
        seat.setCarId(222L);
        Car toyota = new Car("Toyota", "Corolla");
        toyota.setCarId(333L);

        List<Car> cars = Arrays.asList(renault, seat, toyota);

        when(repository.findAll()).thenReturn(cars);
        when(repository.findByCarId(renault.getCarId())).thenReturn(Optional.of(renault));
        when(repository.findByCarId(seat.getCarId())).thenReturn(Optional.of(seat));
        when(repository.findByCarId(toyota.getCarId())).thenReturn(Optional.of(toyota));
        when(repository.findByCarId(-999L)).thenReturn(Optional.empty());
    }


    @Test
    void getAllCarsTest() {
        List<Car> found = carService.getAllCars();
        assertThat(found).hasSize(3).extracting(Car::getCarId).contains(111L, 222L, 333L);
        verify(repository).findAll();
    }

    @Test
    void getCarDetailsTest() {
        Car seat = new Car("Seat", "Ibiza");
        seat.setCarId(222L);
        Car found = carService.getCarDetails(seat.getCarId()).get();
        assertThat(found).isEqualTo(seat);
        verify(repository).findByCarId(seat.getCarId());
    }

    @Test
    void getInvalidCarDetailsTest() {
        boolean found = carService.getCarDetails(-999L).isPresent();
        assertThat(found).isFalse();
        verify(repository).findByCarId(Mockito.anyLong());
    }

}
