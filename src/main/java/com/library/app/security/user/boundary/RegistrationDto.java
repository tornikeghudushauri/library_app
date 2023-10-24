package com.library.app.security.user.boundary;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty
    private String username;
    @Email
    private String email;
    @NotEmpty
    private String password;
}
