package ua.kpi.iasa.taxreportingsystem.util;

import ua.kpi.iasa.taxreportingsystem.domain.Archive;
import ua.kpi.iasa.taxreportingsystem.domain.Report;

public class ArchiveReportConverter {
    public static Report archiveToReport(Archive archive) {
        return Report.builder()
                .id(archive.getId())
                .companyName(archive.getCompanyName())
                .financialTurnover(archive.getFinancialTurnover())
                .fullName(archive.getFullName())
                .workplace(archive.getWorkplace())
                .salary(archive.getSalary())
                .created(archive.getCreated())
                .lastEdit(archive.getLastEdit())
                .taxpayer(archive.getTaxpayer())
                .inspector(archive.getInspector())
                .rejectionReason(archive.getRejectionReason())
                .comment(archive.getComment())
                .personType(archive.getPersonType())
                .reportStatus(archive.getReportStatus())
                .build();
    }

    public static Archive reportToArchive(Report report) {
        return Archive.builder()
                .id(report.getId())
                .companyName(report.getCompanyName())
                .financialTurnover(report.getFinancialTurnover())
                .created(report.getCreated())
                .lastEdit(report.getLastEdit())
                .fullName(report.getFullName())
                .workplace(report.getWorkplace())
                .salary(report.getSalary())
                .taxpayer(report.getTaxpayer())
                .inspector(report.getInspector())
                .personType(report.getPersonType())
                .rejectionReason(report.getRejectionReason())
                .comment(report.getComment())
                .reportStatus(report.getReportStatus())
                .build();
    }
}
