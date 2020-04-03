package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.IndividualPersonReport;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;
import ua.kpi.iasa.taxreportingsystem.repos.IndividualPersonReportRepo;

import java.util.List;
import java.util.Optional;

@Service
public class IndividualPersonReportService {
    @Autowired
    private IndividualPersonReportRepo individualPersonReportRepo;

    public List<IndividualPersonReport> get(ReportStatus reportStatus, User user){
        return individualPersonReportRepo.findByReportStatusAndReplacedInspectorIsNullOrReplacedInspectorNot(reportStatus, user);
    }
}
