package com.unideb.inf.f1manager.controller;

import com.unideb.inf.f1manager.data.repository.TeamRepository;
import com.unideb.inf.f1manager.service.TeamService;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamRepository teamRepository, TeamService teamService) {
        this.teamService = teamService;
    }
    @GetMapping
    public List<TeamDto> getTeams() {
        return teamService.findAll();
    }
    @GetMapping("/{id}")
    TeamDto getTeamById(@PathVariable Long id) {
        return teamService.findById(id);
    }

    @GetMapping("/byName")
    TeamDto getTeamByName(@RequestParam String name) {
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

    @PutMapping("/update/{id}")
    TeamDto update(@RequestBody TeamDto teamDto,  @PathVariable Long id) {
        return teamService.save(teamDto);
    }

}
