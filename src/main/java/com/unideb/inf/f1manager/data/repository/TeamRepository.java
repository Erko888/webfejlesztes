package com.unideb.inf.f1manager.data.repository;

import com.unideb.inf.f1manager.data.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    TeamEntity getByName(String name);
    void deleteByNameIgnoreCase(String name);
}
