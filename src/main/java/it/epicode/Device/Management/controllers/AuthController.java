package it.epicode.Device.Management.controllers;

import it.epicode.Device.Management.entities.User;
import it.epicode.Device.Management.payloads.LoginResponseDTO;
import it.epicode.Device.Management.payloads.NewUserDTO;
import it.epicode.Device.Management.payloads.UserLoginDTO;
import it.epicode.Device.Management.services.AuthService;
import it.epicode.Device.Management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new LoginResponseDTO(authService.authUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody NewUserDTO newUser) {
        return this.authService.save(newUser);
    }

}
