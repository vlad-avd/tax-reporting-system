package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchReportException;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_INSPECTOR')")
@Controller
@RequestMapping("/verification-report")
public class VerificationReportController {

    private final ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public VerificationReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ExceptionHandler(NoSuchReportException.class)
    public String handleNoSuchReportException(NoSuchReportException exception) {
        logger.error(exception.getMessage() + exception);
        return "redirect:/error";
    }

    @GetMapping()
    public String unverifiedReports(@AuthenticationPrincipal User user,
                                    Model model,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 8) Pageable pageable) throws NoSuchReportException {

        model.addAttribute("reports", reportService.getVerificationReports(user.getId(), pageable).orElseThrow(() -> new NoSuchReportException("Reports were not found")));
        model.addAttribute("url", "/verification-report");

        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " got verification reports.");

        return "verification-report-list";
    }

    @GetMapping("/{reportId}")
    public String openReportAsInspector(@PathVariable("reportId") Report report, Model model){
        model.addAttribute("report", report);
        model.addAttribute("rejectionReasons", RejectionReason.values());

        return "report-validation";
    }

    @PostMapping("/{reportId}")
    public String checkReport(@PathVariable("reportId") Report report,
                              @AuthenticationPrincipal User user,
                              @RequestParam String reportStatus,
                              @RequestParam(required = false) String rejectionReason,
                              @RequestParam(required = false) String comment,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 7) Pageable pageable,
                              Model model){

        if(!rejectionReason.isEmpty()) {
            report.setRejectionReason(RejectionReason.valueOf(rejectionReason));
        }
        if(!comment.isEmpty()) {
            report.setComment(comment);
        }
        if(reportStatus.equals("approve") ){
            report.setReportStatus(ReportStatus.APPROVED);
            reportService.moveReportToArchive(report);
            logger.debug("Report: " + report + " sent to archive.");
        }
        else if(reportStatus.equals("reject") ){
            report.setReportStatus(ReportStatus.REJECTED);
            reportService.moveReportToArchive(report);
            logger.debug("Report: " + report + " sent to archive.");
        }
        else if(reportStatus.equals("sendToEdit")){
            report.setReportStatus(ReportStatus.NEED_TO_EDIT);
            reportService.saveReport(report);
            logger.debug("Report: " + report + " has been updated by " + user);
        }
        model.addAttribute("reports", reportService.getVerificationReports(user.getId(), pageable));

        return "redirect:/verification-report";
    }
}
