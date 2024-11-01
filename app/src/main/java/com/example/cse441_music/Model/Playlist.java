package com.example.cse441_music.Model;

import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    public Playlist(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
    public Playlist() {
    }
}
