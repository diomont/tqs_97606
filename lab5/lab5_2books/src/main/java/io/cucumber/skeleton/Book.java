package io.cucumber.skeleton;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Book {
    
    private String title;
    private String author;
    private Set<String> categories;
    private LocalDate publishedDate;

    public Book(String title, String author, LocalDate date) {
        this.title = title;
        this.author = author;
        this.publishedDate = date;
        categories = new HashSet<String>();
    }

    public Book(String title, String author, LocalDate date, Set<String> categories) {
        this.title = title;
        this.author = author;
        this.publishedDate = date;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", categories=" + categories + ", publishedDate=" + publishedDate + ", title="
                + title + "]";
    }
}
