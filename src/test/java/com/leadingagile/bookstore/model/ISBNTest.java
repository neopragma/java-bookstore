package com.leadingagile.bookstore.model;

import com.neopragma.contracts.ContractViolationException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ISBNTest {
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
                ()-> new ISBN((String[]) null));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_empty_isbn_list() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {}));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_first_entry_with_null_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] { null, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_second_entry_with_null_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, null }));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_first_entry_with_empty_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] { EMPTY_STRING, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_second_entry_with_empty_isbn() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, EMPTY_STRING }));
        assertThat(t.getMessage()).endsWith(MISSING_ISBN);
    }

    @Test void it_rejects_first_entry_with_wrong_length() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {SHORT_ISBN, TEN_DIGIT_ISBN}));
        assertThat(t.getMessage()).endsWith(WRONG_LENGTH_ISBN);
    }


    @Test void it_rejects_second_entry_with_wrong_length() {
        Throwable t = assertThrows(ContractViolationException.class,
                ()-> new ISBN(new String[] {TEN_DIGIT_ISBN, SHORT_ISBN}));
        assertThat(t.getMessage()).endsWith(WRONG_LENGTH_ISBN);
    }

    @Test void it_returns_the_13_digit_isbn_when_not_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN()).isEqualTo(THIRTEEN_DIGIT_ISBN);
    }

    @Test void it_returns_the_13_digit_isbn_when_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN_13()).isEqualTo(THIRTEEN_DIGIT_ISBN);
    }

    @Test void it_returns_the_10_digit_isbn_when_specified() {
        ISBN isbn = new ISBN(THIRTEEN_DIGIT_ISBN, TEN_DIGIT_ISBN);
        assertThat(isbn.getISBN_10()).isEqualTo(TEN_DIGIT_ISBN);
    }

}
