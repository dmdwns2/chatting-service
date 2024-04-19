package com.example.controller;

import com.example.log4j.LogRunningTime;
import com.example.service.UserService;
import com.example.dto.LoginCommand;
import com.example.dto.LogoutCommand;
import com.example.exception.NotFoundUserException;
import com.example.exception.NotMatchPasswordException;
import com.example.form.LoginForm;
import com.example.security.LoginCheck;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "login";
    }

    @LogRunningTime
    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult, Model model,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return loginForm(model);
        }
        try {
            userService.login(new LoginCommand(
                    loginForm.getName(),
                    loginForm.getPassword()
            ));
            model.addAttribute("loginSuccess", true);
            session.setAttribute("user", loginForm.getName());
        } catch (RuntimeException e) {
            handleLoginException(e, model);
            return "login";
        }
        return "home";
    }

    @LogRunningTime
    @LoginCheck
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        String name = session.getAttribute("user").toString();
        userService.logout(new LogoutCommand(name));
        session.invalidate();
        return "home";
    }

    private void handleLoginException(RuntimeException e, Model model) {
        if (e instanceof NotMatchPasswordException) {
            model.addAttribute("loginError", "Incorrect password");
        } else if (e instanceof NotFoundUserException) {
            model.addAttribute("loginError", "User not found");
        } else {
            model.addAttribute("loginError", e.getMessage());
        }
    }
}