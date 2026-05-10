package com.b1uffer.multisessiontest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session, Authentication auth) {
        // session 에서 id 가져옴
        model.addAttribute("sessionId", session.getId());
        // authentication 에서 name 가져옴
        model.addAttribute("username", auth.getName());
        // session 생성일자
        model.addAttribute("creationTime", session.getCreationTime());
        // session 최근접속시간
        model.addAttribute("lastAccessedTime", session.getLastAccessedTime());
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/session-expired")
    public String sessionExpired(Model model) {
        model.addAttribute("msg","세션이 만료되었습니다. 다시 로그인 해주세요.");
        return "session-expired";
    }
}
