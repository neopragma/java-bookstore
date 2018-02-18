package com.leadingagile.bookstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neopragma.contracts.Contract;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * International Standard Book Number
 *
 * @author neopragma
 * @since 1.8
 */
@Embeddable
public class ISBN {

    private static final String MISSING_ISBN
            = "13- and/or 10-digit ISBN must be provided";
    private static final String WRONG_LENGTH_ISBN
            = "ISBN values must be 10 or 13 digits";
    private static final int THIRTEEN = 13;
    private static final int TEN = 10;

    @Size(max = TEN)
    private String isbn_10;

    @Size(max = THIRTEEN)
    private String isbn_13;

    /**
     * This is to pacify Hibernate.
     */
    ISBN() {}

    @JsonCreator
    public ISBN(@JsonProperty("isbn") String...isbn) {
        setIsbnValues(isbn);
    }

    private void setIsbnValues(String...isbn) {
        Contract.require(
            (isbn != null && isbn.length > 0 && isbn.length < 3),
            MISSING_ISBN);
        Contract.require(
            (isNotEmpty(isbn[0])),
            MISSING_ISBN);
        Contract.require(
            (isbn.length ==1 || isNotEmpty(isbn[1])),
            MISSING_ISBN);
        Contract.require(
            (isbn[0].length() == THIRTEEN || isbn[0].length() == TEN),
            WRONG_LENGTH_ISBN);
        Contract.require(
                (isbn.length == 1 ||
                    (isbn[1].length() == THIRTEEN || isbn[1].length() == TEN)),
            WRONG_LENGTH_ISBN);

        for (String value : isbn) {
            if (value.length() == THIRTEEN) {
                isbn_13 = value;
            }
            if (value.length() == TEN) {
                isbn_10 = value;
            }
        }
    }

    public String getISBN() {
        return hasISBN_13() ? isbn_13 : isbn_10;
    }

    @JsonProperty("isbn-13")
    public String getISBN_13() {
        return isbn_13;
    }

    @JsonProperty("isbn-10")
    public String getISBN_10() {
        return isbn_10;
    }

    private boolean hasISBN_13() {
        return (isbn_13 != null);
    }


}
