package com.unideb.inf.f1manager.controller;

import com.unideb.inf.f1manager.service.DriverService;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @GetMapping("/{id}")
    DriverDto getTeamById(@PathVariable Long id) {
        return driverService.findById(id);
    }

    @GetMapping("/byName/{name}")
    DriverDto getTeamByName(@PathVariable String name) {
        return driverService.findByName(name);
    }

    @PostMapping("/save")
    DriverDto save(@RequestBody DriverDto driverDto) {
        return driverService.save(driverDto);
    }

    @DeleteMapping("/deleteByName")
    void deleteByName(@RequestParam String name) {
        driverService.deleteByName(name);
    }

    @PostMapping("/update")
    DriverDto update(@RequestBody DriverDto driverDto) {
        return  driverService.save(driverDto);
    }
}
