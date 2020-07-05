package ua.kpi.iasa.taxreportingsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ua.kpi.iasa.taxreportingsystem.controller.MainController;
import ua.kpi.iasa.taxreportingsystem.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/registration", "/login/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        logger.info(authentication.getName() + " logged in.");
                        httpServletResponse.sendRedirect("/");
            })
                .loginPage("/login")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        logger.info(authentication.getName() + " logged out.");
                        httpServletResponse.sendRedirect("/");
                })
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}