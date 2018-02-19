package com.leadingagile.bookstore;

import com.leadingagile.bookstore.controller.BookstoreController;
import com.leadingagile.bookstore.helpers.ApiHelper;
import com.leadingagile.bookstore.helpers.AuthorHelper;
import com.leadingagile.bookstore.model.Author;
import com.leadingagile.bookstore.repository.AuthorRepository;
import com.leadingagile.bookstore.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doReturn;

import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class BookstoreApplicationTests {

	private BookstoreController controller;
	@Mock private ApiHelper apiHelper;
	@Mock private AuthorHelper authorHelper;
	@Mock private Author expectedAuthor;
	@Mock private ResponseEntity<Author> authorResponseEntity;
	@Mock private AuthorRepository authorRepository;

	/**
	 * Can't use JUnit5 @BeforeEach with MockitoJUnitRunner as of Feb 2018.
	 */
	@Before
	public void beforeEach() {
		controller = new BookstoreController();
	}

	@Test
	public void itReturnsApiHelp() {
		controller.setApiHelper(apiHelper);
		controller.apiHelp();
		verify(apiHelper, times(1)).apiHelp();
	}

	@Test
	public void itAddsAnAuthor() {
        when(expectedAuthor.getDisplayName()).thenReturn("Mark Twain");
        when(expectedAuthor.getSurname()).thenReturn("Clemens");
        when(expectedAuthor.getGivenName()).thenReturn("Samuel");
        when(expectedAuthor.getMiddleName()).thenReturn("Tiberius");
        when(authorResponseEntity.getBody()).thenReturn(expectedAuthor);
        when(authorHelper.createAuthor(expectedAuthor, null))
				.thenReturn(authorResponseEntity);
        controller.setAuthorHelper(authorHelper);

		ResponseEntity<Author> responseEntity =
		    controller.createAuthor(expectedAuthor);

		Author actualAuthor = new Author(
		    responseEntity.getBody().getDisplayName(),
			responseEntity.getBody().getSurname(),
			responseEntity.getBody().getGivenName(),
			responseEntity.getBody().getMiddleName());
		assertThat(actualAuthor, is(equalTo(expectedAuthor)));
	}

}
