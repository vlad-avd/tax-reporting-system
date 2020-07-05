package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.iasa.taxreportingsystem.dto.UserDto;
import ua.kpi.iasa.taxreportingsystem.service.UserService;
import ua.kpi.iasa.taxreportingsystem.util.UserValidator;

@Controller
public class MainController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

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
    public String addUser(UserDto userDTO, Model model){

        UserValidator userValidator = new UserValidator();

        if(!userService.findByUsername(userDTO.getUsername()).isPresent()
                && userValidator.isValidUsername(userDTO.getUsername())
                && userValidator.isValidPassword(userDTO.getPassword())) {

            userService.createUser(userDTO);
            logger.info("User " + userDTO + " was created.");

            return "redirect:/login";
        }

        model.addAttribute("isUserExist", userService.findByUsername(userDTO.getUsername()).isPresent());
        model.addAttribute("isUsernameValid", userValidator.isValidUsername(userDTO.getUsername()));
        model.addAttribute("isPasswordValid", userValidator.isValidPassword(userDTO.getPassword()));

        return "registration";
    }
}
