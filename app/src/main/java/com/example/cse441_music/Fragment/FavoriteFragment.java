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
import com.example.cse441_music.Database.DatabaseHelper;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;
    private DatabaseHelper databaseHelper;
    private ApiService apiService;
    private SongService songService;
    private List<Song> favoriteSongs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerViewSongs = view.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        apiService = new ApiService();
        songService = new SongService();
        favoriteSongs = new ArrayList<>();
        songAdapter = new SongAdapter(favoriteSongs);  // Initialize with an empty list
        recyclerViewSongs.setAdapter(songAdapter);

        loadFavorites(); // Load favorites when the view is created
        return view;
    }

    private void loadFavorites() {
        List<String> favoriteIds = databaseHelper.getFavorites(); // Lấy danh sách ID bài hát yêu thích từ cơ sở dữ liệu

        for (String id : favoriteIds) {
            new Thread(() -> {
                try {
                    // Fetch song details from Jamendo API using each song ID
                    Song song = songService.getSongById(id); // Gọi phương thức để lấy bài hát

                    // Update UI on the main thread
                    if (song != null) {
                        getActivity().runOnUiThread(() -> {
                            favoriteSongs.add(song);
                            songAdapter.notifyItemInserted(favoriteSongs.size() - 1); // Cập nhật adapter
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error loading favorite songs", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        }
    }
}
