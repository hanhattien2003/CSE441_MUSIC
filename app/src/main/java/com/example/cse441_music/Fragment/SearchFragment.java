package com.example.cse441_music.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.Adapter.SongAdapter;
import com.example.cse441_music.R;
import com.example.cse441_music.Model.Song;

import com.example.cse441_music.Controller.SongController;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private List<Song> songList;
    private EditText editTextSearch;
    private SongController songController; // Khai báo SongController

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        editTextSearch = view.findViewById(R.id.editTextSearch);

        songList = new ArrayList<>();
        adapter = new SongAdapter(songList);
        recyclerView.setAdapter(adapter);

        songController = new SongController(); // Khởi tạo SongController

        // Ban đầu lấy tất cả bài hát
        fetchSongs("");

        // Thêm listener cho EditText để tìm kiếm
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetchSongs(s.toString()); // Lấy bài hát dựa trên truy vấn tìm kiếm
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không làm gì
            }
        });

        return view;
    }

    // Lấy dữ liệu bài hát từ API, tùy chọn theo truy vấn tìm kiếm
    private void fetchSongs(String query) {
        new Thread(() -> {
            try {
                List<Song> songs = songController.getSongs(query); // Gọi SongController để lấy danh sách bài hát

                requireActivity().runOnUiThread(() -> {
                    songList.clear(); // Xóa danh sách trước khi thêm kết quả mới
                    songList.addAll(songs); // Thêm bài hát mới vào danh sách
                    adapter.notifyDataSetChanged(); // Cập nhật adapter
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
