package com.leadingagile.bookstore.helpers;

import com.google.common.collect.ImmutableList;
import com.leadingagile.bookstore.model.Book;
import com.leadingagile.bookstore.repository.AuthorRepository;
import com.leadingagile.bookstore.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.PersistenceException;
import java.util.List;

public class BookHelper {

    public static ResponseEntity<Book> createBook(
            Book book,
            BookRepository repository) {
        HttpHeaders headers = new HttpHeaders();
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(repository.save(book));
        } catch (PersistenceException e) {
            return ResponseEntity.badRequest()
                    .headers(headers)
                    .body(null);
        }
    }

    public static ResponseEntity<List<Book>> listBooks(BookRepository repository) {
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(ImmutableList.copyOf(repository.findAll()));
    }

    public static ResponseEntity<Book> addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId,
            BookRepository bookRepository,
            AuthorRepository authorRepository) {
        Book book = bookRepository.findOne(bookId);
        book.addAuthor(authorRepository.findOne(authorId));
        bookRepository.save(book);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(book);
    }

}
