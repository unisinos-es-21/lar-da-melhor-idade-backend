package com.example.demo.service;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.exception.*;
import com.example.demo.repository.InstitutionalizedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstitutionalizedService {

    private final InstitutionalizedRepository institutionalizedRepository;

    public List<InstitutionalizedEntity> findAll() {
        return institutionalizedRepository.findAll();
    }

    public InstitutionalizedEntity create(InstitutionalizedEntity institutionalized) {
        if (Objects.nonNull(institutionalized.getId())) {
            throw new CreateEntityIdNotNullException();
        }
        if (institutionalizedRepository.existsByCpf(institutionalized.getCpf())) {
            throw new CpfDuplicatedException(institutionalized.getCpf());
        }
        return institutionalizedRepository.save(institutionalized);
    }

    public InstitutionalizedEntity update(Long id, InstitutionalizedEntity institutionalized) {
        if (Objects.isNull(institutionalized.getId())) {
            throw new UpdateEntityIdNullException();
        }
        if (institutionalizedRepository.existsById(institutionalized.getId())) {
            throw new RecordsNotFoundException(institutionalized.getId());
        }
        if (!id.equals(institutionalized.getId())) {
            throw new DifferentIDException();
        }
        return institutionalizedRepository.save(institutionalized);
    }

    public void delete(Long id) {
        if (institutionalizedRepository.existsById(id)) {
            throw new RecordsNotFoundException(id);
        }
        institutionalizedRepository.deleteById(id);
    }

}
