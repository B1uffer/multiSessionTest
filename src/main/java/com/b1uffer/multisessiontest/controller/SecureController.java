package com.b1uffer.multisessiontest.controller;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {
    @GetMapping("/change-password")
    public String changePasswordPage(Authentication authentication) {
        if(authentication instanceof RememberMeAuthenticationToken) {
            return "redirect:/login?reauth=true";
        }
        return "change-password";
    }
}
