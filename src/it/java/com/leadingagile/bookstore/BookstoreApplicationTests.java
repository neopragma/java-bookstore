package com.leadingagile.bookstore;

import com.leadingagile.bookstore.controller.BookstoreController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookstoreApplicationTests {

	@Test
	public void itReturnsApiHelp() {
        BookstoreController controller = new BookstoreController();
        assertThat(controller.apiHelp(),
            startsWith("{\"description\":\"Bookstore Service\",\"version\":\"v1\",\"requests\":["));
	}

}
