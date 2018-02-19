package com.leadingagile.bookstore.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ApiHelper {

    private static final String[][] helpData = {
        { "get", "/", "returns api help" },
        { "get", "/v1/books", "returns list of all books" },
        { "post", "/v1/book", "add a book" },
        { "get", "/v1/authors", "returns list of all authors" },
        { "post", "/v1/author", "add an author" },
        { "post", "/v1/book/{bookId}/author/{authorId}",
            "associate an author with a book"}
    };

    public String apiHelp() {
        JsonObject message = new JsonObject();
        message.addProperty("description", "Bookstore Service");
        message.addProperty("version", "v1");

        JsonArray requests = new JsonArray(helpData.length);
        for (int i = 0 ; i < helpData.length ; i++) {
            JsonObject requestData = new JsonObject();
            requestData.addProperty("http-verb", helpData[i][0]);
            requestData.addProperty("uri", helpData[i][1]);
            requestData.addProperty("description", helpData[i][2]);
            requests.add(requestData);
        }
        message.add("requests", requests);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(message);
    }
}
