package com.example.api;

import com.example.dto.Response;
import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.form.SignupForm;
import com.example.log4j.LogRunningTime;
import com.example.service.SignUpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
public class SignUpRestController {

    private final SignUpService signUpService;

    @LogRunningTime
    @Tag(name = "sign up")
    @PostMapping("/signup")
    public Response<String> signup(@RequestBody @Validated final SignupForm signupForm) {
        UserCreatedEvent userCreatedEvent = signUpService.signup(new SignUpCommand(
                signupForm.getName(),
                signupForm.getPassword(),
                signupForm.getNickname()
        ));

        return Response.success("login complete. name : " + userCreatedEvent.getName()
                + " time : " + userCreatedEvent.getCreatedAt().toString());
    }
}
