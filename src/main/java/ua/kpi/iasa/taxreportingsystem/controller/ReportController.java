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
import ua.kpi.iasa.taxreportingsystem.dto.ReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.UserDTO;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public String reportList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId()));

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
                                         @RequestParam("taxPeriodFrom")
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taxPeriodFrom,
                                         @RequestParam("taxPeriodTo")
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taxPeriodTo, Model model){

        reportDTO.setTaxPeriodFrom(taxPeriodFrom);
        reportDTO.setTaxPeriodTo(taxPeriodTo);
        reportDTO.setTaxpayer(user);
        reportService.createIndividualPersonReport(reportDTO);

        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId()));

        return "report-list";
    }
}
