package ua.kpi.iasa.taxreportingsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
