package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    @GetMapping("/report")
    public String reportList(){
        return "reportList";
    }
}
