package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.taxreportingsystem.domain.Archive;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.dto.ReportValidationResultDto;
import ua.kpi.iasa.taxreportingsystem.dto.StatisticsDto;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchReportException;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchUserException;
import ua.kpi.iasa.taxreportingsystem.repos.ArchiveRepo;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;
import ua.kpi.iasa.taxreportingsystem.util.ArchiveReportConverter;
import ua.kpi.iasa.taxreportingsystem.util.ReportValidator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepo reportRepo;
    private final UserRepo userRepo;
    private final ArchiveRepo archiveRepo;

    @Autowired
    public ReportService(ReportRepo reportRepo, UserRepo userRepo, ArchiveRepo archiveRepo) {
        this.reportRepo = reportRepo;
        this.userRepo = userRepo;
        this.archiveRepo = archiveRepo;
    }

    public Optional<Report> findReportById(Long reportId) {
        return reportRepo.findById(reportId);
    }

    public Optional<Page<Report>> getVerificationReports(Long id, Pageable pageable){
        return reportRepo.getVerificationReports(id, pageable);
    }

    public Page<Report> getUserSubmittedReports(Long id, Pageable pageable){
        List<Report> reports = reportRepo.findByTaxpayerId(id);
        List<Archive> archivedReports = archiveRepo.findByTaxpayerId(id);

        reports.addAll(archivedReports.stream()
                        .map(ArchiveReportConverter::archiveToReport)
                        .collect(Collectors.toList()));

        reports = reports.stream()
                        .sorted(Comparator.comparing(Report::getId).reversed())
                        .collect(Collectors.toList());

        return new PageImpl<>(reports.subList(pageable.getPageNumber()*pageable.getPageSize(),
                                        Math.min((pageable.getPageNumber() + 1) * pageable.getPageSize(), reports.size())),
                                    pageable,
                                    reports.size());
    }

    public void saveReport(Report report){
        reportRepo.save(report);
    }

    public ReportValidationResultDto createIndividualPersonReport(Report report, User user) throws NoSuchUserException, NoSuchReportException {
        ReportValidator reportValidator = new ReportValidator();

        boolean isFullNameValid = reportValidator.isFullNameValid(report.getFullName());
        boolean isWorkplaceValid = reportValidator.isWorkplaceValid(report.getWorkplace());
        boolean isSalaryValid = reportValidator.isSalaryValid(String.valueOf(report.getSalary()));

        if(isFullNameValid && isWorkplaceValid && isSalaryValid) {
            report.setTaxpayer(user);
            report.setInspector(getInspectorIdWithLeastReportsNumber(report.getId(), true));

            saveReport(Report.builder()
                    .fullName(report.getFullName())
                    .workplace(report.getWorkplace())
                    .salary(report.getSalary())
                    .personType(PersonType.INDIVIDUAL)
                    .reportStatus(ReportStatus.ON_VERIFYING)
                    .created(report.getCreated())
                    .lastEdit(report.getLastEdit())
                    .taxpayer(report.getTaxpayer())
                    .inspector(report.getInspector())
                    .created(LocalDate.now())
                    .build());
        }
            return ReportValidationResultDto.builder()
                    .isFullNameValid(isFullNameValid)
                    .isWorkplaceValid(isWorkplaceValid)
                    .isSalaryValid(isSalaryValid)
                    .build();
    }

    public ReportValidationResultDto createLegalEntityReport(Report report, User user) throws NoSuchUserException, NoSuchReportException {
        ReportValidator reportValidator = new ReportValidator();

        boolean isCompanyNameValid = reportValidator.isWorkplaceValid(report.getCompanyName());
        boolean isFinancialTurnoverValid = reportValidator.isSalaryValid(String.valueOf(report.getFinancialTurnover()));

        if(isCompanyNameValid && isFinancialTurnoverValid) {
            report.setTaxpayer(user);
            report.setInspector(getInspectorIdWithLeastReportsNumber(report.getId(), true));

            reportRepo.save(Report.builder()
                    .companyName(report.getCompanyName())
                    .financialTurnover(report.getFinancialTurnover())
                    .personType(PersonType.ENTITY)
                    .reportStatus(ReportStatus.ON_VERIFYING)
                    .lastEdit(report.getLastEdit())
                    .taxpayer(report.getTaxpayer())
                    .taxpayer(report.getTaxpayer())
                    .inspector(report.getInspector())
                    .created(LocalDate.now())
                    .build());
        }
        return ReportValidationResultDto.builder()
                .isCompanyNameValid(isCompanyNameValid)
                .isFinancialTurnoverValid(isFinancialTurnoverValid)
                .build();
    }

    public User getInspectorIdWithLeastReportsNumber(Long reportId, boolean createReport) throws NoSuchUserException, NoSuchReportException {
        List<Long> inspectors = reportRepo.getAllInspectorIds();
        List<Long> inspectorIdsInReports = reportRepo.getAllInspectorIdsFromReports();

        if (!createReport) {
            List<Long> replacedInspectors = reportRepo.getReplacedInspectorsByReportId(reportId);
            inspectors.removeAll(replacedInspectors);
            inspectors.remove(reportRepo
                            .findById(reportId)
                            .orElseThrow(() -> new NoSuchReportException("Report: " + reportId + " was not found"))
                            .getInspector()
                            .getId());
            inspectorIdsInReports.removeAll(replacedInspectors);
        }

        Long inspectorWithLeastReportsNumber = inspectors.get(0);
        long reportsNumber = inspectorIdsInReports.stream()
                .filter(inspectors.get(0)::equals)
                .count();

        for (Long inspector : inspectors) {
            long rN = inspectorIdsInReports.stream()
                    .filter(inspector::equals)
                    .count();

            if (rN < reportsNumber) {
                reportsNumber = rN;
                inspectorWithLeastReportsNumber = inspector;
            }
        }
        return userRepo.findById(inspectorWithLeastReportsNumber).orElseThrow(() -> new NoSuchUserException("User with id = " + inspectorIdsInReports + " was not found"));
    }

    @Transactional
    public void moveReportToArchive(Report report) {
        reportRepo.deleteReplacedInspectors(report.getId());
        reportRepo.delete(report);
        archiveRepo.save(ArchiveReportConverter.reportToArchive(report));
    }

    public boolean isPossiblyToReplaceInspector(Long reportId) {
        List<Long> inspectors = reportRepo.getAllInspectorIds();
        List<Long> replacedInspectors = reportRepo.getReplacedInspectorsByReportId(reportId);
        return (inspectors.size() - replacedInspectors.size()) > 1; // Attempts to replace the inspector one less than the inspectors in the database
    }

    public StatisticsDto getStatistics(Long userId) {
        List<Report> reports = reportRepo.findByTaxpayerId(userId);

        int reportsNumber = reports.size();

        List<LocalDate> createdDates = reports.stream()
                .map(Report::getCreated)
                .collect(Collectors.toList());

        LocalDate firstReportDate = createdDates.stream()
                .min(Comparator.comparing(LocalDate::toEpochDay))
                .orElse(null);

        LocalDate lastReportDate = createdDates.stream()
                .max(Comparator.comparing(LocalDate::toEpochDay))
                .orElse(null);

        List<ReportStatus> reportStatuses = reports.stream()
                .map(Report::getReportStatus)
                .collect(Collectors.toList());

        int onVerifyingReportsNumber = Math.toIntExact(reportStatuses.stream()
                .filter(reportStatus -> reportStatus.equals(ReportStatus.ON_VERIFYING))
                .count());

        int needToEditReportsNumber = Math.toIntExact(reportStatuses.stream()
                .filter(reportStatus -> reportStatus.equals(ReportStatus.NEED_TO_EDIT))
                .count());

        int approvedReportsNumber = Math.toIntExact(reportStatuses.stream()
                .filter(reportStatus -> reportStatus.equals(ReportStatus.APPROVED))
                .count());

        int rejectedReportsNumber = Math.toIntExact(reportStatuses.stream()
                .filter(reportStatus -> reportStatus.equals(ReportStatus.REJECTED))
                .count());

        return StatisticsDto.builder()
                .reportsNumber(reportsNumber)
                .firstReportDate(firstReportDate)
                .lastReportDate(lastReportDate)
                .onVerifyingReportsNumber(onVerifyingReportsNumber)
                .needToEditReportsNumber(needToEditReportsNumber)
                .approvedReportsNumber(approvedReportsNumber)
                .rejectedReportsNumber(rejectedReportsNumber)
                .build();
    }

    public void editReport(Report report, Report editedReport) {
        report.setFullName(editedReport.getFullName());
        report.setWorkplace(editedReport.getWorkplace());
        report.setSalary(editedReport.getSalary());
        report.setCompanyName(editedReport.getCompanyName());
        report.setFinancialTurnover(editedReport.getFinancialTurnover());
        report.setLastEdit(LocalDate.now());
        report.setReportStatus(ReportStatus.ON_VERIFYING);

        saveReport(editedReport);
    }

    public void replaceInspector(Report report) throws NoSuchUserException, NoSuchReportException {
        List<User> replacedInspectors = report.getReplacedInspectors();
        replacedInspectors.add(report.getInspector());

        report.setReplacedInspectors(replacedInspectors);
        report.setReportStatus(ReportStatus.ON_VERIFYING);
        report.setInspector(getInspectorIdWithLeastReportsNumber(report.getId(), false));

        saveReport(report);
    }

    public void verifyReport(Report report, String reportStatus) {
        if(reportStatus.equals("approve") ){
            report.setReportStatus(ReportStatus.APPROVED);
            moveReportToArchive(report);
        }
        else if(reportStatus.equals("reject") ){
            report.setReportStatus(ReportStatus.REJECTED);
            moveReportToArchive(report);
        }
        else if(reportStatus.equals("sendToEdit")){
            report.setReportStatus(ReportStatus.NEED_TO_EDIT);
            saveReport(report);
        }
    }
}
