package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.dto.IndividualPersonReportDto;
import ua.kpi.iasa.taxreportingsystem.dto.LegalEntityReportDto;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchReportException;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchUserException;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;
import ua.kpi.iasa.taxreportingsystem.util.ReportValidator;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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

    @GetMapping("/individual-person-report")
    public String createIndividualPersonReport(){
        return "create-individual-person-report";
    }

    @GetMapping("/legal-entity-report")
    public String createLegalEntityReport(){
        return "create-legal-entity-report";
    }

    @GetMapping()
    public String reportList(@AuthenticationPrincipal User user, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 2) Pageable pageable) throws NoSuchReportException {
        model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId(), pageable).orElseThrow(() -> new NoSuchReportException("Reports were not found")));

        model.addAttribute("url", "/report");

        logger.info(user + " got report list");

        return "report-list";
    }

    @GetMapping("/{report}")
    public String openReport(@AuthenticationPrincipal User user,
                             @PathVariable Report report,
                             Model model){
        model.addAttribute("report", report);
        model.addAttribute("userId", user.getId());
        model.addAttribute("replaceInspector", reportService.isPossiblyToReplaceInspector(report.getId()));
        return "report";
    }

    @PostMapping("/individual-person-report")
    public String individualPersonReport(@AuthenticationPrincipal User user,
                                         IndividualPersonReportDto reportDTO,
                                         Model model,
                                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 8) Pageable pageable) throws NoSuchReportException, NoSuchUserException {

        ReportValidator reportValidator = new ReportValidator();

        if(reportValidator.isFullNameValid(reportDTO.getFullName())
                && reportValidator.isWorkplaceValid(reportDTO.getWorkplace())
                && reportValidator.isSalaryValid(String.valueOf(reportDTO.getSalary()))) {

            reportDTO.setTaxpayer(user);
            reportDTO.setInspector(reportService.getInspectorIdWithLeastReportsNumber());

            reportService.createIndividualPersonReport(reportDTO);

            model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId(), pageable).orElseThrow(() -> new NoSuchReportException("Reports were not found")));

            return "redirect:/report";
        }

        else {
            model.addAttribute("isFullNameValid", reportValidator.isFullNameValid(reportDTO.getFullName()));
            model.addAttribute("isWorkplaceValid", reportValidator.isWorkplaceValid(reportDTO.getWorkplace()));
            model.addAttribute("isSalaryValid", reportValidator.isSalaryValid(String.valueOf(reportDTO.getSalary())));

            return "create-individual-person-report";
        }
    }

    @PostMapping("/legal-entity-report")
    public String legalEntityReport(@AuthenticationPrincipal User user,
                                    LegalEntityReportDto reportDTO,
                                    Model model,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 8) Pageable pageable) throws NoSuchReportException, NoSuchUserException {

        ReportValidator reportValidator = new ReportValidator();

        if(reportValidator.isWorkplaceValid(reportDTO.getCompanyName())
                && reportValidator.isSalaryValid(String.valueOf(reportDTO.getFinancialTurnover()))) {

            reportDTO.setTaxpayer(user);
            reportDTO.setInspector(reportService.getInspectorIdWithLeastReportsNumber());

            reportService.createLegalEntityReport(reportDTO);

            model.addAttribute("reports", reportService.getUserSubmittedReports(user.getId(), pageable).orElseThrow(() -> new NoSuchReportException("Reports were not found")));

            return "redirect:/report";
        }

        else {
            model.addAttribute("isCompanyNameValid", reportValidator.isWorkplaceValid(reportDTO.getCompanyName()));
            model.addAttribute("isFinancialTurnoverValid", reportValidator.isSalaryValid(String.valueOf(reportDTO.getFinancialTurnover())));

            return "create-legal-entity-report";
        }
    }


    @PostMapping("/replace-inspector/{report}")
    public String replaceInspector(@PathVariable Report report) throws NoSuchUserException {
        List<User> replacedInspectors = report.getReplacedInspectors();
        replacedInspectors.add(report.getInspector());
        report.setReplacedInspectors(replacedInspectors);
        report.setReportStatus(ReportStatus.ON_VERIFYING);
        report.setInspector(reportService.getInspectorIdWithLeastReportsNumber());
        reportService.saveReport(report);

        return "redirect:/report";
    }

    @GetMapping("/edit/{report}")
    public String editReportForm(@PathVariable Report report, Model model) {
        model.addAttribute("report", report);
        return "edit-report";
    }

    @PostMapping("/edit/{report}")
    public String editReport(@PathVariable Report report, Report editedReport) {

        report.setFullName(editedReport.getFullName());
        report.setWorkplace(editedReport.getWorkplace());
        report.setSalary(editedReport.getSalary());
        report.setCompanyName(editedReport.getCompanyName());
        report.setFinancialTurnover(editedReport.getFinancialTurnover());
        report.setLastEdit(LocalDate.now());
        report.setReportStatus(ReportStatus.ON_VERIFYING);
        reportService.saveReport(editedReport);
        return "redirect:/report";
    }
}
