package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.Data;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @Enumerated(EnumType.STRING)
    private RejectionReason rejectionReason;

    @Enumerated(EnumType.STRING)
    private Edits edits;

    private int period;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="taxpayer_id")
    private User taxpayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="inspector_id")
    private User inspector;
}
