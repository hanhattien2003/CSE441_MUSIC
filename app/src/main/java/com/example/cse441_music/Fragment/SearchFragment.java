package com.example.cse441_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.Adapter.SongAdapter;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.Controller.SongController;
import com.example.cse441_music.R;

import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private EditText searchEditText;
    private SongController songController;

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

        searchEditText = view.findViewById(R.id.search_edit_text);
        songController = new SongController(new Song(), getActivity()); // Khởi tạo SongController

        // Tải khi vào giao diện
        songController.fetchTracks("a", tracks -> {
            songAdapter = new SongAdapter(tracks, getActivity());
            recyclerView.setAdapter(songAdapter);
        });

        // Thêm sự kiện lắng nghe cho EditText
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    songController.fetchTracks(query, tracks -> {
                        songAdapter = new SongAdapter(tracks, getActivity());
                        recyclerView.setAdapter(songAdapter);
                    });
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
                return true; // Đánh dấu rằng sự kiện đã được xử lý
            }
            return false; // Không xử lý các sự kiện khác
        });

        return view;
    }
}
