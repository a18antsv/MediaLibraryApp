package com.github.a18antsv.medialibraryapp.object;

public class Movie extends Product {
    private int length;
    private int age;
    private int rating;
    private String company;

    public Movie(int productkey, String title, int price, String release, String genre, String comment, int length, int age, int rating, String company) {
        super(productkey, title, price, release, genre, comment);
        this.length = length;
        this.age = age;
        this.rating = rating;
        this.company = company;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
