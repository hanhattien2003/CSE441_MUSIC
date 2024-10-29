package com.example.cse441_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.APIService.ApiService;
import com.example.cse441_music.APIService.Song.SongService;
import com.example.cse441_music.Adapter.SongAdapter;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private ApiService apiService;
    private SongService songService;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Thiết lập RecyclerView
        recyclerViewSongs = rootView.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách bài hát
        songList = new ArrayList<>();
        songAdapter = new SongAdapter(songList);
        recyclerViewSongs.setAdapter(songAdapter);

        // Khởi tạo ApiService và SongService
        apiService = new ApiService();
        songService = new SongService();

        // Lấy danh sách bài hát
        fetchTopTracks("your_query"); // Thay "your_query" bằng giá trị bạn muốn tìm kiếm

        return rootView;
    }

    private void fetchTopTracks(String query) {
        new Thread(() -> {
            try {
                // Lấy danh sách bài hát theo query
                String jsonResponse = apiService.fetchSongs(query, 0, 20); // Lấy 20 bài hát đầu tiên
                List<Song> songs = songService.parseJson(jsonResponse);

                // Cập nhật danh sách bài hát trong UI
                getActivity().runOnUiThread(() -> {
                    songList.clear();
                    songList.addAll(songs);
                    songAdapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error fetching top tracks", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
