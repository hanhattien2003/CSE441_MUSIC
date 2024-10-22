package com.example.cse441_music.Controller;

import com.example.cse441_music.APIService.ApiService;
import com.example.cse441_music.APIService.Song.SongService;
import com.example.cse441_music.Model.Song;

import java.util.List;

public class SongController {
    private ApiService apiService;
    private SongService songService;

    public SongController() {
        this.apiService = new ApiService();
        this.songService = new SongService();
    }

    // Phương thức mới hỗ trợ phân trang
    public List<Song> getSongs(String query, int page, int songsPerPage) throws Exception {
        // Gọi ApiService với các tham số trang và số lượng bài hát
        String jsonResponse = apiService.fetchSongs(query, page, songsPerPage);
        return songService.parseJson(jsonResponse); // Phân tích JSON và trả về danh sách bài hát
    }
}
