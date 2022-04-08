package io.cucumber.skeleton;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepDefinitions {

    private BookSearch bookSearch;
    private List<Book> results;

    /*
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDateTime iso8601Date(String year, String month, String day){
		return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
	}
    */

    @Given("a set of books initialized by the following table")
    public void a_set_of_books_initialized_by_the_following_table(List<Map<String, String>> dataTable) {
        bookSearch = new BookSearch();

        for (Map<String, String> row : dataTable) {
            Book book = new Book(
                row.get("title"),
                row.get("author"),
                LocalDate.parse(row.get("published"))
            );
            String[] categories = row.get("categories").split(",\\s");
            for (String cat : categories) {
                book.addCategory(cat);
            }
            bookSearch.addBook(book);
        }
    }

    @When("a customer searches for books written by {string}")
    public void a_customer_searches_for_books_written_by(String author) {
        results = bookSearch.searchByAuthor(author);
    }

    @When("a customer searches for books with the category {string}")
    public void a_customer_searches_for_books_with_the_category(String category) {
        results = bookSearch.searchByCategory(category);
    }

    @When("a customer searches for books published between {int} and {int}")
    public void a_customer_searches_for_books_published_between_and(Integer year_start, Integer year_end) {
        results = bookSearch.searchByYear(year_start, year_end);
    }

    @Then("{int} book(s) should be found")
    public void books_should_be_found(Integer total) {
        assertEquals(total, results.size());
    }

    @Then("the book title(s) should be {string}")
    public void the_book_titles_should_be(String bookTitles) {
        ArrayList<String> actualTitles = new ArrayList<>();
        for (Book book : results)
            actualTitles.add(book.getTitle());

        ArrayList<String> expectedTitles = new ArrayList<>();
        for (String title : bookTitles.split(",\\s"))
            expectedTitles.add(title);

        assertAll(
            () -> {assertEquals(expectedTitles.size(), actualTitles.size());},
            () -> {assertTrue(actualTitles.containsAll(expectedTitles));}
        );
    }
}
