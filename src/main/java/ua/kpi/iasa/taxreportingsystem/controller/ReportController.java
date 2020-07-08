package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.dto.ReportValidationResultDto;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchReportException;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchUserException;
import ua.kpi.iasa.taxreportingsystem.service.ArchiveService;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;
import ua.kpi.iasa.taxreportingsystem.util.ArchiveReportConverter;

import java.util.Optional;

/**
 * Class controller handles user actions with reports.(All report/** mappings).
 * @author Vladyslav Avdiienko
 * @version 1.0
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;
    private final ArchiveService archiveService;

    /** Constructor of the controller that initializes the services.
     * @param reportService Service that processes a table with reports.
     * @param archiveService Service that processes a table with archives.
     */
    @Autowired
    public ReportController(ReportService reportService, ArchiveService archiveService) {
        this.reportService = reportService;
        this.archiveService = archiveService;
    }

    @ExceptionHandler(NoSuchReportException.class)
    public String handleNoSuchReportException(NoSuchReportException exception) {
        logger.error(exception.getMessage() + exception);
        return "redirect:/error";
    }

    @ExceptionHandler(NoSuchUserException.class)
    public String handleNoSuchUserException(NoSuchUserException exception) {
        logger.error(exception.getMessage() + exception);
        return "redirect:/error";
    }

    /** Returns page to create individual person report.
     * @return Name of the file representing the individual person report page.
     */
    @GetMapping("/individual-person-report")
    public String individualPersonReportForm(){
        return "create-individual-person-report";
    }

    /** Returns page to create legal entity report.
     * @return Name of the file representing the legal entity report page.
     */
    @GetMapping("/legal-entity-report")
    public String legalEntityReportForm(){
        return "create-legal-entity-report";
    }

    /** Returns list of all reports submitted by the user.
     * @return Name of the file representing the list of reports.
     */
    @GetMapping()
    public String getReports(@AuthenticationPrincipal User user, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 7) Pageable pageable) {
        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId(), pageable));
        model.addAttribute("url", "/report");

        logger.info(user + " got report list");

        return "report-list";
    }

    /** Returns report data.
     * @param repId Report(id) to be open.
     * @exception NoSuchReportException if report was not found
     * @return Name of the file representing the report.
     */
    @GetMapping("/{repId}")
    public String openReport(@AuthenticationPrincipal User user,
                             @PathVariable String repId,
                             Model model) throws NoSuchReportException {

        Long reportId = Long.parseLong(repId);

        Optional<Report> findReportResult = reportService.findReportById(reportId);
        Report report = findReportResult.isPresent()
                ? findReportResult.get()
                : ArchiveReportConverter.archiveToReport(
                        archiveService.findReportById(reportId)
                        .orElseThrow(() -> new NoSuchReportException("Report was not found")));

        model.addAttribute("report", report);
        model.addAttribute("userId", user.getId());
        model.addAttribute("replaceInspector", reportService.isPossiblyToReplaceInspector(report.getId()));

        return "report";
    }

    /** Creates individual person report.
     * @param report Report to be created.
     * @exception NoSuchUserException if inspector with least reports number was not found
     * @see Report
     */
    @PostMapping("/individual-person-report")
    public String createIndividualPersonReport(@AuthenticationPrincipal User user,
                                         Report report,
                                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 7) Pageable pageable,
                                         Model model) throws NoSuchUserException {

        ReportValidationResultDto validationResult = reportService.createIndividualPersonReport(report, user);

        if(validationResult.isFullNameValid()
                && validationResult.isWorkplaceValid()
                && validationResult.isSalaryValid()) {

            return "redirect:/report";
        }
        else {
            model.addAttribute("isFullNameValid", validationResult.isFullNameValid());
            model.addAttribute("isWorkplaceValid", validationResult.isWorkplaceValid());
            model.addAttribute("isSalaryValid", validationResult.isSalaryValid());

            return "create-individual-person-report";
        }
    }

    /** Creates legal entity report.
     * @param report Report to be created.
     * @exception NoSuchUserException if inspector with least reports number was not found
     * @see Report
     */
    @PostMapping("/legal-entity-report")
    public String createLegalEntityReport(@AuthenticationPrincipal User user,
                                    Report report,
                                    Model model,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 8) Pageable pageable) throws NoSuchUserException {

        ReportValidationResultDto validationResult = reportService.createLegalEntityReport(report, user);

        if(validationResult.isCompanyNameValid() && validationResult.isFinancialTurnoverValid()) {

            return "redirect:/report";
        }

        else {
            model.addAttribute("isCompanyNameValid", validationResult.isCompanyNameValid());
            model.addAttribute("isFinancialTurnoverValid", validationResult.isFinancialTurnoverValid());

            return "create-legal-entity-report";
        }
    }

    /** Adds a current inspector to the list of replaced inspectors.
     *  Sets up a new inspector, if possible.
     * @param report Report which inspector will be replaced.
     * @exception NoSuchUserException if inspector with least reports number was not found
     * @see Report
     */
    @PostMapping("/replace-inspector/{report}")
    public String replaceInspector(@PathVariable Report report) throws NoSuchUserException {
        reportService.replaceInspector(report);

        return "redirect:/report";
    }

    /** Returns page to edit report if possible.
     * @param report Report(id) to be edited.
     * @return Name of the file representing the report edit page.
     */
    @GetMapping("/edit/{report}")
    public String getEditReportForm(@PathVariable Report report, Model model) {
        model.addAttribute("report", report);
        return "edit-report";
    }

    /** Report editing, data updating.
     * @param report Report(id) to be edited.
     * @param editedReport New report data.
     * @see Report
     */
    @PostMapping("/edit/{report}")
    public String editReport(@PathVariable Report report, Report editedReport) {
        reportService.editReport(report, editedReport);
        return "redirect:/report";
    }
}
