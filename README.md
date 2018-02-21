# Java Bookstore

Starter project for training classes. This is a partial implementation of a sample application. It constitutes a portion of the material used in the class. The class facilitator will provide further information about what to do with it. The project is not intended for self-study, although the code may contain some useful examples. 

This version of Bookstore is based on the following technologies:

- Java 8
- JUnit 5
- Spring Boot
- JPA talking to MySQL 5.7
- Cucumber (Ruby) for API testing

# Setup 

### 1. Install MySQL 

Follow the instructions to install MySQL for your platform. [MySQL site](https://dev.mysql.com)

### 2. Clone this repository 

```shell
git clone https://github.com/neopragma/java-bookstore.git
```

### 3. Create MySQL database and bookstore user 

This creates a database named _bookstore_ and a user named _bookstore_ on localhost with a password of _bookstore_. This is the application's userid for MySQL.

```shell
mysql -u root -p < create_bookstore_database.sql 
```

### 4. See if the build works 

Install dependencies and run unit tests.

```shell 
mvn install 
mvn test 
``` 

Start the server on localhost. 

If using Maven:

```shell 
mvn spring-boot:run
```

If using Gradle:

```shell 
gradle bootRun 
```

In a browser, navigate to http://localhost:8080. Alternatively, if you have curl and jq installed, you can run: 

```shell 
curl -s http://localhost:8080 | jq .
```

If the server returns something like the following, then you're doing OK so far. 

```shell 
{
  "description": "Bookstore Service",
  "version": "v1",
  "requests": [
    {
      "http-verb": "get",
      "uri": "/",
      "description": "returns api help"
    },
    {
      "http-verb": "get",
      "uri": "/v1/books",
      "description": "returns list of all books"
    },
    {
      "http-verb": "post",
      "uri": "/v1/book",
      "description": "add a book"
    },
    {
      "http-verb": "get",
      "uri": "/v1/authors",
      "description": "returns list of all authors"
    },
    {
      "http-verb": "post",
      "uri": "/v1/author",
      "description": "add an author"
    },
    {
      "http-verb": "post",
      "uri": "/v1/book/{bookId}/author/{authorId}",
      "description": "associate an author with a book"
    }
  ]
}
```

### 5. Install Postman (optional)

Postman is handy for playing with the API interactively. [Postman site](https://www.getpostman.com/docs/postman/launching_postman/installation_and_updates)

If you choose to use Postman, you will find some saved API calls in Backup.postman_dump.json. That file can be imported into Postman. 

# User interface (optional)

This is not a UI project. The sample application is a RESTful service. A separate project can be used to explore/test/demonstrate a web-based UI for the Bookstore service. The repo for that project is here: [Bookstore UI](https://github.com/neopragma/bookstore-ui).



