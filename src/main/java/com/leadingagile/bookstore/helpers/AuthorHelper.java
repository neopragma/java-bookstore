package com.leadingagile.bookstore.helpers;

import com.google.common.collect.ImmutableList;
import com.leadingagile.bookstore.model.Author;
import com.leadingagile.bookstore.repository.AuthorRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

public class AuthorHelper {

    public ResponseEntity<Author> createAuthor(
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

    public ResponseEntity<List<Author>> listAuthors(AuthorRepository repository) {
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(ImmutableList.copyOf(repository.findAll()));
    }

}
