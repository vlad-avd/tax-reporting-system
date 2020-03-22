package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.Report;
import ua.kpi.iasa.taxreportingsystem.repos.IndividualPersonReportRepo;

import java.util.List;

@Service
public class IndividualPersonReportService {
    @Autowired
    private IndividualPersonReportRepo individualPersonReportRepo;
}
