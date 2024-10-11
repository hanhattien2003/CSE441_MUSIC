package com.example.cse441_music.Model;

import java.util.List;

public class MusicPlayer {
    private List<Song> playlist;  // Danh sách bài hát
    private int currentIndex;     // Vị trí hiện tại trong danh sách
    private int volume;
    private boolean isPlaying;

    public List<Song> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Song> playlist) {
        this.playlist = playlist;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public MusicPlayer(List<Song> playlist, int currentIndex, int volume, boolean isPlaying) {
        this.playlist = playlist;
        this.currentIndex = currentIndex;
        this.volume = volume;
        this.isPlaying = isPlaying;
    }

    public MusicPlayer() {
    }
}


