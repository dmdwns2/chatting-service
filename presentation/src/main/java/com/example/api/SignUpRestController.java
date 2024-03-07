package com.example.api;

import com.example.dto.Response;
import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.form.SignupForm;
import com.example.usecase.SignUpUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
@Tag(name = "Signup")
public class SignUpRestController {

    private final SignUpUseCase signUpUseCase;

    @Tag(name = "sign up")
    @PostMapping("/signup")
    public Response<String> signup(@RequestBody @Validated SignupForm signupForm) {
        UserCreatedEvent userCreatedEvent = signUpUseCase.join(new SignUpCommand(
                signupForm.getName(),
                signupForm.getPassword(),
                signupForm.getNickname()
        ));

        return Response.success("login complete. name : " + userCreatedEvent.getName()
                + " time : " + userCreatedEvent.getCreatedAt().toString());
    }
}
