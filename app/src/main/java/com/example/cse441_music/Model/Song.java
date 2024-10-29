package com.example.cse441_music.Model;

public class Song {
    private String id;
    private String name;
    private String artistName;
    private String albumName;
    private String audioUrl;
    private String imageUrl;
    private String title;
    private Artist artist;
    private Album album;

    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public Album getAlbum() { return album; }

    public Song(String id, String name, String artistName, String audioUrl, String imageUrl, String albumName) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.albumName = albumName;

    }
    public class Artist {
        private String name;
        public String getName() { return name; }
    }

    public class Album {
        private String cover;
        public String getCover() { return cover; }
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
}
