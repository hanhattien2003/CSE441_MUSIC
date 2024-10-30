package com.example.cse441_music.Model;

public class Song {
    private String id;
    private String name;
    private String artistName;
    private String albumName;
    private String audioUrl;
    private String imageUrl;

    public Song() {
    }

    public Song(String id, String name, String artistName, String audioUrl, String imageUrl, String albumName) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.albumName = albumName;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
