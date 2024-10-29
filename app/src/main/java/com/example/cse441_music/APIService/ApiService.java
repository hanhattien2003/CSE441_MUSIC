package com.example.cse441_music.APIService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "https://api.jamendo.com/v3.0/tracks/?client_id=900dafad";

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
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(apiUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
