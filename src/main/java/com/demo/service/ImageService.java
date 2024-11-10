package com.demo.service;

import com.demo.model.Image;
import com.demo.model.User;
import com.demo.repository.ImageRepository;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService{

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public List<Image> findAllImagesByUser(Long userId, Integer page, Integer size) {
        return imageRepository.findImageByUserId(userId,
                PageRequest.of(page, size, Sort.by("created").descending()));
    }

    public Image saveImage(Long id, MultipartFile file){
        User user = userRepository.findById(id).get();
        Image image = new Image();
        image.setUser(user);
        image.setSize(file.getSize());
        image.setWayPictures(file.getOriginalFilename());
        return imageRepository.save(image);
    }
}