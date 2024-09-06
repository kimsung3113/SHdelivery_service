package com.delivery.storefranchise.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@Slf4j
public class PageController {

    @RequestMapping(path = {"/", "", "main"})
    public ModelAndView mainPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                log.info("Username: {}", userDetails.getUsername());
                log.info("Authorities: {}", userDetails.getAuthorities());
            } else {
                log.info("Principal: {}", principal.toString());
            }
        } else {
            log.info("No authentication information available");
        }

        return new ModelAndView("main"); // +html
    }

    @RequestMapping("/order")
    public ModelAndView order(){
        return new ModelAndView("/order/order");
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("/login/login");
    }

}
