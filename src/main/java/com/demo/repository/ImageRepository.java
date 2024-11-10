package com.demo.repository;

import com.demo.model.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImageByUserId(Long id, Pageable pageable);
}
