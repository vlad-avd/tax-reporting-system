package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;
import ua.kpi.iasa.taxreportingsystem.service.UserService;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", userService.loadUserByUsername(user.getUsername()));

        return "profile";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping
    public String userEdit(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user){

        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.saveUser(user);

        return "redirect:/user";
    }
}
