package com.example.cse441_music.Model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;

    public Playlist(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs != null ? songs : new ArrayList<>();
    }

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

    // Thêm bài hát vào playlist
    public void addSong(Song song) {
        if (songs != null) {
            songs.add(song);
        }
    }

    // Xóa bài hát khỏi playlist
    public void removeSong(Song song) {
        if (songs != null) {
            songs.remove(song);
        }
    }

    // Lấy số lượng bài hát trong playlist
    public int getSongCount() {
        return songs != null ? songs.size() : 0;
    }

    // Kiểm tra playlist có rỗng không
    public boolean isEmpty() {
        return songs == null || songs.isEmpty();
    }

    public class Track {
        private String title;
        private Artist artist;
        private Album album;

        public String getTitle() { return title; }
        public Artist getArtist() { return artist; }
        public Album getAlbum() { return album; }
    }

    public class Artist {
        private String name;
        public String getName() { return name; }
    }

    public class Album {
        private String cover;
        public String getCover() { return cover; }
    }
}
