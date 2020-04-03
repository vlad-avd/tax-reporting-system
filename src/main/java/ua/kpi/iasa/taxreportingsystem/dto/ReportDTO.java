package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.ReportStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReportDTO {
    private Long id;

    private PersonType personType;

    private ReportStatus reportStatus;

    private RejectionReason rejectionReason;

    private Edits edits;

    private int period;

    private User taxpayer;

    private User inspector;

    private User replacedInspector;

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
