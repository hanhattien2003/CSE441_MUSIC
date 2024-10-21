package com.example.cse441_music.Controller;

import com.example.cse441_music.Model.Playlist;
import com.example.cse441_music.Model.Song;

import java.util.List;

public class MusicPlayerController {
    private Playlist playlist; // Danh sách phát
    private int currentIndex = -1; // Vị trí của bài hát hiện tại (-1 có nghĩa là chưa có bài hát nào được chọn)

    // Constructor nhận một Playlist
    public MusicPlayerController(Playlist playlist) {
        this.playlist = playlist;
    }

    // Lấy bài hát hiện tại
    public Song getCurrentSong() {
        if (currentIndex >= 0 && playlist.getSongs() != null && !playlist.getSongs().isEmpty()) {
            return playlist.getSongs().get(currentIndex); // Lấy bài hát tại chỉ số currentIndex
        }
        return null; // Nếu không có bài hát nào
    }

    // Đặt bài hát hiện tại
    public void setCurrentSong(Song currentSong) {
        List<Song> songs = playlist.getSongs();
        if (songs != null && songs.contains(currentSong)) {
            this.currentIndex = songs.indexOf(currentSong); // Cập nhật chỉ số hiện tại
        }
    }

    // Phát bài hát hiện tại
    public void playCurrentSong() {
        Song currentSong = getCurrentSong();
        if (currentSong != null) {
            System.out.println("Playing song: " + currentSong.getName());
            // Thực thi phương thức phát nhạc ở đây
        } else {
            // Nếu chưa có bài hát nào được chọn, có thể phát bài đầu tiên trong danh sách phát
            if (playlist.getSongs() != null && !playlist.getSongs().isEmpty()) {
                currentIndex = 0; // Đặt chỉ số bài hát hiện tại là 0
                currentSong = playlist.getSongs().get(currentIndex); // Lấy bài đầu tiên
                System.out.println("Playing first song: " + currentSong.getName());
                // Thực thi phương thức phát nhạc ở đây
            } else {
                System.out.println("No song available to play.");
            }
        }
    }

    // Dừng bài hát đang phát
    public void stop() {
        Song currentSong = getCurrentSong();
        if (currentSong != null) {
            System.out.println("Stopped song: " + currentSong.getName());
            // Thực thi phương thức dừng nhạc ở đây
        } else {
            System.out.println("No song is currently playing.");
        }
    }

    // Chuyển đến bài hát tiếp theo
    public void nextSong() {
        if (playlist.getSongs() != null && !playlist.getSongs().isEmpty()) {
            currentIndex = (currentIndex + 1) % playlist.getSongs().size(); // Di chuyển đến bài tiếp theo
            playCurrentSong(); // Phát bài hát tiếp theo
        }
    }

    // Trở về bài hát trước
    public void previousSong() {
        if (playlist.getSongs() != null && !playlist.getSongs().isEmpty()) {
            currentIndex = (currentIndex - 1 + playlist.getSongs().size()) % playlist.getSongs().size(); // Di chuyển đến bài trước
            playCurrentSong(); // Phát bài hát trước
        }
    }
}
