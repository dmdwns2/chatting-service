package com.example.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class LoginForm implements Serializable {
    @NotBlank
    @Length(min = 3, max = 20)
    private String name;

    @NotBlank
    @Length(min = 4, max = 50)
    private String password;
}