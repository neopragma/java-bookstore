package com.leadingagile.bookstore.repository;

import com.leadingagile.bookstore.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
