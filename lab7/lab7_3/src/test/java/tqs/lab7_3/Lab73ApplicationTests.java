package tqs.lab7_3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class Lab73ApplicationTests {

	@SuppressWarnings("rawtypes")
	@Container
	static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
		.withUsername("test")
		.withPassword("password")
		.withDatabaseName("test");

	@Autowired
	private BookRepository repository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@Test
	@Order(1)
	void insertBook() {
		Book book = new Book();
		book.setName("O Ano da Morte de Ricardo Reis");
		book.setAuthor("José Saramago");
		repository.saveAndFlush(book);

		Book res = repository.findById(book.getId()).get();
		assertEquals("O Ano da Morte de Ricardo Reis", res.getName());
		assertEquals("José Saramago", res.getAuthor());
	}

	@Test
	@Order(2)
	void readBooks() {
		List<Book> res = repository.findAll();
		List<String> names = new ArrayList<>();
		res.forEach((Book b) -> names.add(b.getName()));

		assertEquals(3, res.size());
		assertTrue(names.containsAll(Arrays.asList("Livro do Desassossego", "Os Maias", "Os Lusíadas")));
	}

}
