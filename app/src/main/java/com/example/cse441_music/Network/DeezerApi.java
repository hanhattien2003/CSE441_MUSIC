package com.example.cse441_music.Network;

import com.example.cse441_music.Model.DeezerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeezerApi {
    @GET("search")
    Call<DeezerResponse> searchTracks(@Query("q") String query);

    @GET("genre")
    Call<DeezerResponse> getGenres();
}
