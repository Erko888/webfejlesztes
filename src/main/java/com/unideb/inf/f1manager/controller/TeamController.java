package com.unideb.inf.f1manager.controller;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    @GetMapping
    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }
    @GetMapping("/{id}")
    public TeamDto getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id);
    }

}
