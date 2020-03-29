package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.iasa.taxreportingsystem.domain.IndividualPersonReport;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.repos.IndividualPersonReportRepo;

import java.util.Map;

@Controller
public class InspectorReportController {

    @Autowired
    private IndividualPersonReportRepo individualPersonReportRepo;

    @GetMapping("/verification-reports")
    public String unverifiedReports(@AuthenticationPrincipal User user, Model model){
        Iterable<IndividualPersonReport> reports = individualPersonReportRepo.findByReportStatus(ReportStatus.ON_VERIFYING);
        model.addAttribute("reports", reports);

        return "reportListForInspector";
    }

    @GetMapping("/verification-report/{reportId}")
    public String openReportAsInspector(@PathVariable("reportId") IndividualPersonReport report, Model model){
        model.addAttribute("report", report);
        model.addAttribute("edits", Edits.values());
        model.addAttribute("rejectionReasons", RejectionReason.values());

        return "checkReport";
    }

    @PostMapping("/check-report/{reportId}")
    public String checkReport(@PathVariable("reportId") IndividualPersonReport report,
                              @AuthenticationPrincipal User user,
                              @RequestParam String message,
                              @RequestParam String reportStatus, Model model){

        if( reportStatus.equals("approve") ){
            report.setReportStatus(ReportStatus.APPROVED);
        }
        else if( reportStatus.equals("reject") ){
            report.setReportStatus(ReportStatus.REJECTED);
            report.setRejectionReason(RejectionReason.valueOf(message));
        }
        else if(reportStatus.equals("sendToEdit")){
            report.setReportStatus(ReportStatus.NEED_TO_EDIT);
            report.setEdits(Edits.valueOf(message));
        }
        report.setInspector(user);
        individualPersonReportRepo.save(report);
        model.addAttribute("reports", individualPersonReportRepo.findByReportStatus(ReportStatus.ON_VERIFYING));

        return "redirect:/verification-reports";
    }
}
