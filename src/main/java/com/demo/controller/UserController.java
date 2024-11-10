package com.demo.controller;

import com.demo.model.User;
import com.demo.service.StorageService;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @GetMapping("/moder")
    public String listUploadedFiles() {

        List<String> allFiles = storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(UserController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .toList();
        return allFiles.toString();
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PutMapping("moder/{id}")
    public Optional<User> isBlocked(@PathVariable Long id) {
        User user = userService.findById(id).get();
        if (!user.getBlocked()) {
            user.setBlocked(true);
        }else {
            user.setBlocked(false);
        }
        userService.save(user);
        return Optional.of(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            User savedUser = userService.save(user);

            if (savedUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/moder/list")
    public List<User> getUsers() {
        return userService.findAll();
    }
}