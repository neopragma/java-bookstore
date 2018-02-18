package com.leadingagile.bookstore;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.java8.En;
import org.junit.jupiter.api.Tag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;

public class ApiHelpStepdefs implements En {
    private HttpResponse<JsonNode> jsonResponse;
    private static final String BOOKSTORE_SERVICE_BASE_URI = "http://localhost:8080";
    private static final String EMPTY_STRING = "";
    private static final String UTF8 = "UTF-8";
    private static final String slash = "/";

    public ApiHelpStepdefs() {
        Given("^client wants to know how to interact with the Bookstore API$", () -> {

        });

        When("^client calls the base URI$", () -> {
            jsonResponse = get(BOOKSTORE_SERVICE_BASE_URI);
        });

        Then("^client receives API help$", () -> {
            try {
//                System.out.println("Response: " + jsonResponse.getBody());

                assertThat(jsonResponse.getBody().getObject().get("description"),
                        is(equalTo("Bookstore Service")));
                assertThat(jsonResponse.getBody().getObject().get("version"),
                        is(notNullValue()));
            } catch (Exception e) {
                fail("Exception \"" + e + "\" was thrown; no exception was expected");
            }
        });
    }


    private HttpResponse<JsonNode> get(String uri) {
        try {
            return Unirest.get(uri).asJson();
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
}
