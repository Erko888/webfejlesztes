package com.unideb.inf.f1manager.service.dto;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TeamDto {
    private Long id;
    private String name;
    private List<DriverEntity> drivers;
}
