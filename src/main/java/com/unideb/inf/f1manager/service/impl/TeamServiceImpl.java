package com.unideb.inf.f1manager.service.impl;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.TeamService;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import com.unideb.inf.f1manager.service.mapper.TeamMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    final TeamRepository teamRepository;
    final ModelMapper modelMapper;
    final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.teamMapper = teamMapper;
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

        return teamMapper.teamEntityToDto(entities);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByName(String name) {
        teamRepository.deleteByNameIgnoreCase(name);
        teamRepository.flush();
    }

    @Override
    public TeamDto save(TeamDto teamDto) {
        if (teamDto.getId() == null){
            //SAVE
            TeamEntity entity =  modelMapper
                    .map(teamDto, TeamEntity.class);
            entity = teamRepository.save(entity);
            teamDto = modelMapper.map(entity, TeamDto.class);
            return teamDto;
        } else {
            //UPDATE
            TeamEntity e = teamRepository.getByName(teamDto.getName());

            e.setName(teamDto.getName());
            e.setDrivers(teamDto.getDrivers());

            e = teamRepository.save(e);

            return teamMapper.teamEntityToDto(e);
        }
    }
}
