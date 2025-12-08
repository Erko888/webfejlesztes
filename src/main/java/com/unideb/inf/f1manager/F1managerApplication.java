package com.unideb.inf.f1manager;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import com.unideb.inf.f1manager.data.entity.TeamEntity;
import com.unideb.inf.f1manager.service.dto.DriverDto;
import com.unideb.inf.f1manager.service.dto.TeamDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class F1managerApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1managerApplication.class, args);
	}
    @Bean
    ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        // Configure Team mapping
        modelMapper.typeMap(TeamEntity.class, TeamDto.class)
                .addMappings(mapper -> {
                    mapper.map(TeamEntity::getDrivers, TeamDto::setDrivers);
                });

        // Configure Driver mapping
        modelMapper.typeMap(DriverEntity.class, DriverDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getTeam().getId(), DriverDto::setTeamId);
                });

        return modelMapper;
    }

}
