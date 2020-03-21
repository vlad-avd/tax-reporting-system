package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @GetMapping("user")
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }
}
