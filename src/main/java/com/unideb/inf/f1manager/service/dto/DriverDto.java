package com.unideb.inf.f1manager.service.dto;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DriverDto {
    private Long id;
    private String name;
    private int number;
    private TeamEntity team;
}
