package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TaxSystemErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/access-denied")
    public String handleError() {
        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " attempted to access the protected URL.");
        return "error-403";
    }
}
