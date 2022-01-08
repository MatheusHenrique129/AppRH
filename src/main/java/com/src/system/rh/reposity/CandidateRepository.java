package com.src.system.rh.reposity;

import com.src.system.rh.models.CandidateModel;
import com.src.system.rh.models.VacancyModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidateRepository extends CrudRepository<CandidateModel, String> {

    Iterable<CandidateModel> findByVacancy(VacancyModel vacancy);

    CandidateModel findByRg(String rg);

    CandidateModel findById(long id);

    List<CandidateModel> findByName(String name);
}
