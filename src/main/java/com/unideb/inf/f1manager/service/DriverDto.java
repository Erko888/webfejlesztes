package com.unideb.inf.f1manager.service;

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
public class DriverDto {

    private Long id;
    private String name;
    private int number;
    private List<DriverEntity> drivers;
}
