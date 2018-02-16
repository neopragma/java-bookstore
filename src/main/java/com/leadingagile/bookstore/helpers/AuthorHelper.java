package com.leadingagile.bookstore.helpers;

import com.leadingagile.bookstore.model.Author;
import com.leadingagile.bookstore.repository.AuthorRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.persistence.PersistenceException;

public class AuthorHelper {

    public static ResponseEntity<Author> createAuthor(
            Author author,
            AuthorRepository authorRepository) {
        HttpHeaders headers = new HttpHeaders();
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(authorRepository.save(author));
        } catch (PersistenceException e) {
            return ResponseEntity.badRequest()
                    .headers(headers)
                    .body(null);
        }
    }
}
