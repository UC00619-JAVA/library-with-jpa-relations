package com.example.libraryjpa;

import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.entity.Category;
import com.example.libraryjpa.entity.Review;
import com.example.libraryjpa.repository.BookRepository;
import com.example.libraryjpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (bookRepository.count() > 0) {
            return;
        }

        // NEW (session 15): categories first
        Category programming = new Category("Programming");
        Category bestPractices = new Category("Best practices");
        Category java = new Category("Java");
        categoryRepository.saveAll(List.of(programming, bestPractices, java));

        Book cleanCode = new Book("Clean Code", "Robert C. Martin", 2008);
        cleanCode.addCategory(programming);
        cleanCode.addCategory(bestPractices);
        cleanCode.addReview(new Review(5, "A classic"));
        cleanCode.addReview(new Review(4, "Long but useful"));

        Book cleanCoder = new Book("The Clean Coder", "Robert C. Martin", 2011);
        cleanCoder.addCategory(bestPractices);

        Book effectiveJava = new Book("Effective Java", "Joshua Bloch", 2018);
        effectiveJava.addCategory(java);
        effectiveJava.addCategory(bestPractices);
        effectiveJava.addReview(new Review(5, "Must read"));

        Book headFirstJava = new Book("Head First Java", "Kathy Sierra", 2005);
        headFirstJava.addCategory(java);
        headFirstJava.addCategory(programming);

        // cascade: saving the books also saves their reviews
        bookRepository.saveAll(List.of(cleanCode, cleanCoder, effectiveJava, headFirstJava));

        log.info("Loaded {} books and {} categories",
                bookRepository.count(), categoryRepository.count());
    }
}
