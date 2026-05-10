package com.b1uffer.multisessiontest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session, Authentication auth) {
        model.addAttribute("sessionId", session.getId()); // session Id
        model.addAttribute("username", auth.getName()); // authentication name
        model.addAttribute("creationTime", session.getCreationTime());
        model.addAttribute("lastAccessedTime", session.getLastAccessedTime());
        return "home";
    }
}
