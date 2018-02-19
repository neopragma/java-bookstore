package com.leadingagile.bookstore.controller;

import com.leadingagile.bookstore.helpers.ApiHelper;
import com.leadingagile.bookstore.helpers.AuthorHelper;
import com.leadingagile.bookstore.helpers.BookHelper;
import com.leadingagile.bookstore.model.Author;
import com.leadingagile.bookstore.model.Book;
import com.leadingagile.bookstore.repository.AuthorRepository;
import com.leadingagile.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class BookstoreController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private ApiHelper apiHelper;
    private AuthorHelper authorHelper;

    /**
     * @return API help as a JSON document
     */
    @GetMapping("/")
    public String apiHelp() {
        return apiHelper().apiHelp();
    }

    /**
     * @return List of all books
     */
    @GetMapping("/v1/books")
    public ResponseEntity<List<Book>> listBooks() {
        return BookHelper.listBooks(bookRepository);
    }

    /**
     * Add a book
     * @param book to add
     * @return the book entity that was added
     */
    @PostMapping("/v1/book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return BookHelper.createBook(book, bookRepository);
    }

    /**
     * @return List of all authors
     */
    @GetMapping("/v1/authors")
    public ResponseEntity<List<Author>> listAuthors() {
        return authorHelper().listAuthors(authorRepository);
    }

    /**
     * Add an author
     * @param author to add
     * @return the author entity that was added
     */
    @PostMapping("/v1/author")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        return authorHelper().createAuthor(author, authorRepository);
    }

    @PostMapping("/v1/book/{bookId}/author/{authorId}")
    public ResponseEntity<Book> addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {
        return BookHelper.addAuthorToBook(
                bookId,
                authorId,
                bookRepository,
                authorRepository);
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public ApiHelper apiHelper() {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
        }
        return apiHelper;
    }

    public void setApiHelper(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public AuthorHelper authorHelper() {
        if (authorHelper == null) {
            authorHelper = new AuthorHelper();
        }
        return authorHelper;
    }

    public void setAuthorHelper(AuthorHelper authorHelper) {
        this.authorHelper = authorHelper;
    }

}
