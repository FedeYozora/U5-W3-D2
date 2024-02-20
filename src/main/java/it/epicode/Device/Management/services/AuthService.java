package it.epicode.Device.Management.services;

import it.epicode.Device.Management.entities.User;
import it.epicode.Device.Management.payloads.NewUserDTO;
import it.epicode.Device.Management.payloads.UserLoginDTO;
import it.epicode.Device.Management.repos.UserRepo;
import it.epicode.Device.Management.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder bcrypt;


    public String authUserAndGenerateToken(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new RuntimeException("Credenziali sbagliate");
        }
    }

    public User save(NewUserDTO payload) {
        userRepo.findByEmail(payload.email()).ifPresent(user -> {
            throw new RuntimeException("L'email é giá in uso");
        });
        User newUser = new User();
        newUser.setUsername(payload.username());
        newUser.setName(payload.name());
        newUser.setSurname(payload.surname());
        newUser.setEmail(payload.email());
        newUser.setAvatar(payload.avatar());
        newUser.setPassword(bcrypt.encode(payload.password()));
        return userRepo.save(newUser);
    }
}
