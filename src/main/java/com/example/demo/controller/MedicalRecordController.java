package com.example.demo.controller;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.entity.MedicalRecordEntity;
import com.example.demo.enumerator.ReasonEnum;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<MedicalRecordEntity> findAll(Pageable pageable,
                                             @RequestParam(required = false, value = "id") Long id,
                                             @RequestParam(required = false, value = "institutionalizedName") String institutionalizedName,
                                             @RequestParam(required = false, value = "institutionalizedId") Long institutionalizedId,
                                             @RequestParam(required = false, value = "medicalAppointmentDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate medicalAppointmentDate,
                                             @RequestParam(required = false, value = "responsible") String responsible,
                                             @RequestParam(required = false, value = "reason") ReasonEnum reason,
                                             @RequestParam(required = false, value = "cid") String cid) {
        Page<MedicalRecordEntity> page = medicalRecordService.findAll(pageable, id, institutionalizedName, institutionalizedId, medicalAppointmentDate, responsible, reason, cid);
        if (CollectionUtils.isEmpty(page.getContent())) {
            throw new RecordsNotFoundException();
        } else {
            return page;
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecordEntity create(@Valid @RequestBody MedicalRecordEntity institutionalized) {
        return medicalRecordService.create(institutionalized);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MedicalRecordEntity findOne(@PathVariable Long id) {
        return medicalRecordService.findOne(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MedicalRecordEntity update(@PathVariable Long id, @Valid @RequestBody MedicalRecordEntity institutionalized) {
        return medicalRecordService.update(id, institutionalized);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        medicalRecordService.delete(id);
    }

}
