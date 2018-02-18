package com.leadingagile.bookstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.leadingagile.bookstore.model.Author;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.json.JSONObject;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookstoreStepdefs implements En {
    private HttpResponse<JsonNode> jsonResponse;
    private static final String BOOKSTORE_SERVICE_BASE_URI
            = "http://localhost:8080";
    private static final String EMPTY_STRING = "";
    private static final String UTF8 = "UTF-8";
    private static final String slash = "/";

    private Author expectedAuthor;
    private Author actualAuthor;

    public BookstoreStepdefs() {
        setObjectMapper();

        Given("^no authors are defined$", () -> {});

        When("^I add an author with display name (.*), surname (.*), given name (.*), and middle name (.*)$",
                (String displayName,
                 String surname,
                 String givenName,
                 String middleName) -> {
            expectedAuthor = new Author(displayName, surname, givenName, middleName);
        try {
             HttpResponse<Author> jsonResponse
                 = Unirest.post(BOOKSTORE_SERVICE_BASE_URI + "/v1/author")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(expectedAuthor)
                    .asObject(Author.class);                actualAuthor = jsonResponse.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Then("^the system will return information about that author$", () -> {
            assertThat(actualAuthor, is(equalTo(expectedAuthor)));
        });
    }

    private HttpResponse<JsonNode> get(String uriPath) {
        try {
            return Unirest.get(uriPath).asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
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
