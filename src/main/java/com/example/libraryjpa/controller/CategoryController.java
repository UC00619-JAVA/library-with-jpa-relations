package com.example.libraryjpa.controller;

import com.example.libraryjpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final BookService bookService;

    @GetMapping("/{name}/books")
    public List<String> booksOfCategory(@PathVariable String name) {
        return bookService.findTitlesByCategory(name);
    }
}
