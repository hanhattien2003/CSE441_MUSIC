package com.example.cse441_music.Controller;


import android.util.Log;

import com.example.cse441_music.Model.DeezerGenreResponse;
import com.example.cse441_music.Model.Genre;
import com.example.cse441_music.Network.DeezerApi;
import com.example.cse441_music.Network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreController {

    private DeezerApi deezerApi;

    public GenreController() {
        // Khởi tạo DeezerApi
        deezerApi = RetrofitClient.getClient().create(DeezerApi.class);
    }

    // Phương thức lấy danh sách thể loại từ Deezer API
    public void fetchGenres(final GenreControllerCallback<List<Genre>> callback) {
        deezerApi.getGenres().enqueue(new Callback<DeezerGenreResponse>() {
            @Override
            public void onResponse(Call<DeezerGenreResponse> call, Response<DeezerGenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Genre> genreList = response.body().getData();
                    callback.onSuccess(genreList);
                } else {
                    Log.e("GenreController", "Lỗi khi lấy dữ liệu từ Deezer API");
                    callback.onFailure("Lỗi khi lấy dữ liệu từ API");
                }
            }

            @Override
            public void onFailure(Call<DeezerGenreResponse> call, Throwable t) {
                Log.e("GenreController", "Lỗi kết nối: " + t.getMessage());
                callback.onFailure("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Interface callback để trả về dữ liệu hoặc lỗi
    public interface GenreControllerCallback<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }
}
