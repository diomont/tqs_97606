package tqs.lab3_2cars;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CarService {

    private CarRepository repo;

    public CarService(CarRepository repository) {
        repo = repository;
    }

    public Car save(Car car) {
        return repo.save(car);
    }

    public List<Car> getAllCars() {
        return repo.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return repo.findByCarId(id);
    }
}
