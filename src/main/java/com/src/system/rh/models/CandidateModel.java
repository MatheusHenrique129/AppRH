package com.src.system.rh.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CandidateModel {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String rg;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @ManyToOne
    private VacancyModel vacancy;
}
