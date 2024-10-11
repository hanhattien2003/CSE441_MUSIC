package com.example.cse441_music.Model;

import java.util.List;

public class Artist {
    private String name;
    private String biography;
    private List<Song> songs;
    private String genere;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Artist(String name, String biography, List<Song> songs, String genere) {
        this.name = name;
        this.biography = biography;
        this.songs = songs;
        this.genere = genere;
    }

    public Artist() {
    }
}
