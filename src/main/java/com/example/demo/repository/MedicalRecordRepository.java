package com.example.demo.repository;

import com.example.demo.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, Long> {
}
