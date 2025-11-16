package com.unideb.inf.f1manager.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "teams")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, name = "name")
    private String name;
    @OneToMany
    @Column(name = "drivers")
    private List<DriverEntity> drivers;
}
