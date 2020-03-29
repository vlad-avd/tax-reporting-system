package ua.kpi.iasa.taxreportingsystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Edits;
import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;
import ua.kpi.iasa.taxreportingsystem.domain.enums.RejectionReason;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="individual_person_report")
public class IndividualPersonReport extends Report {
    private String name;
    private String surname;
    private String patronymic;
    private String workplace;
    private double salary;

    public IndividualPersonReport( String name, String surname, String patronymic, String workplace, double salary ){
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.workplace = workplace;
        this.salary = salary;
    }
}
