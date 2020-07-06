package ua.kpi.iasa.taxreportingsystem.util;

import java.util.ResourceBundle;

public class ReportValidator {
    private final ResourceBundle regexpBundle;

    {
        regexpBundle = ResourceBundle.getBundle("regex");
    }

    public boolean isFullNameValid(String fullName) {
        return fullName.matches(regexpBundle.getString("full.name.regexp"));
    }

    public boolean isWorkplaceValid(String workplace) {
        return workplace.matches(regexpBundle.getString("workplace.regexp"));
    }

    public boolean isSalaryValid(String salary) {
        return salary.matches(regexpBundle.getString("income.regexp"));
    }
}
