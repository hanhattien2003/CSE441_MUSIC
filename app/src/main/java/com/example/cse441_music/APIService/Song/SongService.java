package com.example.cse441_music.APIService.Song;

import com.example.cse441_music.APIService.ApiService;
import com.example.cse441_music.Model.Album;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.Model.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongService {
    private ApiService apiService;

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
            String imageUrl = track.getString("image");
            String albumName = track.optString("album_name");
            String audiodownload = track.optString("audiodownload", "");
            int audiodownload_allowed = track.optBoolean("audiodownload_allowed", false) ? 1 : 0;

            songList.add(new Song(id, name, artistName, audioUrl, imageUrl, albumName, audiodownload, audiodownload_allowed));
        }

        return songList;
    }


    public static List<Album> parseAlbumsJson(String json) throws Exception {
        JSONObject root = new JSONObject(json);
        JSONArray resultsArray = root.getJSONArray("results");

        List<Album> albums = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject albumObject = resultsArray.getJSONObject(i);

            String id = albumObject.getString("id");
            String name = albumObject.getString("name");
            String releaseDate = albumObject.getString("releasedate");
            String artistId = albumObject.getString("artist_id");
            String artistName = albumObject.getString("artist_name");
            String imageUrl = albumObject.getString("image");
            String zipUrl = albumObject.getString("zip");
            String shortUrl = albumObject.getString("shorturl");
            String shareUrl = albumObject.getString("shareurl");
            boolean zipAllowed = albumObject.getBoolean("zip_allowed");

            Album album = new Album(id, name, releaseDate, artistId, artistName, imageUrl, zipUrl, shortUrl, shareUrl, zipAllowed);
            albums.add(album);
        }

        return albums;
    }

    public static List<Track> parseTracksJson(String json) throws Exception {
        JSONObject root = new JSONObject(json);
        JSONArray resultsArray = root.getJSONArray("results");

        // Assuming each album in the response contains a "tracks" array
        List<Track> tracks = new ArrayList<>();
        if (resultsArray.length() > 0) {
            JSONObject albumObject = resultsArray.getJSONObject(0); // First album object
            JSONArray tracksArray = albumObject.getJSONArray("tracks");

            for (int i = 0; i < tracksArray.length(); i++) {
                JSONObject trackObject = tracksArray.getJSONObject(i);

                String trackId = trackObject.getString("id");
                int position = trackObject.getInt("position");
                String trackName = trackObject.getString("name");
                int duration = trackObject.getInt("duration");
                String licenseUrl = trackObject.getString("license_ccurl");
                String audioUrl = trackObject.getString("audio");
                String audioDownloadUrl = trackObject.getString("audiodownload");
                boolean audioDownloadAllowed = trackObject.getBoolean("audiodownload_allowed");

                Track track = new Track(trackId, position, trackName, duration, licenseUrl, audioUrl, audioDownloadUrl, audioDownloadAllowed);
                tracks.add(track);
            }
        }

        return tracks;
    }

}
