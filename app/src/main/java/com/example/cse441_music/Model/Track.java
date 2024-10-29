package com.example.cse441_music.Model;

public class Track {
    private String id;
    private int position;
    private String name;
    private int duration;
    private String licenseUrl;
    private String audioUrl;
    private String audioDownloadUrl;
    private boolean audioDownloadAllowed;

    // Constructor, getters, and setters

    public Track(String id, int position, String name, int duration, String licenseUrl, String audioUrl, String audioDownloadUrl, boolean audioDownloadAllowed) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.duration = duration;
        this.licenseUrl = licenseUrl;
        this.audioUrl = audioUrl;
        this.audioDownloadUrl = audioDownloadUrl;
        this.audioDownloadAllowed = audioDownloadAllowed;
    }

    public String getId() { return id; }
    public int getPosition() { return position; }
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public String getLicenseUrl() { return licenseUrl; }
    public String getAudioUrl() { return audioUrl; }
    public String getAudioDownloadUrl() { return audioDownloadUrl; }
    public boolean isAudioDownloadAllowed() { return audioDownloadAllowed; }

    // Add setters if necessary
}
