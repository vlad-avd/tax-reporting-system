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
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchReportException;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;

/**
 * Class controller handles inspector actions with verification reports.
 * (All verification-report/** mappings).
 * @author Vladyslav Avdiienko
 * @version 1.0
 */
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_INSPECTOR')")
@Controller
@RequestMapping("/verification-report")
public class VerificationReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    /** Constructor of the controller that initializes the services.
     * @param reportService Service that processes a table with reports.
     */
    @Autowired
    public VerificationReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ExceptionHandler(NoSuchReportException.class)
    public String handleNoSuchReportException(NoSuchReportException exception) {
        logger.error(exception.getMessage() + exception);
        return "redirect:/error";
    }

    /** Returns list of all reports to be verified.
     * @exception NoSuchReportException if report was not found.
     * @return Name of the file representing list of verification reports.
     */
    @GetMapping()
    public String getReportListToVerify(@AuthenticationPrincipal User user,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 8) Pageable pageable,
                                    Model model) throws NoSuchReportException {

        model.addAttribute("reports",
                reportService.getVerificationReports(user.getId(), pageable)
                            .orElseThrow(() -> new NoSuchReportException("Reports were not found")));

        model.addAttribute("url", "/verification-report");

        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " got verification reports.");

        return "verification-report-list";
    }

    /** Return report data.
     * @param report Report(id) to be opened.
     * @return Name of the file representing the report verification page.
     */
    @GetMapping("/{reportId}")
    public String getReportToVerify(@PathVariable("reportId") Report report, Model model){
        model.addAttribute("report", report);
        model.addAttribute("rejectionReasons", RejectionReason.values());

        return "report-validation";
    }

    /** Report verification. Set report status. Also rejection reason and comment if necessary.
     * After acceptance or rejection, sending to the archive.
     * @param report Report(id) to be verified.
     * @param reportStatus Report status reason is indicated by the inspector.
     * @param rejectionReason Rejection reason is indicated by the inspector.
     * @param comment Comment is indicated by the inspector.
     */
    @PostMapping("/{reportId}")
    public String verifyReport(@PathVariable("reportId") Report report,
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

        reportService.verifyReport(report, reportStatus);

        model.addAttribute("reports", reportService.getVerificationReports(user.getId(), pageable));
        logger.debug("Report has been verified by " + user + " with status" + reportStatus);

        return "redirect:/verification-report";
    }
}
