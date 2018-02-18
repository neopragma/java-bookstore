package com.leadingagile.bookstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leadingagile.bookstore.model.Author;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import cucumber.api.java8.En;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BookstoreStepdefs implements En {
    private HttpResponse<JsonNode> jsonResponse;
    private static final String BOOKSTORE_SERVICE_BASE_URI
            = "http://localhost:8080";
    private static final String API_VERSION = "/v1";
    private static final String VERSIONED_BASE_URI = BOOKSTORE_SERVICE_BASE_URI + API_VERSION;
    private static final String EMPTY_STRING = "";
    private static final String UTF8 = "UTF-8";
    private static final String slash = "/";
    private static final int INVALID = -1;

    private List<Author> authorList;
    private Author expectedAuthor;
    private Author actualAuthor;
    private int expectedCount;
    private int actualCount;

    public BookstoreStepdefs() {
        setObjectMapper();

        Given("^no authors are defined$", () -> {});

        Given("^(\\d+) authors are defined$", (Integer count) -> {
            expectedCount = count;
            actualCount = INVALID;
        });

        When("^I add an author with display name (.*), surname (.*), given name (.*), and middle name (.*)$",
                (String displayName,
                 String surname,
                 String givenName,
                 String middleName) -> {
            expectedAuthor = new Author(displayName, surname, givenName, middleName);
            try {
                HttpResponse<Author> jsonResponse
                    = Unirest.post(VERSIONED_BASE_URI + "/author")
                       .header("accept", "application/json")
                       .header("content-type", "application/json")
                       .body(expectedAuthor)
                       .asObject(Author.class);
                actualAuthor = jsonResponse.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        When("^I request a list of authors$", ()-> {
            try {
                HttpResponse<Author[]> jsonResponse
                        = Unirest.get(VERSIONED_BASE_URI + "/authors")
                    .header("accept", "application/json")
                    .asObject(Author[].class);

                actualCount = jsonResponse.getBody().length;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });

        Then("^the system will return information about that author$", () -> {
            assertThat(actualAuthor, is(equalTo(expectedAuthor)));
        });

        Then("^I receive a list of the authors$", () -> {
            assertThat(actualCount, is(equalTo(expectedCount)));
        });

    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void setObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }
}
