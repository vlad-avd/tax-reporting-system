package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.service.UserService;

/**
 * Class controller handles user profile mapping.
 * @author Vladyslav Avdiienko
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /** Constructor of the controller that initializes the service.
     * @param userService Service that processes a table with users.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** Returns user profile page.
     * @return Name of the file representing the user profile page.
     */
    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", userService.loadUserByUsername(user.getUsername()));

        return "profile";
    }
}
