package com.example.cse441_music.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.Adapter.SongAdapter;
import com.example.cse441_music.Model.DeezerResponse;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.Network.DeezerApi;
import com.example.cse441_music.Network.RetrofitClient;
import com.example.cse441_music.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;

    public SearchFragment() {
        // Constructor trống yêu cầu
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.fragment_search_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fetchTracks();

        return view;
    }

    private void fetchTracks() {
        DeezerApi deezerApi = RetrofitClient.getClient().create(DeezerApi.class);
        Call<DeezerResponse> call = deezerApi.searchTracks("you");
        call.enqueue(new Callback<DeezerResponse>() {
            @Override
            public void onResponse(Call<DeezerResponse> call, Response<DeezerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Song> tracks = response.body().getData();
                    // Khởi tạo TrackAdapter với danh sách bài hát và context
                    songAdapter = new SongAdapter(tracks, getActivity());
                    recyclerView.setAdapter(songAdapter);
                }
            }

            @Override
            public void onFailure(Call<DeezerResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
