package com.unideb.inf.f1manager.service.mapper;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDto driverEntityToDto(DriverEntity e);
    List<DriverDto> driverEntityToDto(List<DriverEntity> l);
    DriverEntity driverDtoToEntity(DriverDto d);
    List<DriverEntity> driverDtoToEntity(List<DriverDto> d);
}
