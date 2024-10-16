package com.example.cse441_music.Model;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName("title")
    private String title;

    @SerializedName("artist")
    private Artist artist;

    @SerializedName("album")
    private Album album;

    @SerializedName("duration")
    private int duration;

    @SerializedName("preview")
    private String filePath;

    @SerializedName("cover")
    private String coverImage;

    @SerializedName("lyrics")
    private String lyrics;

    @SerializedName("genre") // Thêm thuộc tính genre
    private String genre; // Hoặc có thể sử dụng kiểu dữ liệu Genre nếu bạn đã định nghĩa lớp Genre

    public Song(String title, Artist artist, Album album, int duration, String filePath, String coverImage, String lyrics, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
        this.coverImage = coverImage;
        this.lyrics = lyrics;
        this.genre = genre; // Khởi tạo thuộc tính genre
    }

    public Song() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getGenre() { // Getter cho thuộc tính genre
        return genre;
    }

    public void setGenre(String genre) { // Setter cho thuộc tính genre
        this.genre = genre;
    }
}
