package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/individual-person-report")
    public String createIndividualPersonReport(){
        return "fillIndividualPersonReport";
    }

    @GetMapping("/legal-entity-report")
    public String createLegalEntityReport(){
        return "fillLegalEntityReport";
    }
}
