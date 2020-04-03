package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.iasa.taxreportingsystem.domain.IndividualPersonReport;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualPersonReportRepo extends JpaRepository<IndividualPersonReport, Long> {
    List<IndividualPersonReport> findByReportStatus(ReportStatus status);
    List<IndividualPersonReport> findByTaxpayerId(Long id);
    List<IndividualPersonReport> findByReportStatusAndReplacedInspectorIsNullOrReplacedInspectorNot(ReportStatus status, User user);
}
