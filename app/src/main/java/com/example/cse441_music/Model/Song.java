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

    public Song() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }

    public void setAudioDownloadUrl(String audioDownloadUrl) {
        this.audioDownloadUrl = audioDownloadUrl;
    }

    public String getProUrl() {
        return proUrl;
    }

    public void setProUrl(String proUrl) {
        this.proUrl = proUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWaveform() {
        return waveform;
    }

    public void setWaveform(String waveform) {
        this.waveform = waveform;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAudioDownloadAllowed() {
        return audioDownloadAllowed;
    }

    public void setAudioDownloadAllowed(boolean audioDownloadAllowed) {
        this.audioDownloadAllowed = audioDownloadAllowed;
    }
}
