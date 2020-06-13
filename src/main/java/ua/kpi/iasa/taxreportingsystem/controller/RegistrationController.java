package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;
import ua.kpi.iasa.taxreportingsystem.dto.UserDTO;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;
import ua.kpi.iasa.taxreportingsystem.service.UserService;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registratin(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDTO userDTO, Model model){

        if(userService.findByUsername(userDTO.getUsername()).isPresent()){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        userService.createUser(userDTO);

        return "redirect:/login";
    }
}
