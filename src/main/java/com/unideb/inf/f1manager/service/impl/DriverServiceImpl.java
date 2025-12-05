package com.unideb.inf.f1manager.service.impl;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.DriverRepository;
import com.unideb.inf.f1manager.service.DriverService;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import com.unideb.inf.f1manager.service.mapper.DriverMapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    final DriverRepository driverRepository;
    final ModelMapper modelMapper;
    final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper modelMapper, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverDto findByName(String name) {
        return modelMapper.map(driverRepository.getByName(name), DriverDto.class);
    }

    @Override
    public DriverDto findById(Long id) {
        return modelMapper.map(driverRepository.getReferenceById(id), DriverDto.class);
    }

    @Override
    public List<DriverDto> findAll() {
        List<DriverEntity> entities = driverRepository.findAll();

        return driverMapper.driverEntityToDto(entities);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByName(String name) {
        driverRepository.deleteByNameIgnoreCase(name);
        driverRepository.flush();
    }

    @Override
    public DriverDto save(DriverDto driverDto) {
        if (driverDto.getId() == null){
            //SAVE
            DriverEntity entity =  modelMapper
                    .map(driverDto, DriverEntity.class);
            entity = driverRepository.save(entity);
            driverDto = modelMapper.map(entity, DriverDto.class);
            return driverDto;
        } else {
            //UPDATE
            DriverEntity e = driverRepository.getByName(driverDto.getName());

            e.setName(driverDto.getName());
            e.setNumber(driverDto.getNumber());
            e.setTeam(driverDto.getTeam());

            e = driverRepository.save(e);

            return driverMapper.driverEntityToDto(e);
        }
    }
}
