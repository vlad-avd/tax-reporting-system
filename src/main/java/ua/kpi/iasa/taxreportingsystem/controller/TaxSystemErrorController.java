package ua.kpi.iasa.taxreportingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class controller handles error, denied mappings.
 * @author Vladyslav Avdiienko
 * @version 1.0
 */
@Controller
public class TaxSystemErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    /** Returns error-403 page.
     * @return Name of the file representing access-denied page page.
     */
    @RequestMapping("/denied")
    public String handleDeniedError() {
        logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " attempted to access the protected URL.");
        return "error-403";
    }

    /** Returns error page.
     * @return Name of the file representing the error page.
     */
    @RequestMapping("/error")
    public String handleAllErrors() {
        logger.info("Something went wrong");
        return "error";
    }
}
