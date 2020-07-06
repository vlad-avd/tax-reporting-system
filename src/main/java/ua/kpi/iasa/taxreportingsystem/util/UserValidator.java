package ua.kpi.iasa.taxreportingsystem.util;

import java.util.ResourceBundle;

public class UserValidator {

    private final ResourceBundle regexpBundle;

    {
        regexpBundle = ResourceBundle.getBundle("regex");
    }

    public boolean isValidUsername(String username) {
        return username.matches(regexpBundle.getString("username.regexp"));
    }

    public boolean isValidPassword(String password) {
        return password.matches(regexpBundle.getString("password.regexp"));
    }
}
