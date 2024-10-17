package com.example.cse441_music.Model;

public class Genre {
    private String name;
    private String picture;

    public Genre(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
