package it.epicode.Device.Management.controllers;

import it.epicode.Device.Management.entities.User;
import it.epicode.Device.Management.exceptions.BadRequestException;
import it.epicode.Device.Management.payloads.NewUserDTO;
import it.epicode.Device.Management.services.AuthService;
import it.epicode.Device.Management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findByID(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return authService.save(body);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIDAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return userService.findByIDAndUpdate(id, body);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIDAndDelete(@PathVariable UUID id) {
        userService.findByIDAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadFile(@RequestParam("avatar") MultipartFile body, @PathVariable UUID id) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return userService.uploadImg(body, id);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        // @AuthenticationPrincipal mi consente di accedere all'utente attualmente autenticato
        // Posso fare ciò perché precedentemente abbiamo estratto l'id utente dal token e abbiamo cercato
        // nel db l'utente, aggiungendolo poi al Security Context
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewUserDTO updatedUser) {
        return this.userService.findByIDAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByIDAndDelete(currentAuthenticatedUser.getId());
    }
}
