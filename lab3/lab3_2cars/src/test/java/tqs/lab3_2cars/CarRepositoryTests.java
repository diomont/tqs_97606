package tqs.lab3_2cars;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    @Test
    void whenFindCarByExistingId_thenReturnCar() {
        Car toyota = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(toyota);

        Optional<Car> found = repository.findByCarId(toyota.getCarId());
        assertThat(found).get().isEqualTo(toyota);
    }

    @Test
    void whenFindCarByInvalidId_thenReturnEmpty() {
        Optional<Car> found = repository.findByCarId(-999L);
        assertThat(found.isEmpty()).isTrue();
    }

    @Test
    void givenCars_whenFindAllCars_returnAllCars() {
        Car renault = new Car("Renault", "Megane");
        Car seat = new Car("Seat", "Ibiza");
        Car toyota = new Car("Toyota", "Corolla");

        entityManager.persist(renault);
        entityManager.persist(seat);
        entityManager.persist(toyota);
        entityManager.flush();

        List<Car> allEmployees = repository.findAll();
        assertThat(allEmployees)
            .hasSize(3)
            .extracting(Car::getCarId)
            .containsOnly(renault.getCarId(), seat.getCarId(), toyota.getCarId());
    }

}
