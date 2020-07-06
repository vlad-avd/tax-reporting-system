package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.Archive;
import ua.kpi.iasa.taxreportingsystem.repos.ArchiveRepo;

import java.util.Optional;

@Service
public class ArchiveService {

    private final ArchiveRepo archiveRepo;

    @Autowired
    public ArchiveService(ArchiveRepo archiveRepo) {
        this.archiveRepo = archiveRepo;
    }

    public Optional<Archive> findReportById(Long reportId) {
        return archiveRepo.findById(reportId);
    }
}
