package com.example.cse441_music.Model;

public class Album {
    private String id;
    private String name;
    private String releaseDate;
    private String artistId;
    private String artistName;
    private String imageUrl;
    private String zipUrl;
    private String shortUrl;
    private String shareUrl;
    private boolean zipAllowed;

    public Album(String id, String name, String releaseDate, String artistId, String artistName, String imageUrl, String zipUrl, String shortUrl, String shareUrl, boolean zipAllowed) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.artistId = artistId;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
        this.zipUrl = zipUrl;
        this.shortUrl = shortUrl;
        this.shareUrl = shareUrl;
        this.zipAllowed = zipAllowed;
    }

    // Getters and Setters

    public String getId() { return id; }
    public String getName() { return name; }
    public String getReleaseDate() { return releaseDate; }
    public String getArtistId() { return artistId; }
    public String getArtistName() { return artistName; }
    public String getImageUrl() { return imageUrl; }
    public String getZipUrl() { return zipUrl; }
    public String getShortUrl() { return shortUrl; }
    public String getShareUrl() { return shareUrl; }
    public boolean isZipAllowed() { return zipAllowed; }
}
