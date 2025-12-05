package com.unideb.inf.f1manager.data.repository;

import com.unideb.inf.f1manager.data.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    DriverEntity getByName(String name);
    void deleteByName(String name);
    void deleteByNameIgnoreCase(String name);
}
