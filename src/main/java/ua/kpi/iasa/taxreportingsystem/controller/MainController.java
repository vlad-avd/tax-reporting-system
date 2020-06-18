package ua.kpi.iasa.taxreportingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.iasa.taxreportingsystem.dto.UserDTO;
import ua.kpi.iasa.taxreportingsystem.service.UserService;
import ua.kpi.iasa.taxreportingsystem.util.UserValidator;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDTO userDTO, Model model){

        UserValidator userValidator = new UserValidator();

        if(!userService.findByUsername(userDTO.getUsername()).isPresent()
                && userValidator.isValidUsername(userDTO.getUsername())
                && userValidator.isValidPassword(userDTO.getPassword())) {

            userService.createUser(userDTO);
            return "redirect:/login";
        }

        model.addAttribute("isUserExist", userService.findByUsername(userDTO.getUsername()).isPresent());
        model.addAttribute("isUsernameValid", userValidator.isValidUsername(userDTO.getUsername()));
        model.addAttribute("isPasswordValid", userValidator.isValidPassword(userDTO.getPassword()));

        return "registration";
    }
}
