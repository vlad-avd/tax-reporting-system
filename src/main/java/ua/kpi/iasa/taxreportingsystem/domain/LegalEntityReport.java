package ua.kpi.iasa.taxreportingsystem.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="legal_entity_report")
public class LegalEntityReport extends Report {
    private String companyName;
    private int financialTurnover;
    private int employeesNumber;
}
