package com.unideb.inf.f1manager.service;

import com.unideb.inf.f1manager.service.dto.DriverDto;

import java.util.List;

public interface DriverService {
    DriverDto findByName(String name);
    DriverDto findById(Long id);
    List<DriverDto> findAll();
    void deleteByName(String name);
    DriverDto save(DriverDto driverDto);
}
