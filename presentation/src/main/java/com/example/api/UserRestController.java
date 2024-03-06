package com.example.api;

import com.example.dto.LoginCommand;
import com.example.dto.LogoutCommand;
import com.example.dto.Response;
import com.example.dto.UserLoggedInEvent;
import com.example.form.LoginForm;
import com.example.security.LoginCheck;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chattings")
@Tag(name = "User")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/login")
    public Response<String> login(@RequestBody @Valid LoginForm loginForm) {
        UserLoggedInEvent login = userService.login(new LoginCommand(loginForm.getName(), loginForm.getPassword()));
        return Response.success("login complete. name : " + login.getName() + " time : " + login.getCreatedAt());
    }

    @LoginCheck
    @PostMapping("/logout")
    public Response<String> logout(HttpSession session) {
        String name = session.getAttribute("user").toString();
        userService.logout(new LogoutCommand(name));
        session.invalidate();
        return Response.success("logout complete.");
    }
}
