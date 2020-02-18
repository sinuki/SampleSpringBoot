package com.parksw.app.auth.controller;

import com.parksw.app.auth.dto.LoginForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class LoginController {

    @GetMapping("login")
    public String login(Authentication auth, LoginForm loginForm, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/secret";
        }
        model.addAttribute("loginForm", loginForm);
        return "auth/login";
    }
}
