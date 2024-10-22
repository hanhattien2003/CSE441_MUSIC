package com.example.cse441_music.APIService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "https://api.jamendo.com/v3.0/tracks/?client_id=900dafad";

    // Phương thức fetchSongs với phân trang
    public String fetchSongs(String query, int page, int limit) throws Exception {
        // Xây dựng URL API với tham số tìm kiếm, số trang và số lượng bài hát mỗi trang
        String apiUrl = BASE_URL + "&limit=" + limit + "&offset=" + (page * limit);
        if (!query.isEmpty()) {
            apiUrl += "&search=" + query;
        }

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
        return result.toString(); // Trả về chuỗi JSON từ API
    }
}
