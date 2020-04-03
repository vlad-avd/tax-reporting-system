package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.dto.IndividualPersonReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.LegalEntityReportDTO;
import ua.kpi.iasa.taxreportingsystem.dto.ReportDTO;
import ua.kpi.iasa.taxreportingsystem.repos.ReportRepo;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepo reportRepo;

    public List<Report> get(User user){
        return reportRepo.getVerificationReports(user);
    }

    public List<Report> getUserSubmittedReports(Long id){
        return reportRepo.findByTaxpayerId(id);
    }

    public void saveReport(Report report){
        reportRepo.save(report);
    }

    public Report createIndividualPersonReport(IndividualPersonReportDTO reportDTO){
        return reportRepo.save(Report.builder()
                        .name(reportDTO.getName())
                        .surname(reportDTO.getSurname())
                        .patronymic(reportDTO.getPatronymic())
                        .workplace(reportDTO.getWorkplace())
                        .salary(reportDTO.getSalary())
                        .personType(PersonType.INDIVIDUAL_PERSON)
                        .reportStatus(ReportStatus.ON_VERIFYING)
                        .taxPeriodFrom(reportDTO.getTaxPeriodFrom())
                        .taxPeriodTo(reportDTO.getTaxPeriodTo())
                        .taxpayer(reportDTO.getTaxpayer())
                        .build());
    }

    public Report createLegalEntityReport(LegalEntityReportDTO reportDTO){
        return reportRepo.save(Report.builder()
                        .companyName(reportDTO.getCompanyName())
                        .employeesNumber(reportDTO.getEmployeesNumber())
                        .financialTurnover(reportDTO.getFinancialTurnover())
                        .personType(PersonType.LEGAL_ENTITY)
                        .reportStatus(ReportStatus.ON_VERIFYING)
                        .taxPeriodFrom(reportDTO.getTaxPeriodFrom())
                        .taxPeriodTo(reportDTO.getTaxPeriodTo())
                        .taxpayer(reportDTO.getTaxpayer())
                        .build());
        }
}
