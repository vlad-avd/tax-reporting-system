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
@Table(name="archive")
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @Enumerated(EnumType.STRING)
    private RejectionReason rejectionReason;

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
}
