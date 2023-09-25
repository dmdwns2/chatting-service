package com.example.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class SignupForm implements Serializable {
    @NotBlank
    @Length(min = 3, max = 20)
    private String name;

    @NotBlank
    @Length(min = 4, max = 50)
    private String password;

    @NotBlank
    @Length(min = 1, max = 8)
    private String nickname;
}

