package com.example.demo.entity;

import com.example.demo.enumerator.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "institutionalized", schema = "public")
@SequenceGenerator(name = "generator_id", sequenceName = "sequence_institutionalized", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionalizedEntity extends BasicEntity {

    @NotNull
    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @NotNull
    @Column(length = 30, nullable = false)
    private String phone;

    @NotNull
    @Column(name = "birth_day", nullable = false)
    private LocalDate birthDay;

    @NotNull
    @Column(nullable = false)
    private GenderEnum gender;

}
