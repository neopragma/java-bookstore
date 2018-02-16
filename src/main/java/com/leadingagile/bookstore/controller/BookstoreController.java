package com.leadingagile.bookstore.controller;

import com.leadingagile.bookstore.helpers.ApiHelper;
import com.leadingagile.bookstore.helpers.AuthorHelper;
import com.leadingagile.bookstore.helpers.BookHelper;
import com.leadingagile.bookstore.model.Author;
import com.leadingagile.bookstore.model.Book;
import com.leadingagile.bookstore.repository.AuthorRepository;
import com.leadingagile.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class BookstoreController {

    @Autowired
    BookRepository bookstoreRepository;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/")
    public String apiHelp() {
        return ApiHelper.apiHelp();
    }

    @GetMapping("/v1/books")
    public ResponseEntity<List<Book>> listBooks() {
        return BookHelper.listBooks(bookstoreRepository);
    }

    @PostMapping("/v1/book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return BookHelper.createBook(book, bookstoreRepository);
    }

    @PostMapping("/v1/author")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        return AuthorHelper.createAuthor(author, authorRepository);
    }

}
