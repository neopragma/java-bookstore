package com.leadingagile.bookstore.model;

import com.neopragma.contracts.ContractViolationException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("micro")
class ISBNTest {
    private static final String EMPTY_STRING = "";
    private static final String MISSING_ISBN
            = "13- and/or 10-digit ISBN must be provided";
    private static final String WRONG_LENGTH_ISBN
            = "ISBN values must be 10 or 13 digits";
    private static final String TEN_DIGIT_ISBN = "1234567890";
    private static final String THIRTEEN_DIGIT_ISBN = "1234567890123";
    private static final String SHORT_ISBN = "12345";

    @Test void it_rejects_null_isbn_list() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(null));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_empty_isbn_list() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {}));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_first_entry_with_null_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] { null, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_second_entry_with_null_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, null }));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_first_entry_with_empty_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] { EMPTY_STRING, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_second_entry_with_empty_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, EMPTY_STRING }));
        assertThat(t.getMessage(),
                endsWith(MISSING_ISBN));
    }

    @Test void it_rejects_first_entry_with_wrong_length() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {SHORT_ISBN, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage(),
                endsWith(WRONG_LENGTH_ISBN));
    }


    @Test void it_rejects_second_entry_with_wrong_length() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, SHORT_ISBN}));
        assertThat(t.getMessage(),
                endsWith(WRONG_LENGTH_ISBN));
    }

    @Test void it_returns_the_13_digit_isbn_when_not_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN(), is(equalTo(THIRTEEN_DIGIT_ISBN)));
    }

    @Test void it_returns_the_13_digit_isbn_when_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN_13(), is(equalTo(THIRTEEN_DIGIT_ISBN)));
    }

    @Test void it_returns_the_10_digit_isbn_when_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN_10(), is(equalTo(TEN_DIGIT_ISBN)));
    }

}
