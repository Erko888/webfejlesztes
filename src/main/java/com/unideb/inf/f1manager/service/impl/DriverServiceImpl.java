package com.unideb.inf.f1manager.service.impl;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.DriverRepository;
import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.DriverService;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import com.unideb.inf.f1manager.service.mapper.DriverMapper;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    final DriverRepository driverRepository;
    final ModelMapper modelMapper;
    final DriverMapper driverMapper;
    final TeamRepository teamRepository;

    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper modelMapper, DriverMapper driverMapper, TeamRepository teamRepository) {
        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
        this.driverMapper = driverMapper;
        this.teamRepository = teamRepository;
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
    @Transactional
    public DriverDto save(DriverDto driverDto) {
        if (driverDto == null) {
            throw new IllegalArgumentException("Driver DTO cannot be null");
        }

        // Convert DTO to Entity
        DriverEntity entity = (driverDto.getId() == null)
                 ? new DriverEntity()
                : driverRepository.findById(driverDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        // Map fields
        modelMapper.map(driverDto, entity);

        // Handle team relationship
        if (driverDto.getTeamId() != null) {
            TeamEntity team = teamRepository.findById(driverDto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found"));
            entity.setTeam(team);
        } else {
            entity.setTeam(null);
        }

        // Save and return
        entity = driverRepository.save(entity);
        return modelMapper.map(entity, DriverDto.class);
    }

}
