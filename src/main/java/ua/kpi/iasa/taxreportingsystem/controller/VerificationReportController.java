package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_INSPECTOR')")
@Controller
public class VerificationReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/verification-reports")
    public String unverifiedReports(@AuthenticationPrincipal User user, Model model){
        Optional<List<Report>> reports = reportService.getVerificationReports(user);
        model.addAttribute("reports", reports.get());

        return "verification-report-list";
    }

    @GetMapping("/verification-report/{reportId}")
    public String openReportAsInspector(@PathVariable("reportId") Report report, Model model){
        model.addAttribute("report", report);
        model.addAttribute("edits", Edits.values());
        model.addAttribute("rejectionReasons", RejectionReason.values());

        return "report-validation";
    }

    @PostMapping("/check-report/{reportId}")
    public String checkReport(@PathVariable("reportId") Report report,
                              @AuthenticationPrincipal User user,
                              @RequestParam(required = false) String message,
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
            //report.setEdits(Edits.valueOf(message));
        }
        report.setInspector(user);
        reportService.saveReport(report);
        model.addAttribute("reports", reportService.getVerificationReports(user));

        return "redirect:/verification-reports";
    }
}
