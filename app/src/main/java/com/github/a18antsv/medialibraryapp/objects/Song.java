package com.github.a18antsv.medialibraryapp.objects;

public class Song extends Product {
    private String label;
    private String artist;
    private int length;

    public Song(int productkey, String title, int price, String release, String genre, String comment, String label, String artist, int length) {
        super(productkey, title, price, release, genre, comment);
        this.label = label;
        this.artist = artist;
        this.length = length;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
