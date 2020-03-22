package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.iasa.taxreportingsystem.domain.IndividualPersonReport;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.util.List;

public interface IndividualPersonReportRepo extends JpaRepository<IndividualPersonReport, Long> {
    List<IndividualPersonReport> findByReportStatus(ReportStatus status);
    List<IndividualPersonReport> findByTaxpayerId(Long id);
}
