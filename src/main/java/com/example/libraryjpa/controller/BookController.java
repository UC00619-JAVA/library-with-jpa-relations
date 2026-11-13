package com.example.libraryjpa.controller;

import com.example.libraryjpa.transfer.BookDetailsResponse;
import com.example.libraryjpa.transfer.BookRequest;
import com.example.libraryjpa.transfer.BookResponse;
import com.example.libraryjpa.transfer.BookSummaryResponse;
import com.example.libraryjpa.transfer.ReviewRequest;
import com.example.libraryjpa.transfer.ReviewResponse;
import com.example.libraryjpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // GET /api/books or /api/books?author=Robert C. Martin
    @GetMapping
    public List<BookResponse> findAll(@RequestParam(required = false) String author) {
        return bookService.findAll(author);
    }

    // GET /api/books/summary -> title + categories of every book
    @GetMapping("/summary")
    public List<BookSummaryResponse> summary() {
        return bookService.summary();
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // GET /api/books/1/details
    @GetMapping("/{id}/details")
    public BookDetailsResponse details(@PathVariable Long id) {
        return bookService.findDetails(id);
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.id())).body(created);
    }

    // POST /api/books/1/reviews -> 201
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewResponse> addReview(@PathVariable Long id,
                                                    @Valid @RequestBody ReviewRequest request) {
        ReviewResponse created = bookService.addReview(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
