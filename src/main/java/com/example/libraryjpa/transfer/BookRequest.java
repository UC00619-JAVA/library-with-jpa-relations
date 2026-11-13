package com.example.libraryjpa.transfer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must have at most 100 characters")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotNull(message = "Published year is required")
        @Min(value = 1450, message = "Published year must be 1450 or later")
        @Max(value = 2100, message = "Published year must be 2100 or earlier")
        Integer publishedYear, Boolean available) {
}
