package com.example.cse441_music.Model;

public class Song {
    private String id;
    private String name;
    private String duration;
    private String artistId;
    private String artistName;
    private String albumName;
    private String albumId;
    private String licenseUrl;
    private int position;
    private String releaseDate;
    private String albumImage;
    private String audioUrl;
    private String audioDownloadUrl;
    private String proUrl;
    private String shortUrl;
    private String shareUrl;
    private String waveform;
    private String imageUrl;

    private boolean audioDownloadAllowed;



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




}
