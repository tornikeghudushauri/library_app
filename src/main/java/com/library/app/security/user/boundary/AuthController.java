package com.library.app.security.user.boundary;

import com.library.app.security.user.repository.entity.UserEntity;
import com.library.app.security.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());

        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "error.user", "Email already exists");
        }

        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            result.rejectValue("username", "error.user", "Username already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/books?success";
    }
}
