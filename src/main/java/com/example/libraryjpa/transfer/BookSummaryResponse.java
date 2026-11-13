package com.example.libraryjpa.transfer;

import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.entity.Category;

import java.util.List;

public record BookSummaryResponse(String title, List<String> categories) {

    public static BookSummaryResponse from(Book book) {
        List<String> categories = book.getCategories().stream()
                .map(Category::getName)
                .sorted()
                .toList();

        return new BookSummaryResponse(book.getTitle(), categories);
    }
}
