package com.example.controller;

import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.form.SignupForm;
import com.example.usecase.SignUpUseCase;
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
public class SignupController {
    private final SignUpUseCase signUpUseCase;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        if (!model.containsAttribute("signupForm")) {
            model.addAttribute("signupForm", new SignupForm());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated SignupForm signupForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return signupForm(model);
        }

        UserCreatedEvent userCreatedEvent = signUpUseCase.signup(new SignUpCommand(
                signupForm.getName(),
                signupForm.getPassword(),
                signupForm.getNickname()
        ));
        model.addAttribute("name", userCreatedEvent.getName());
        return "redirect:/chattings/login";
    }
}