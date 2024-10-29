package com.example.cse441_music.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewSongs = rootView.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchSongs();

        return rootView;
    }

    private void fetchSongs() {
        DeezerApi apiService = RetrofitClient.getClient().create(DeezerApi.class);
        Call<DeezerResponse> call = apiService.searchTracks("pop");

        call.enqueue(new Callback<DeezerResponse>() {
            @Override
            public void onResponse(Call<DeezerResponse> call, Response<DeezerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Song> songList = response.body().getData(); // Đảm bảo phương thức getData() trả về List<Song>
                    if (songList != null && !songList.isEmpty()) {
                        songAdapter = new SongAdapter(getContext(), songList);
                        recyclerViewSongs.setAdapter(songAdapter);
                    } else {
                        Toast.makeText(getContext(), "No songs found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch songs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeezerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
