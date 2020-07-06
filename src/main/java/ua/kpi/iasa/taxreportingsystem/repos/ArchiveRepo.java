package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.iasa.taxreportingsystem.domain.Archive;

import java.util.List;

public interface ArchiveRepo extends JpaRepository<Archive, Long> {
    List<Archive> findByTaxpayerId(Long id);
}
