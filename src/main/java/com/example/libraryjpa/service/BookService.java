package com.example.libraryjpa.service;

import com.example.libraryjpa.transfer.BookDetailsResponse;
import com.example.libraryjpa.transfer.BookRequest;
import com.example.libraryjpa.transfer.BookResponse;
import com.example.libraryjpa.transfer.BookSummaryResponse;
import com.example.libraryjpa.transfer.ReviewRequest;
import com.example.libraryjpa.transfer.ReviewResponse;
import com.example.libraryjpa.exception.BookNotFoundException;
import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.entity.Review;
import com.example.libraryjpa.repository.BookRepository;
import com.example.libraryjpa.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<BookResponse> findAll(String author) {
        List<Book> books = (author == null)
                ? bookRepository.findAll()
                : bookRepository.findByAuthor(author);
        return books.stream().map(BookResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return BookResponse.from(getBook(id));
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        Book book = new Book(request.title(), request.author(), request.publishedYear());
        return BookResponse.from(bookRepository.save(book));
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = getBook(id);
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPublishedYear(request.publishedYear());
        if (request.available() != null) {
            book.setAvailable(request.available());
        }
        return BookResponse.from(bookRepository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        // the reviews go too (cascade); the book_categories rows are removed by Hibernate
        bookRepository.delete(getBook(id));
    }

    @Transactional
    public ReviewResponse addReview(Long bookId, ReviewRequest request) {
        Book book = getBook(bookId);
        Review review = new Review(request.rating(), request.comment());
        book.addReview(review);                 // sets review.book (the owner side)
        Review saved = reviewRepository.save(review);
        return ReviewResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public BookDetailsResponse findDetails(Long id) {
        return BookDetailsResponse.from(getBook(id));
    }

    @Transactional(readOnly = true)
    public List<BookSummaryResponse> summary() {
        // BEFORE: bookRepository.findAll() -> 1 select for the books
        //         + 1 select for the categories of EACH book (N+1: 5 selects)
        // AFTER:  findAllWithCategories() -> 1 select with a left join
        return bookRepository.findAllWithCategories().stream()
                .map(BookSummaryResponse::from)
                .toList();
    }

    // extra: titles of the books in one category
    @Transactional(readOnly = true)
    public List<String> findTitlesByCategory(String categoryName) {
        return bookRepository.findByCategoriesName(categoryName).stream()
                .map(Book::getTitle)
                .sorted()
                .toList();
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
