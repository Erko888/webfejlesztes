package com.unideb.inf.f1manager.service.dto;

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
    private List<DriverDto> drivers;
}
