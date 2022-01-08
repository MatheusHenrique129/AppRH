package com.src.system.rh.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VacancyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //vai gerar um id automaticamente (auto-incrementado)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String data;
    @NotEmpty
    private String salary;
    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE)
    private List<CandidateModel> candidates;

}
