package com.src.system.rh.controllers;

import com.src.system.rh.models.CandidateModel;
import com.src.system.rh.models.VacancyModel;
import com.src.system.rh.reposity.CandidateRepository;
import com.src.system.rh.reposity.VacancyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class VacancyController {

    private VacancyRepository vacancyRepository;
    private CandidateRepository candidateRepository;

    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
    public String form() {
        return "vacancy/formVacancy";
    }

    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST)
    public String form(@Valid VacancyModel vacancy, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarVaga";
        }
        vacancyRepository.save(vacancy);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");

        return "redirect:/cadastrarVaga";
    }

    @RequestMapping(value = "/vagas")
    public ModelAndView listVacancy() {
        ModelAndView modelAndView = new ModelAndView("vacancy/listVacancy");
        Iterable<VacancyModel> vacancy = vacancyRepository.findAll();
        modelAndView.addObject("vagas", vacancy);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView vacancyDetails(@PathVariable("id") long id) {
        VacancyModel vacancy = vacancyRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("vacancy/vacancyDetails");
        modelAndView.addObject("vacancy", vacancy);
        Iterable<CandidateModel> candidate = candidateRepository.findByVacancy(vacancy);
        return modelAndView;
    }

    @RequestMapping("/deletarVaga")
    public String VacancyDelete(long id) {
        VacancyModel vacancy = vacancyRepository.findById(id);
        vacancyRepository.delete(vacancy);
        return "redirect:/vagas";
    }

    public String vacancyDetailsPost(@PathVariable("id") long id, @Valid CandidateModel candidate,
                                     BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("messagem", "Verifique os campos...");
            return "redirect:/{id}";
        }

        if (candidateRepository.findByRg(candidate.getRg()) != null) {
            attributes.addFlashAttribute("messagem", "RG duplicado");
            return "redirect:/{id}";
        }

        VacancyModel vacancy = vacancyRepository.findById(id);
        candidate.setVacancy(vacancy);
        candidateRepository.save(candidate);
        attributes.addFlashAttribute("messagem", "Candidato adicionado com sucesso!");
        return "redirect:/{id}";
    }

    @RequestMapping("/deletarCandidato")
    public String CandidateDelete(String rg) {
        CandidateModel candidate = candidateRepository.findByRg(rg);
        VacancyModel vacancy = candidate.getVacancy();
        String id = "" + vacancy.getId();

        candidateRepository.delete(candidate);

        return "redirect:/" + id;
    }

    @RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
    public ModelAndView editVacancy(long id) {
        VacancyModel vacancy = vacancyRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("vacancy/update-vacancy");
        modelAndView.addObject("vacancy", vacancy);
        return modelAndView;
    }

    @RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
    public String updateVacancy(@Valid VacancyModel vacancy, BindingResult result, RedirectAttributes attributes) {
        vacancyRepository.save(vacancy);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

        long idLong = vacancy.getId();
        String id = "" + idLong;
        return "redirect:/" + id;
    }
}