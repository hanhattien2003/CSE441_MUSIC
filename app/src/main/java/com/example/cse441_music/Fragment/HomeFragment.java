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
    private RecyclerView recyclerViewTopYear, recyclerViewTopMonth, recyclerViewTopWeek;
    private SongAdapter songAdapterYear, songAdapterMonth, songAdapterWeek;
    private List<Song> songListYear, songListMonth, songListWeek;
    private ApiService apiService;
    private SongService songService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Thiết lập RecyclerViews
        recyclerViewTopYear = rootView.findViewById(R.id.recyclerViewTopYear);
        recyclerViewTopMonth = rootView.findViewById(R.id.recyclerViewTopMonth);
        recyclerViewTopWeek = rootView.findViewById(R.id.recyclerViewTopWeek);

        recyclerViewTopYear.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTopMonth.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTopWeek.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách bài hát và adapters
        songListYear = new ArrayList<>();
        songListMonth = new ArrayList<>();
        songListWeek = new ArrayList<>();

        songAdapterYear = new SongAdapter(getContext(), songListYear);
        songAdapterMonth = new SongAdapter(getContext(), songListMonth);
        songAdapterWeek = new SongAdapter(getContext(), songListWeek);

        recyclerViewTopYear.setAdapter(songAdapterYear);
        recyclerViewTopMonth.setAdapter(songAdapterMonth);
        recyclerViewTopWeek.setAdapter(songAdapterWeek);

        // Khởi tạo ApiService và SongService
        apiService = new ApiService();
        songService = new SongService();

        // Lấy danh sách bài hát
        fetchTopTracksYear();
        fetchTopTracksMonth();
        fetchTopTracksWeek();

        return rootView;
    }

    private void fetchTopTracksYear() {
        new Thread(() -> {
            try {
                String jsonResponse = apiService.fetchTopYear(0, 20); // Lấy 20 bài hát đầu tiên trong năm
                List<Song> songs = songService.parseJson(jsonResponse);
                getActivity().runOnUiThread(() -> {
                    songListYear.clear();
                    songListYear.addAll(songs);
                    songAdapterYear.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error fetching top tracks of the year", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void fetchTopTracksMonth() {
        new Thread(() -> {
            try {
                String jsonResponse = apiService.fetchTopMonth(0, 20); // Lấy 20 bài hát đầu tiên trong tháng
                List<Song> songs = songService.parseJson(jsonResponse);
                getActivity().runOnUiThread(() -> {
                    songListMonth.clear();
                    songListMonth.addAll(songs);
                    songAdapterMonth.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error fetching top tracks of the month", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void fetchTopTracksWeek() {
        new Thread(() -> {
            try {
                String jsonResponse = apiService.fetchTopWeek(0, 20); // Lấy 20 bài hát đầu tiên trong tuần
                List<Song> songs = songService.parseJson(jsonResponse);
                getActivity().runOnUiThread(() -> {
                    songListWeek.clear();
                    songListWeek.addAll(songs);
                    songAdapterWeek.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error fetching top tracks of the week", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
