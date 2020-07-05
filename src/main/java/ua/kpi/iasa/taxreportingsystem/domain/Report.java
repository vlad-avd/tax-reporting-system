package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    private String comment;

    private LocalDate created;
    private LocalDate lastEdit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="taxpayer_id")
    private User taxpayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="inspector_id")
    private User inspector;

    //IndividualPersonReport
    private String fullName;
    private String workplace;
    private BigDecimal salary;

    //LegalEntityReport
    private String companyName;
    private BigDecimal financialTurnover;

    @ManyToMany
    @JoinTable (name="report_replaced_inspector",
            joinColumns=@JoinColumn (name="report_id"),
            inverseJoinColumns=@JoinColumn(name="inspector_id"))
    private List<User> replacedInspectors;

    public Report(String companyName, BigDecimal financialTurnover){
        this.companyName = companyName;
        this.financialTurnover = financialTurnover;
    }

    public Report(String fullName, String workplace, BigDecimal salary ){
        this.fullName = fullName;
        this.workplace = workplace;
        this.salary = salary;
    }
}
