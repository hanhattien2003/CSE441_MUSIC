package com.example.cse441_music.Controller;

import static com.example.cse441_music.Network.RetrofitClient.BASE_URL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.cse441_music.Model.DeezerResponse;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.Network.DeezerApi;
import com.example.cse441_music.Network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SongController {
    private Song song;
    private Context context; // Để sử dụng Toast

    // Constructor
    public SongController(Song song, Context context) {
        this.song = song;
        this.context = context;
    }

    public void deleteSong() {
        // Xóa bài hát khỏi điện thoại
    }

    public void fetchTracks(String query, SongFetchCallback callback) {
        DeezerApi deezerApi = RetrofitClient.getClient().create(DeezerApi.class);
        Call<DeezerResponse> call = deezerApi.searchTracks(query);
        call.enqueue(new Callback<DeezerResponse>() {
            @Override
            public void onResponse(Call<DeezerResponse> call, Response<DeezerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Song> tracks = response.body().getData();
                    callback.onSuccess(tracks); // Gọi callback thành công
                } else {
                    Toast.makeText(context, "Không tìm thấy bài hát", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeezerResponse> call, Throwable t) {
                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface SongFetchCallback {
        void onSuccess(List<Song> songs);
    }


}
