package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaxSystemErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/denied")
    public String handleDeniedError() {
        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " attempted to access the protected URL.");
        return "error-403";
    }

    @RequestMapping("/error")
    public String handleAllErrors() {
        logger.info("Something went wrong");
        return "error";
    }
}
