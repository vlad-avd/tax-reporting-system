package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchiveDTO {
    private Long id;

    private PersonType personType;

    private ReportStatus reportStatus;

    private RejectionReason rejectionReason;

    private String comment;

    private LocalDate created;
    private LocalDate lastEdit;

    private User taxpayer;

    private User inspector;

    //IndividualPersonReport
    private String fullName;
    private String workplace;
    private BigDecimal salary;

    //LegalEntityReport
    private String companyName;
    private BigDecimal financialTurnover;

    private List<User> replacedInspectors;
}
