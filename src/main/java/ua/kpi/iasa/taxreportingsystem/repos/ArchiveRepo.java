package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.iasa.taxreportingsystem.domain.Archive;

public interface ArchiveRepo extends JpaRepository<Archive, Long> {
}
