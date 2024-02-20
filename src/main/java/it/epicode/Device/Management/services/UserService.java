package it.epicode.Device.Management.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.Device.Management.entities.User;
import it.epicode.Device.Management.exceptions.NotFoundException;
import it.epicode.Device.Management.payloads.NewUserDTO;
import it.epicode.Device.Management.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Cloudinary cloudinary;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepo.findAll(pageable);
    }

    public User findById(UUID uuid) {
        return userRepo.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public User findByIDAndUpdate(UUID uuid, @RequestBody NewUserDTO body) {
        User user = userRepo.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        user.setUsername(body.username());
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setAvatar(body.avatar());
        return userRepo.save(user);
    }

    public void findByIDAndDelete(UUID uuid) {
        User user = this.findById(uuid);
        userRepo.delete(user);
    }

    public String uploadImg(MultipartFile file, UUID uuid) throws IOException {
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get(("url"));
        User user = userRepo.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        user.setAvatar(url);
        userRepo.save(user);
        return url;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException());
    }
}
