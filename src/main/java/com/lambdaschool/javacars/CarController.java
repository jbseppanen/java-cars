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

    @GetMapping() //  For testing.
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    @GetMapping("/id/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepo.findById(id).orElseThrow();
    }


    @PostMapping("/upload")
    public List<Car> loadCars(@RequestBody List<Car> carsToUpload) {
        return carRepo.saveAll(carsToUpload);
    }
}
