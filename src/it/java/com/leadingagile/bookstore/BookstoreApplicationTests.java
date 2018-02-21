package com.leadingagile.bookstore;

import com.leadingagile.bookstore.controller.BookstoreController;
import com.leadingagile.bookstore.helpers.ApiHelper;
import com.leadingagile.bookstore.helpers.AuthorHelper;
import com.leadingagile.bookstore.model.Author;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class BookstoreApplicationTests {

	private BookstoreController controller;
	@Mock private ApiHelper apiHelper;
	@Mock private AuthorHelper authorHelper;
	@Mock private Author expectedAuthor;
	@Mock private ResponseEntity<Author> authorResponseEntity;

	/**
	 * Can't use JUnit5 @BeforeEach with MockitoJUnitRunner as of Feb 2018.
	 */
	@Before	public void beforeEach() {
		controller = new BookstoreController();
	}

	@Test public void controller_invokes_apihelper_to_get_api_help() {
		controller.setApiHelper(apiHelper);
		controller.apiHelp();
		verify(apiHelper, times(1))
			.apiHelp();
	}

	@Test public void controller_invokes_author_helper_to_create_author() {
        controller.setAuthorHelper(authorHelper);
		ResponseEntity<Author> responseEntity =
		    controller.createAuthor(expectedAuthor);
		verify(authorHelper, times(1))
			.createAuthor(expectedAuthor, null);
	}

	@Test public void controller_invokes_author_helper_to_get_list_of_authors() {
		controller.setAuthorHelper(authorHelper);
		ResponseEntity<List<Author>> responseEntity =
				controller.listAuthors();
		verify(authorHelper, times(1))
				.listAuthors(null);
	}

}
