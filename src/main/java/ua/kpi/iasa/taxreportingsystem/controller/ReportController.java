package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.dto.IndividualPersonReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.LegalEntityReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.ReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.UserDTO;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/individual-person-report")
    public String createIndividualPersonReport(){
        return "create-individual-person-report";
    }

    @GetMapping("/legal-entity-report")
    public String createLegalEntityReport(){
        return "create-legal-entity-report";
    }

    @GetMapping("/report")
    public String reportList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId()).orElse(new ArrayList<>()));

        return "report-list";
    }

    @GetMapping("/report/{report}")
    public String openReport(@PathVariable Report report, Model model){
        model.addAttribute("report", report);
        return "individual-person-report";
    }

    @PostMapping("/report/individual-person-report")
    public String individualPersonReport(@AuthenticationPrincipal User user,
                                         IndividualPersonReportDTO reportDTO,
                                         Model model){

        reportDTO.setTaxpayer(user);
        try {
            reportService.createIndividualPersonReport(reportDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId()).get());

        return "report-list";
    }

    @PostMapping("/report/legal-entity-report")
    public String legalEntityReport(@AuthenticationPrincipal User user,
                                    LegalEntityReportDTO reportDTO,
                                    Model model){

        reportDTO.setTaxpayer(user);
        reportService.createLegalEntityReport(reportDTO);

        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId()).get());

        return "report-list";
    }
}
