package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="legal_entity_report")
public class LegalEntityReport extends Report {
    private String companyName;
    private int financialTurnover;
    private int employeesNumber;

    public LegalEntityReport(String companyName, int financialTurnover, int employeesNumber){
        this.companyName = companyName;
        this.financialTurnover = financialTurnover;
        this.employeesNumber = employeesNumber;
    }
}
