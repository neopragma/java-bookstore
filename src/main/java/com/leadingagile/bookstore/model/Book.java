package com.leadingagile.bookstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neopragma.contracts.Contract;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Represents the domain concept "Book".
 *
 * @author neopragma
 * @since 1.8
 */
@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @NotBlank
    private String title;

    private BigDecimal price;

    private Integer unitsInStock;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
        joinColumns = @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"))
    private List<Author> authors;

    @Embedded
    private ISBN isbn;

    /**
     * This is to pacify Hibernate.
     */
    public Book() {}

    /**
     * This is the standard constructor
     * @param title - Title of the book
     * @param isbn - ISBN of the book
     */
    @JsonCreator
    public Book(@JsonProperty("title") String title,
                @JsonProperty("price") BigDecimal price,
                @JsonProperty("units-in-stock") Integer unitsInStock,
                @JsonProperty("isbn") String...isbn) {
        this.price = price;
        this.unitsInStock = unitsInStock;
        setTitle(title);
        this.isbn = new ISBN(isbn);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Contract.require(isNotEmpty(title),
                "Book title cannot be empty");
        Contract.require(title.length() <= 300,
                "Book title cannot exceed 300 characters in length");
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        this.authors.add(author);
    }

    /**
     * @return the 13-digit ISBN if there is one,
     * otherwise the 10-digit ISBN.
     */
    public String getISBN() {
        return isbn.getISBN();
    }

    /**
     * @return the 13-digit ISBN or null.
     */
    public String getISBN_13() {
        return isbn.getISBN_13();
    }

    /**
     * @return the 10-digit ISBN or null.
     */
    public String getISBN_10() {
        return isbn.getISBN_10();
    }

    /**
     * @param isbns - one or two ISBN values as Strings,
     *              either 13-digit, 10-digit, or both.
     */
    public void setISBN(String...isbns) {
        isbn = new ISBN(isbns);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("units-in-stock")
    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

}
