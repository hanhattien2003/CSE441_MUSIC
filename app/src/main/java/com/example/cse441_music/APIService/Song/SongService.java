package com.example.cse441_music.APIService.Song;

import com.example.cse441_music.Model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongService {
    public List<Song> parseJson(String json) throws JSONException {
        List<Song> songList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject track = results.getJSONObject(i);
            String id = track.getString("id");
            String name = track.getString("name");
            String artistName = track.getString("artist_name");
            String audioUrl = track.getString("audio");
            String imageUrl = track.getString("image"); // Lấy URL của ảnh bài hát
            String albumName = track.optString("album_name"); // Thêm tên album

            songList.add(new Song(id, name, artistName, audioUrl, imageUrl, albumName)); // Thêm bài hát vào danh sách
        }

        return songList; // Trả về danh sách bài hát
    }
}
