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

    public List<Song> getSongs(String query) throws Exception {
        String jsonResponse = apiService.fetchSongs(query); // Gọi ApiService để lấy dữ liệu
        return songService.parseJson(jsonResponse); // Phân tích JSON và trả về danh sách bài hát
    }
}

