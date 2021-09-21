package com.example.demo.entity;

import com.example.demo.enumerator.ReasonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "medical_record", schema = "public")
@SequenceGenerator(name = "generator_id", sequenceName = "sequence_medical_record", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordEntity extends BasicEntity {

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "institutionalized_id", nullable = false)
    private InstitutionalizedEntity institutionalized;

    @NotNull
    @Column(nullable = false, name = "medical_appointment_date")
    private LocalDate medicalAppointmentDate;

    @Column(length = 100)
    private String responsible;

    @NotNull
    @Column(nullable = false)
    private ReasonEnum reason;

    @NotNull
    @Column(length = 400, nullable = false)
    private String anamnesis;

    @Column(length = 200, name = "diagnostic_hypotheses")
    private String diagnosticHypotheses;

    @Column(length = 200, name = "definitive_diagnosis")
    private String definitiveDiagnosis;

    @NotNull
    @Column(nullable = false, name = "infectious_disease_carrier")
    private boolean infectiousDiseaseCarrier;

    @Column(length = 200, name = "infectious_disease_description")
    private String infectiousDiseaseDescription;

    @Column(length = 10)
    private String cid;

}