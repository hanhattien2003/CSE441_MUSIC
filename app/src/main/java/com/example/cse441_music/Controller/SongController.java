package com.example.cse441_music.Controller;

import com.example.cse441_music.APIService.ApiService;
import com.example.cse441_music.APIService.Song.SongService;
import com.example.cse441_music.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongController {
    private ApiService apiService;
    private SongService songService;

    public SongController() {
        this.apiService = new ApiService();
        this.songService = new SongService();
    }

    // Thêm selectedTag vào phương thức getSongs để thực hiện lọc bài hát
    public List<Song> getSongs(String query, String selectedTag, int page, int songsPerPage) throws Exception {
        // Gọi API để lấy danh sách bài hát theo query và phân trang
        String jsonResponse = apiService.fetchSongs(query, page, songsPerPage);
        List<Song> allSongs = songService.parseJson(jsonResponse); // Lấy toàn bộ danh sách bài hát từ API

        // Thực hiện bước lọc thứ 2 dựa trên giá trị selectedTag
        List<Song> filteredSongs = new ArrayList<>();
        for (Song song : allSongs) {
            switch (selectedTag) {
                case "albums":
                    if (song.getAlbumName() != null && song.getAlbumName().toLowerCase().contains(query.toLowerCase())) {
                        filteredSongs.add(song); // Thêm bài hát vào danh sách nếu album chứa query
                    }
                    break;
                case "artists":
                    if (song.getArtistName().toLowerCase().contains(query.toLowerCase())) {
                        filteredSongs.add(song); // Thêm bài hát nếu nghệ sĩ chứa query
                    }
                    break;
                case "songs":
                    if (song.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredSongs.add(song); // Thêm bài hát nếu tên bài hát chứa query
                    }
                    break;
                default:
                    return allSongs;

            }
        }

        return filteredSongs; // Trả về danh sách đã lọc
    }


}
