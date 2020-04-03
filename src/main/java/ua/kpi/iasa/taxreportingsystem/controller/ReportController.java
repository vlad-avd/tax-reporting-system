package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

@Controller
public class IndividualPersonReportController {

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public String reportList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("reports", reportRepo.findByTaxpayerId(user.getId()));

        return "report-list";
    }

    @GetMapping("/report/{report}")
    public String openReport(@PathVariable Report report, Model model){
        model.addAttribute("report", report);
        return "individual-person-report";
    }

    @PostMapping("/report/individual-person-report")
    public String individualPersonReport(@AuthenticationPrincipal User user,
                                         @RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String patronymic,
                                         @RequestParam String workplace,
                                         @RequestParam Double salary, Model model){
        Report report = new Report(name, surname, patronymic, workplace, salary);
        report.setPeriod(1);
        report.setPersonType(PersonType.INDIVIDUAL_PERSON);
        report.setTaxpayer(user);
        report.setReportStatus(ReportStatus.ON_VERIFYING);
        reportRepo.save(report);

        model.addAttribute("reports", reportRepo.findByTaxpayerId(user.getId()));

        return "report-list";
    }
}
