package com.src.system.rh.reposity;

import com.src.system.rh.models.VacancyModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VacancyRepository extends CrudRepository<VacancyModel, String> {
    VacancyModel findById(long id);
    List<VacancyModel> findByName(String name);
}
