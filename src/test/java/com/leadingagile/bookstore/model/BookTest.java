package com.leadingagile.bookstore.model;

import com.neopragma.contracts.ContractViolationException;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private final static String EMPTY_STRING = "";
    private final static String testISBN = "9781617291357";
    private final static String testTitle = "My Fine Book";
    private final static BigDecimal testPrice
        = new BigDecimal("20.00");
    private final static Integer testUnitsInStock = 2;

    @Test void it_creates_a_new_book_with_default_title() {
        Book book = standardBook();
        assertThat(book.getTitle(), is(equalTo(testTitle)));
        assertThat(book.getISBN(), is(equalTo(testISBN)));
        assertThat(book.getPrice(), is(equalTo(testPrice)));
        assertThat(book.getUnitsInStock(), is(equalTo(testUnitsInStock)));
    }

    @Test void it_creates_a_new_book_with_title_300_characters_long() {
        Book book = new Book(
                maxLengthTitle(),
                testPrice,
                testUnitsInStock,
                testISBN);
        assertThat(book.getTitle(), is(equalTo(maxLengthTitle())));
    }

    @Test void it_rejects_a_null_book_title() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new Book(
                        null,
                        testPrice,
                        testUnitsInStock,
                        testISBN));
        assertThat(t.getMessage(), endsWith("Book title cannot be empty"));
    }

    @Test void it_rejects_an_empty_book_title() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new Book(
                        EMPTY_STRING,
                        testPrice,
                        testUnitsInStock,
                        testISBN));
        assertThat(t.getMessage(), endsWith("Book title cannot be empty"));
    }

    @Test void it_rejects_a_book_title_longer_than_300_characters() {
        String titleText = maxLengthTitle() + "X";
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new Book(
                        titleText,
                        testPrice,
                        testUnitsInStock,
                        testISBN));
        assertThat(t.getMessage(), endsWith("Book title cannot exceed 300 characters in length"));
    }

    @Test void an_author_can_be_added_to_a_book() {
        Author author = new Author("Dr. Seuss", "Adams", "John", "Quincy");
        Book book = new Book(
                testTitle,
                testPrice,
                testUnitsInStock,
                testISBN);
        book.addAuthor(author);
        assertThat(book.getAuthors().get(0).getDisplayName(), is(equalTo("Dr. Seuss")));
    }

    private Book standardBook() {
        return new Book(
                testTitle,
                testPrice,
                testUnitsInStock,
                testISBN);
    }
    private String maxLengthTitle() {
        StringBuilder titleText = new StringBuilder();
        for (int i = 0 ; i < 10 ; i++) {
            titleText.append("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        return titleText.toString();
    }
}
