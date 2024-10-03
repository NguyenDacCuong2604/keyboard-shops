package com.example.keyboardshops.repository;

import com.example.keyboardshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
