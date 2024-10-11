package com.example.cse441_music.Controller;

import com.example.cse441_music.Model.Playlist;
import com.example.cse441_music.Model.Song;

import java.util.List;

public class PlaylistController {
    private Playlist playlist;

    // Constructor nhận đối tượng Playlist
    public PlaylistController(Playlist playlist) {
        this.playlist = playlist;
    }

    // Thêm bài hát vào playlist
    public void addSong(Song song) {
        List<Song> songs = playlist.getSongs(); // Lấy danh sách bài hát hiện tại
        if (songs != null && !songs.contains(song)) { // Kiểm tra xem bài hát đã tồn tại chưa
            songs.add(song); // Thêm bài hát vào danh sách
        }
    }

    // Xóa bài hát khỏi playlist
    public void removeSong(Song song) {
        List<Song> songs = playlist.getSongs();
        if (songs != null) {
            songs.remove(song); // Xóa bài hát khỏi danh sách
        }
    }

    // Phát tất cả các bài hát trong playlist
    public void playAll() {
        List<Song> songs = playlist.getSongs(); // Lấy danh sách bài hát
        if (songs != null && !songs.isEmpty()) {
            for (Song song : songs) {
                // Chơi nhạc (giả định bạn có phương thức `play` trong lớp `Song`)
                System.out.println("Playing song: " + song.getTitle());
                // song.play(); // Nếu bạn có phương thức phát nhạc trong lớp Song
            }
        } else {
            System.out.println("Playlist is empty or null.");
        }
    }
}
