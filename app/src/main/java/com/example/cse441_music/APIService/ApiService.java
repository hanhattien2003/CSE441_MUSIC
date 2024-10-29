package com.example.cse441_music.APIService;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "https://api.jamendo.com/v3.0";
    private static final String CLIENT_ID = "900dafad";
    public String buildSongUrl(String query, int page, int limit) {
        StringBuilder apiUrl = new StringBuilder(BASE_URL);
        apiUrl.append("&limit=").append(limit);
        apiUrl.append("&offset=").append(page * limit);

        if (!query.isEmpty()) {
            apiUrl.append("&search=").append(query);
        }

        return apiUrl.toString();
    }

    public String buildTopTracksUrl(String startDate, String endDate, int page, int limit) {
        StringBuilder apiUrl = new StringBuilder(BASE_URL);
        apiUrl.append("&format=json&order=popularity_total&limit=").append(limit);
        apiUrl.append("&offset=").append(page * limit);
        apiUrl.append("&datebetween=").append(startDate).append("_").append(endDate);

        return apiUrl.toString();
    }

    public String fetchData(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        return result.toString(); // Return the JSON response from the API
    }


    // Method to fetch songs with pagination
    public String fetchSongs(String query, int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/tracks/?client_id=" + CLIENT_ID + "&limit=" + limit + "&offset=" + (page * limit);
        if (!query.isEmpty()) {
            apiUrl += "&search=" + query;
        }
        return fetchData(apiUrl);
    }
    // Method to fetch album tracks by artist name
    public String fetchAlbums(int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/albums/?client_id=" + CLIENT_ID + "&format=jsonpretty&limit=" + limit + "&offset=" + (page * limit);
        Log.d("API URL", apiUrl);
        return fetchData(apiUrl);
    }

    public String fetchTracksByAlbum(String albumId) throws Exception {
        String apiUrl = BASE_URL + "/albums/tracks/?client_id=" + CLIENT_ID + "&format=jsonpretty&id=" + albumId;
        return fetchData(apiUrl);
    }

}
