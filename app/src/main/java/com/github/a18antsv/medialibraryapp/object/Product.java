package com.github.a18antsv.medialibraryapp.object;

public class Product {
    private int productkey;
    private String title;
    private int price;
    private String release;
    private String genre;
    private String comment;
    private String imgUrl;

    public Product(int productkey, String title, int price, String release, String genre, String comment, String imgUrl) {
        this.productkey = productkey;
        this.title = title;
        this.price = price;
        this.release = release;
        this.genre = genre;
        this.comment = comment;
        this.imgUrl = imgUrl;
    }

    public int getProductkey() {
        return productkey;
    }

    public void setProductkey(int productkey) {
        this.productkey = productkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}