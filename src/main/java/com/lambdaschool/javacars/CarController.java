package com.lambdaschool.javacars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
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
        CarLog message = new CarLog("Search for " + brand);
        rTemplate.convertAndSend(JavaCarsApplication.QUEUE_NAME_LOG, message.toString());
        return carRepo.findCarsByBrandIgnoreCase(brand);
    }

    @PostMapping("/upload")
    public List<Car> loadCars(@RequestBody List<Car> carsToUpload) {
        CarLog message = new CarLog("Data Loaded");
        rTemplate.convertAndSend(JavaCarsApplication.QUEUE_NAME_LOG, message.toString());
        return carRepo.saveAll(carsToUpload);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {
        carRepo.deleteById(id);
        CarLog message = new CarLog(id + " Data deleted");
        rTemplate.convertAndSend(JavaCarsApplication.QUEUE_NAME_LOG, message.toString());
        return ResponseEntity.noContent().build();
    }
}
