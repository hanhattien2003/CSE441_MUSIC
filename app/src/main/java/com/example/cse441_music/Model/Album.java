package com.example.cse441_music.Model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Album {
    @SerializedName("id")
    private int id; // ID của album

    @SerializedName("title")
    private String title; // Tiêu đề của album

    @SerializedName("cover")
    private String cover; // Đường dẫn đến ảnh bìa album

    @SerializedName("release_date")
    private String releaseDate; // Ngày phát hành album

    @SerializedName("tracks")
    private List<Song> Songs; // Danh sách bài hát trong album

    @SerializedName("artist")
    private Artist artist; // Nghệ sĩ của album

    // Getter và setter cho các trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Song> getTracks() {
        return Songs;
    }

    public void setTracks(List<Song> Songs) {
        this.Songs = Songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    // Constructor với tất cả các trường
    public Album(int id, String title, String cover, String releaseDate, List<Song> Songs, Artist artist) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.Songs = Songs;
        this.artist = artist;
    }

    // Constructor không tham số
    public Album() {
    }
}

