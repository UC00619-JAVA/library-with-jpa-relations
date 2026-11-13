package com.example.libraryjpa.repository;

import com.example.libraryjpa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
