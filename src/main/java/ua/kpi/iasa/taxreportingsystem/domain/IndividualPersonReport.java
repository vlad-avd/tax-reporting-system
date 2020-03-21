package ua.kpi.iasa.taxreportingsystem.domain;

import ua.kpi.iasa.taxreportingsystem.domain.enums.PersonType;

import javax.persistence.*;

@Entity
@Table(name="individual_person_report")
public class IndividualPersonReport extends Report {
    private String name;
    private String surname;
    private String patronymic;
    private String workplace;
    private double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
