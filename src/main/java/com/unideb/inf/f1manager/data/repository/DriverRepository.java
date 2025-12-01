package com.unideb.inf.f1manager.data.repository;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverRepository extends JpaRepository<DriverEntity, Long>{
    List<DriverEntity> findByTeamId(Long id);
}
