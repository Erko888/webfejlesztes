package com.unideb.inf.f1manager.controller;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.TeamService;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamRepository teamRepository, TeamService teamService) {
        this.teamService = teamService;
    }
    @GetMapping("/{id}")
    TeamDto getTeamById(@PathVariable Long id) {
        return teamService.findById(id);
    }

    @GetMapping("/byName/{name}")
    TeamDto getTeamByName(@PathVariable String name) {
        return teamService.findByName(name);
    }

    @PostMapping("/save")
    TeamDto save(@RequestBody TeamDto teamDto) {
        return teamService.save(teamDto);
    }

    @DeleteMapping("/deleteByName")
    void deleteByName(@RequestParam String name) {
        teamService.deleteByName(name);
    }

    @PostMapping("/update")
    TeamDto update(@RequestBody TeamDto teamDto) {
        return  teamService.save(teamDto);
    }

}
