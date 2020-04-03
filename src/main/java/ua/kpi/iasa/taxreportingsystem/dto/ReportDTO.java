package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReportDTO {
    protected Long id;

    protected PersonType personType;

    protected ReportStatus reportStatus;

    protected RejectionReason rejectionReason;

    protected Edits edits;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate taxPeriodFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate taxPeriodTo;

    protected User taxpayer;

    protected User inspector;

    protected User replacedInspector;

//    //IndividualPersonReport
//    private String name;
//    private String surname;
//    private String patronymic;
//    private String workplace;
//    private double salary;

//    //LegalEntityReport
//    private String companyName;
//    private int financialTurnover;
//    private int employeesNumber;
//
//    private List<User> replacedInspectors = new ArrayList<>();
//
//    public ReportDTO(String companyName, int financialTurnover, int employeesNumber){
//        this.companyName = companyName;
//        this.financialTurnover = financialTurnover;
//        this.employeesNumber = employeesNumber;
//    }

//    public ReportDTO(String name, String surname, String patronymic, String workplace, double salary ){
//        this.name = name;
//        this.surname = surname;
//        this.patronymic = patronymic;
//        this.workplace = workplace;
//        this.salary = salary;
//    }
}
