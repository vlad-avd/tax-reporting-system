package ua.kpi.iasa.taxreportingsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;
import ua.kpi.iasa.taxreportingsystem.dto.StatisticsDto;
import ua.kpi.iasa.taxreportingsystem.service.ReportService;
import ua.kpi.iasa.taxreportingsystem.service.UserService;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public AdminController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/user")
    public String getUsers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 7) Pageable pageable,
                           Model model){
        model.addAttribute("users", userService.getAllUsers(pageable));
        model.addAttribute("url", "/user");

        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " got user list.");

        return "user-list";
    }

    @GetMapping("/user/edit/{userId}")
    public String getUserEditForm(@PathVariable("userId") User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "user-edit";
    }

    @PostMapping("/user/edit/{userId}")
    public String saveEditedUser(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 7) Pageable pageable,
                                 @PathVariable("userId") User user,
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam Map<String, String> rolesForm,
                                 Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        user.setUsername(username);
        user.setPassword(password);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : rolesForm.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.saveUser(user);
        logger.info("User: " + user + "data has been edited and saved by: " + SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("users", userService.getAllUsers(pageable));

        return "redirect:/user";
    }

    @GetMapping("user/statistics/{userId}")
    public String getStatistics(@PathVariable("userId") User user,
                                Model model) {

        StatisticsDto statistics = reportService.getStatistics(user.getId());
        logger.info("User " + user + "statistics for reports were created");

        model.addAttribute("user", user);
        model.addAttribute("statistics", statistics);

        return "user-statistics";
    }
}
