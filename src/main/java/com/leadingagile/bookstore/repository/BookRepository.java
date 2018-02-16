package com.leadingagile.bookstore.repository;

import com.leadingagile.bookstore.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
