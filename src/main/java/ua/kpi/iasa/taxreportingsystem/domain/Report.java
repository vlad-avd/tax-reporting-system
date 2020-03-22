package ua.kpi.iasa.taxreportingsystem.domain;

import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;

@MappedSuperclass
public abstract class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    private int period;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="taxpayer_id")
    private User taxpayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="inspector_id")
    private User inspector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }


    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public User getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(User taxpayer) {
        this.taxpayer = taxpayer;
    }

    public User getInspector() {
        return inspector;
    }

    public void setInspector(User inspector) {
        this.inspector = inspector;
    }
}
