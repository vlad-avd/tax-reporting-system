package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    //IndividualPersonReport
    private String name;
    private String surname;
    private String patronymic;
    private String workplace;
    private double salary;

    //LegalEntityReport
    private String companyName;
    private int financialTurnover;
    private int employeesNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="replaced_inspector_id")
    private User replacedInspector;

    public Report(String companyName, int financialTurnover, int employeesNumber){
        this.companyName = companyName;
        this.financialTurnover = financialTurnover;
        this.employeesNumber = employeesNumber;
    }

    public Report( String name, String surname, String patronymic, String workplace, double salary ){
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.workplace = workplace;
        this.salary = salary;
    }
}
