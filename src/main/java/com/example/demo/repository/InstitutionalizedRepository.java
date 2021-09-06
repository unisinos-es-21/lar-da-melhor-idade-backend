package com.example.demo.repository;

import com.example.demo.entity.InstitutionalizedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionalizedRepository extends JpaRepository<InstitutionalizedEntity, Long> {
    boolean existsByCpf(String cpf);
}
