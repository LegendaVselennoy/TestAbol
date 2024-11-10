package com.demo.controller;

import com.demo.model.Image;
import com.demo.service.ImageService;
import com.demo.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;
    private final StorageService storageService;

    @PostMapping("/{id}")
    public ResponseEntity<String> save(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
            service.saveImage(id,file);
            storageService.store(file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public List<Image> findAll(@PathVariable Long id,
                               @RequestParam(defaultValue = "5") Integer size,
                               @RequestParam(defaultValue = "0") Integer page) {
        return service.findAllImagesByUser(id,page,size);
    }
}