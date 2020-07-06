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
}
