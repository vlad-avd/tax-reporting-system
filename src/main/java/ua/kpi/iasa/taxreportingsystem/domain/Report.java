package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taxPeriodFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taxPeriodTo;

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
    private BigDecimal salary;

    //LegalEntityReport
    private String companyName;
    private BigDecimal financialTurnover;
    private int employeesNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="replaced_inspector_id")
    private User replacedInspector;

    public Report(String companyName, BigDecimal financialTurnover, int employeesNumber){
        this.companyName = companyName;
        this.financialTurnover = financialTurnover;
        this.employeesNumber = employeesNumber;
    }

    public Report( String name, String surname, String patronymic, String workplace, BigDecimal salary ){
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.workplace = workplace;
        this.salary = salary;
    }
}
