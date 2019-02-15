package com.lambdaschool.javacars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepo;
    private final RabbitTemplate rTemplate;

    public CarController(CarRepository carRepo, RabbitTemplate rTemplate) {
        this.carRepo = carRepo;
        this.rTemplate = rTemplate;
    }

    @GetMapping("/id/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepo.findById(id).orElseThrow(()-> new CarNotFoundException(id));
    }

    @GetMapping("/year/{year}")
    public List<Car> getCarsByYear(@PathVariable int year) {
        return carRepo.findCarsByYear(year);
    }

    @GetMapping("/brand/{brand}")
    public List<Car> getCarsByYear(@PathVariable String brand) {
        return carRepo.findCarsByBrandIgnoreCase(brand);
    }

    @PostMapping("/upload")
    public List<Car> loadCars(@RequestBody List<Car> carsToUpload) {
        return carRepo.saveAll(carsToUpload);
    }

    @DeleteMapping("delete/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carRepo.deleteById(id);
    }

}
