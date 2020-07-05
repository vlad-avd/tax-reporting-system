package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public abstract class ReportDto {
    protected Long id;

    protected PersonType personType;

    protected ReportStatus reportStatus;

    protected String comment;

    protected LocalDate created;
    protected LocalDate lastEdit;

    protected User taxpayer;

    protected User inspector;

    protected User replacedInspector;

}
