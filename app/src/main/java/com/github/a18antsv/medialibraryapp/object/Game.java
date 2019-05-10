package com.github.a18antsv.medialibraryapp.object;

public class Game extends Product {
    private String platform;
    private String publisher;
    private String developer;
    private int age;

    public Game(int productkey, String title, int price, String release, String genre, String comment, String imgUrl, String platform, String publisher, String developer, int age) {
        super(productkey, title, price, release, genre, comment, imgUrl);
        this.platform = platform;
        this.publisher = publisher;
        this.developer = developer;
        this.age = age;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
