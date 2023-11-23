package com.example.controller;

import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.exception.NotFoundUserException;
import com.example.exception.NotMatchPasswordException;
import com.example.form.LoginForm;
import com.example.usecase.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class LoginController {
    private final LoginUseCase loginUseCase;

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return loginForm(model);
        }
        try {
            UserLoggedInEvent userLoggedInEvent = loginUseCase.login(new LoginCommand(
                    loginForm.getName(),
                    loginForm.getPassword()
            ));
            model.addAttribute("loginSuccess", true);
        } catch (RuntimeException e) {
            handleLoginException(e, model);
        }
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