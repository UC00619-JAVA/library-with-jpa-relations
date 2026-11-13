package com.example.libraryjpa.transfer;

import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.entity.Category;
import com.example.libraryjpa.entity.Review;

import java.util.List;

public record BookDetailsResponse(Long id, String title, List<String> categories,
                                  int reviewCount, double averageRating) {

    public static BookDetailsResponse from(Book book) {
        List<String> categories = book.getCategories().stream()
                .map(Category::getName)
                .sorted()
                .toList();

        double average = book.getReviews().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return new BookDetailsResponse(
                book.getId(),
                book.getTitle(),
                categories,
                book.getReviews().size(),
                average
        );
    }
}
