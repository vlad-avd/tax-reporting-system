package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.IndividualPersonReport;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.repos.IndividualPersonReportRepo;

@Controller
public class ReportController {
    @Autowired
    private IndividualPersonReportRepo reportRepo;

    @Autowired IndividualPersonReportRepo individualPersonReportRepo;

    @GetMapping("/report")
    public String reportList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("reports", individualPersonReportRepo.findByTaxpayerId(user.getId()));

        return "reportList";
    }

    @GetMapping("/report/{report}")
    public String openReport(@PathVariable IndividualPersonReport report, Model model){
        model.addAttribute("report", report);
        return "individualPersonReport";
    }

    @PostMapping("/report/individual-person-report")
    public String individualPersonReport(@AuthenticationPrincipal User user,
                                         @RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String patronymic,
                                         @RequestParam String workplace,
                                         @RequestParam Double salary, Model model){
        IndividualPersonReport report = new IndividualPersonReport(name, surname, patronymic, workplace, salary);
        report.setPeriod(1);
        report.setPersonType(PersonType.INDIVIDUAL_PERSON);
        report.setTaxpayer(user);
        report.setReportStatus(ReportStatus.ON_VERIFYING);
        reportRepo.save(report);

        model.addAttribute("reports", individualPersonReportRepo.findByTaxpayerId(user.getId()));

        return "reportList";
    }

    @GetMapping("/verification-reports")
    public String unverifiedReports(@AuthenticationPrincipal User user, Model model){
        Iterable<IndividualPersonReport> reports = individualPersonReportRepo.findByReportStatus(ReportStatus.ON_VERIFYING);
        model.addAttribute("reports", reports);

        return "verificationReportList";
    }

    @GetMapping("/verification-report/{reportId}")
    public String checkReport(@PathVariable("reportId") IndividualPersonReport report, Model model){
        model.addAttribute("report", report);

        return "checkReport";
    }
}
