package com.unideb.inf.f1manager.service.impl;

import com.unideb.inf.f1manager.service.TeamService;
import com.unideb.inf.f1manager.service.dto.TeamDto;

import java.util.List;

public class TeamServiceImpl implements TeamService {
    @Override
    public TeamDto findByName(String name) {
        return null;
    }

    @Override
    public TeamDto findById(Long id) {
        return null;
    }

    @Override
    public List<TeamDto> findAll() {
        return List.of();
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public TeamDto save(TeamDto teamDto) {
        return null;
    }
}
