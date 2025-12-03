package com.unideb.inf.f1manager.data.repository;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
}
