package com.example.cse441_music.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Artist {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("link")
    private String link;

    @SerializedName("picture")
    private String picture;

    @SerializedName("songs")
    private List<Song> songs;

    @SerializedName("nb_album")
    private int albumCount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(int albumCount) {
        this.albumCount = albumCount;
    }


    public Artist(int id, String name, String link, String picture, List<Song> songs, int albumCount) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.songs = songs;
        this.albumCount = albumCount;
    }

    // Constructor không tham số
    public Artist() {
    }
}
