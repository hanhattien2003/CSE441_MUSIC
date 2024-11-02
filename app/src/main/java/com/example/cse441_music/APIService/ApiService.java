package com.example.cse441_music.APIService;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "https://api.jamendo.com/v3.0";
    public static final String CLIENT_ID = "900dafad";

    public String buildSongUrl(String query, int page, int limit) {
        StringBuilder apiUrl = new StringBuilder(BASE_URL + "/tracks/?client_id=" + CLIENT_ID);
        apiUrl.append("&limit=").append(limit);
        apiUrl.append("&offset=").append(page * limit);

        if (!query.isEmpty()) {
            apiUrl.append("&search=").append(query);
        }

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
        return result.toString();
    }


    // Phương thức lấy danh sách album với phân trang
    public String fetchAlbums(int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/albums/?client_id=" + CLIENT_ID + "&format=jsonpretty&limit=" + limit + "&offset=" + (page * limit);
        Log.d("API URL", apiUrl);
        return fetchData(apiUrl);
    }

    // Phương thức lấy bài hát theo album
    public String fetchTracksByAlbum(String albumId) throws Exception {
        String apiUrl = BASE_URL + "/albums/tracks/?client_id=" + CLIENT_ID + "&format=jsonpretty&id=" + albumId;
        return fetchData(apiUrl);
    }

    // Fetch top songs of the year
    public String fetchTopYear(int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/tracks/?client_id=" + CLIENT_ID + "&limit=" + limit + "&offset=" + (page * limit) + "&datebetween=2000-01-01_2023-12-31";
        return fetchData(apiUrl);
    }

    // Fetch top songs of the month
    public String fetchTopMonth(int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/tracks/?client_id=" + CLIENT_ID + "&limit=" + limit + "&offset=" + (page * limit) + "&datebetween=2023-10-01_2023-10-31";
        return fetchData(apiUrl);
    }

    // Fetch top songs of the week
    public String fetchTopWeek(int page, int limit) throws Exception {
        String apiUrl = BASE_URL + "/tracks/?client_id=" + CLIENT_ID + "&limit=" + limit + "&offset=" + (page * limit) + "&datebetween=2023-10-20_2023-10-27";
        return fetchData(apiUrl);
    }

    // Bạn có thể thêm các phương thức API khác nếu cần
}
