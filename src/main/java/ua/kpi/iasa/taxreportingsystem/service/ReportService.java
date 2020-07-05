package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.taxreportingsystem.domain.Archive;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.dto.IndividualPersonReportDto;
import ua.kpi.iasa.taxreportingsystem.dto.LegalEntityReportDto;
import ua.kpi.iasa.taxreportingsystem.dto.StatisticsDto;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchUserException;
import ua.kpi.iasa.taxreportingsystem.repos.ArchiveRepo;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;

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

    public Optional<Page<Report>> getVerificationReports(Long id, Pageable pageable){
        return reportRepo.getVerificationReports(id, pageable);
    }

    public Optional<Page<Report>> getUserSubmittedReports(Long id, Pageable pageable){
        return reportRepo.findByTaxpayerId(id, pageable);
    }

    public void saveReport(Report report){
        reportRepo.save(report);
    }

    public void createIndividualPersonReport(IndividualPersonReportDto reportDTO){
        reportRepo.save(Report.builder()
                .fullName(reportDTO.getFullName())
                .workplace(reportDTO.getWorkplace())
                .salary(reportDTO.getSalary())
                .personType(PersonType.INDIVIDUAL)
                .reportStatus(ReportStatus.ON_VERIFYING)
                .created(reportDTO.getCreated())
                .lastEdit(reportDTO.getLastEdit())
                .taxpayer(reportDTO.getTaxpayer())
                .inspector(reportDTO.getInspector())
                .created(LocalDate.now())
                .build());
    }

    public void createLegalEntityReport(LegalEntityReportDto reportDTO){
        reportRepo.save(Report.builder()
                .companyName(reportDTO.getCompanyName())
                .financialTurnover(reportDTO.getFinancialTurnover())
                .personType(PersonType.ENTITY)
                .reportStatus(ReportStatus.ON_VERIFYING)
                .lastEdit(reportDTO.getLastEdit())
                .taxpayer(reportDTO.getTaxpayer())
                .taxpayer(reportDTO.getTaxpayer())
                .inspector(reportDTO.getInspector())
                .created(LocalDate.now())
                .build());
    }

    public User getInspectorIdWithLeastReportsNumber() throws NoSuchUserException {
        List<Long> inspectors = reportRepo.getAllInspectorIds();
//        List<Long> replacedInspectors = reportRepo.getReplacedInspectorsByReportId(reportId);
//        inspectors.removeAll(replacedInspectors);
        List<Long> inspectorIdsInReports = reportRepo.getAllInspectorIdsFromReports();

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

    public Archive reportToArchive(Report report) {
        return Archive.builder()
                .id(report.getId())
                .companyName(report.getCompanyName())
                .financialTurnover(report.getFinancialTurnover())
                .created(report.getCreated())
                .lastEdit(report.getLastEdit())
                .fullName(report.getFullName())
                .workplace(report.getWorkplace())
                .salary(report.getSalary())
                .taxpayer(report.getTaxpayer())
                .inspector(report.getInspector())
                .personType(report.getPersonType())
                .rejectionReason(report.getRejectionReason())
                .comment(report.getComment())
                .reportStatus(report.getReportStatus())
                .replacedInspectors(report.getReplacedInspectors())
                .build();
    }

    @Transactional
    public void moveReportToArchive(Report report) {
        reportRepo.deleteReplacedInspectors(report.getId());
        reportRepo.save(report);
        reportRepo.delete(report);
        archiveRepo.save(reportToArchive(report));
    }

    public boolean isPossiblyToReplaceInspector(Long reportId) {
        List<Long> inspectors = reportRepo.getAllInspectorIds();
        System.out.println("All inspectors: " + inspectors);
        List<Long> replacedInspectors = reportRepo.getReplacedInspectorsByReportId(reportId);
        System.out.println("Replaced inspectors: " + replacedInspectors);

        return (inspectors.size() - replacedInspectors.size()) > 1;
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
}
