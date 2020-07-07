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

/**
 * Class controller handles root, login and registration mappings.
 * @author Vladyslav Avdiienko
 * @version 1.0
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final UserService userService;

    /** Constructor of the controller that initializes the service.
     * @param userService Service that processes a table with users.
     */
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    /** Returns app main page.
     * @return Name of the file representing the main page.
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /** Returns login page.
     * @return Name of the file representing the login page.
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /** Returns registration page.
     * @return Name of the file representing the registration page.
     */
    @GetMapping("/registration")
    public String registration(){

        return "registration";
    }

    /** Creates a new user with a unique login.
     * After successful registration, it redirects to the login page.
     * @param userDto User login and password wrapped in the object.
     * @see UserDto
     */
    @PostMapping("/registration")
    public String addUser(UserDto userDto, Model model){

        UserValidator userValidator = new UserValidator();

        boolean isUserExist = userService.findByUsername(userDto.getUsername()).isPresent();
        boolean isUsernameValid = userValidator.isValidUsername(userDto.getUsername());
        boolean isPasswordValid = userValidator.isValidPassword(userDto.getPassword());

        if(!isUserExist && isUsernameValid && isPasswordValid) {
            userService.createUser(userDto);
            logger.info("User " + userDto + " was created.");

            return "redirect:/login";
        }

        model.addAttribute("isUserExist", isUserExist);
        model.addAttribute("isUsernameValid", isUsernameValid);
        model.addAttribute("isPasswordValid", isPasswordValid);

        return "redirect:/login";
    }
}
