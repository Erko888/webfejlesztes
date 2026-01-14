package com.unideb.inf.f1manager.service.impl;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.DriverRepository;
import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.TeamService;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import com.unideb.inf.f1manager.service.mapper.DriverMapper;
import com.unideb.inf.f1manager.service.mapper.TeamMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    final TeamRepository teamRepository;
    final ModelMapper modelMapper;
    final TeamMapper teamMapper;
    final DriverRepository driverRepository;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, TeamMapper teamMapper, DriverRepository driverRepository) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.teamMapper = teamMapper;

        this.driverRepository = driverRepository;
    }

    @Override
    public TeamDto findByName(String name) {
        return modelMapper.map(teamRepository.getByName(name), TeamDto.class);
    }

    @Override
    public TeamDto findById(Long id) {
        return modelMapper.map(teamRepository.getReferenceById(id), TeamDto.class);
    }

    @Override
    public List<TeamDto> findAll() {
        List<TeamEntity> entities = teamRepository.findAll();

        return entities.stream()
                .map(this::mapTeamWithDrivers) // Use the helper method
                .collect(Collectors.toList());
    }

    // Helper method to manually map the team AND its drivers
    private TeamDto mapTeamWithDrivers(TeamEntity entity) {
        if (entity == null) return null;

        // 1. Map the basic Team fields (id, name)
        TeamDto dto = modelMapper.map(entity, TeamDto.class);

        // 2. Explicitly map the list of drivers to ensure it appears
        if (entity.getDrivers() != null) {
            List<DriverDto> driverDtos = entity.getDrivers().stream()
                    .map(driver -> modelMapper.map(driver, DriverDto.class))
                    .collect(Collectors.toList());
            dto.setDrivers(driverDtos);
        }

        return dto;
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByName(String name) {
        teamRepository.deleteByNameIgnoreCase(name);
        teamRepository.flush();
    }

    @Override
    @Transactional
    public TeamDto save(TeamDto teamDto) {
        if (teamDto == null) {
            throw new IllegalArgumentException("Team DTO cannot be null");
        }

        TeamEntity entity = (teamDto.getId() == null)
                ? new TeamEntity()
                : teamRepository.findById(teamDto.getId())
                .orElse(new TeamEntity());

        entity.setName(teamDto.getName());
        // Save the team first to get an ID if it's new
        entity = teamRepository.save(entity);

        // Handle drivers
        if (teamDto.getDrivers() != null) {
            entity.getDrivers().clear();

            if (teamDto.getDrivers() != null) {
                for (DriverDto driverDto : teamDto.getDrivers()) {
                    DriverEntity driver = new DriverEntity();
                    if (driverDto.getId() != null) {
                        // If driver has ID, fetch existing driver
                        driver = driverRepository.findById(driverDto.getId())
                                .orElse(new DriverEntity());
                    }
                    driver.setName(driverDto.getName());
                    driver.setNumber(driverDto.getNumber());
                    driver.setTeam(entity);
                    entity.getDrivers().add(driver);
                }
            }
        }
        entity = teamRepository.save(entity);
        return teamMapper.teamEntityToDto(entity);
    }

}
