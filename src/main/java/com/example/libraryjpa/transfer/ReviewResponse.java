package com.example.libraryjpa.transfer;

import com.example.libraryjpa.entity.Review;

public record ReviewResponse(Long id, int rating, String comment) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review.getId(), review.getRating(), review.getComment());
    }
}
