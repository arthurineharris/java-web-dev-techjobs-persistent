package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    public EmployerRepository getEmployerRepository() {
        return employerRepository;
    }

    public void setEmployerRepository(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @GetMapping
    public String displayAllEmployers(@RequestParam(required = false) Integer employerId, Model model) {
//
        if (employerId == null) {
//            model.addAttribute("title", "All Employers");
            model.addAttribute("employers", employerRepository.findAll());
        } else {
            Optional<Employer> result = employerRepository.findById(employerId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Employer Id:" + employerId);
            } else {
               Employer employer = result.get();
               model.addAttribute("title", "Employers in TechJobs:" + employer.getName());
               model.addAttribute("employers", employer.getJobs());
            }
        }
        return "employers/index";

    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer employer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        employerRepository.save(employer);

//        model.addAttribute("newEmployer", new Employer());
        model.addAttribute("employer", employer);
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable Integer employerId) {

        Optional optEmployer = employerRepository.findById(employerId);

        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            model.addAttribute("employer", employerRepository.findAll());
            return "employers/view";
        }
    }


}
