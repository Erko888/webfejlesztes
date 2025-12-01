package com.unideb.inf.f1manager.service.mapper;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDto teamEntityToDto(TeamEntity e);
    List<TeamDto> teamEntityToDto(List<TeamEntity> l);
    TeamEntity teamDtoToEntity(TeamDto t);
    List<TeamEntity> teamDtoToEntity(List<TeamDto> t);
}
