package io.cucumber.skeleton;

import java.util.ArrayList;
import java.util.List;

public class BookSearch {
    
    private List<Book> books;

    public BookSearch() {
        books = new ArrayList<Book>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        books.forEach((Book book) -> {
            if (book.getAuthor().equals(author)) results.add(book);
        });
        return results;
    }

    public List<Book> searchByCategory(String category) {
        List<Book> results = new ArrayList<>();
        books.forEach((Book book) -> {
            if (book.getCategories().contains(category)) results.add(book);
        });
        return results;
    }

    public List<Book> searchByYear(Integer year_start, Integer year_end) {
        List<Book> results = new ArrayList<>();
        books.forEach((Book book) -> {
            if (book.getPublishedDate().getYear() >= year_start && book.getPublishedDate().getYear() <= year_end)
                results.add(book);
        });
        return results;
    }

}
