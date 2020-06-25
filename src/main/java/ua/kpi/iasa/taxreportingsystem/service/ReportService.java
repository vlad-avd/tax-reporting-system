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
import ua.kpi.iasa.taxreportingsystem.dto.IndividualPersonReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.LegalEntityReportDTO;
import ua.kpi.iasa.taxreportingsystem.exception.NoSuchUserException;
import ua.kpi.iasa.taxreportingsystem.repos.ArchiveRepo;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArchiveRepo archiveRepo;

    public Optional<Page<Report>> getVerificationReports(Long id, Pageable pageable){
        return reportRepo.getVerificationReports(id, pageable);
    }

    public Optional<Page<Report>> getUserSubmittedReports(Long id, Pageable pageable){
        return reportRepo.findByTaxpayerId(id, pageable);
    }

    public void saveReport(Report report){
        reportRepo.save(report);
    }

    public Report createIndividualPersonReport(IndividualPersonReportDTO reportDTO){
        return reportRepo.save(Report.builder()
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

    public Report createLegalEntityReport(LegalEntityReportDTO reportDTO){
        return reportRepo.save(Report.builder()
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
}
