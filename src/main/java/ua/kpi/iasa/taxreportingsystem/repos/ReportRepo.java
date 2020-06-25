package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.taxreportingsystem.domain.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {

    Optional<Page<Report>> findByTaxpayerId(Long id, Pageable pageable);

    @Query(
            value = "SELECT * FROM report AS r WHERE r.report_status = 'ON_VERIFYING' and r.inspector_id = :id",
            nativeQuery = true)
    Optional<Page<Report>> getVerificationReports(@Param("id") Long id, Pageable pageable);

    @Query(
            value = "SELECT (id) FROM usr LEFT JOIN user_role ON usr.id = user_role.user_id WHERE user_role.roles LIKE '%ROLE_INSPECTOR%' or user_role.roles LIKE '%ROLE_ADMIN%'",
            nativeQuery = true)
    List<Long> getAllInspectorIds();

    @Query(
            value = "SELECT (inspector_id) FROM report",
            nativeQuery = true)
    List<Long> getAllInspectorIdsFromReports();

    @Query(
            value = "SELECT (inspector_id) FROM reports_replaced_inspector WHERE report_id = :id",
            nativeQuery = true)
    List<Long> getReplacedInspectorsByReportId(@Param("id") Long reportId);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM reports_replaced_inspector WHERE report_id = :id",
            nativeQuery = true)
    void deleteReplacedInspectors(@Param("id") Long reportId);
}
