package com.github.a18antsv.medialibraryapp.object;

public class Book extends Product {
    private String publisher;
    private int pages;
    private String type;
    private String isbn;

    public Book(int productkey, String title, int price, String release, String genre, String comment, String publisher, int pages, String type, String isbn) {
        super(productkey, title, price, release, genre, comment);
        this.publisher = publisher;
        this.pages = pages;
        this.type = type;
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
