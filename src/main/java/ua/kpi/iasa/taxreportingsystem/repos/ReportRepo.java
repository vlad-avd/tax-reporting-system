package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
    List<Report> findByTaxpayerId(Long id);
    @Query(
            value = "SELECT * FROM report r WHERE r.report_status = 'ON_VERIFYING' and (r.replaced_inspector_id <> ? or r.replaced_inspector_id is NULL)",
            nativeQuery = true)
    List<Report> getVerificationReports(User user);
}
