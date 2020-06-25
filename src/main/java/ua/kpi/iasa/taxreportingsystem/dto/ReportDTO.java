package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReportDTO {
    protected Long id;

    protected PersonType personType;

    protected ReportStatus reportStatus;

    protected RejectionReason rejectionReason;

    protected String comment;

    protected LocalDate created;
    protected LocalDate lastEdit;

    protected User taxpayer;

    protected User inspector;

    protected User replacedInspector;

}
