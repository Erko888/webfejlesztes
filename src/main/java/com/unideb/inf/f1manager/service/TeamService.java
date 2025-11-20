package com.unideb.inf.f1manager.service;

import com.unideb.inf.f1manager.service.dto.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto findByName(String name);
    TeamDto findById(Long id);
    List<TeamDto> findAll();
    void deleteByName(String name);
    TeamDto save(TeamDto teamDto);
}
