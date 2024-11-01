package com.example.cse441_music.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cse441_music.Adapter.FavoriteAdapter;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private static final int REQUEST_PERMISSION_READ_STORAGE = 1;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kiểm tra quyền truy cập
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_STORAGE);
        } else {
            loadSongs();
        }

        return view;
    }

    // Xử lý kết quả khi yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadSongs();
            } else {
                // Hiển thị thông báo hoặc xử lý nếu người dùng không cấp quyền
            }
        }
    }

    // Phương thức lấy danh sách bài hát từ thiết bị và cài đặt Adapter
    private void loadSongs() {
        List<Song> songList = getSongsFromDevice();
        adapter = new FavoriteAdapter(songList, getContext());
        recyclerView.setAdapter(adapter);
    }

    // Phương thức lấy danh sách bài hát từ thiết bị
    private List<Song> getSongsFromDevice() {
        List<Song> songs = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int urlColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);
                String url = cursor.getString(urlColumn);

                songs.add(new Song(id, name, artist, url, "", album));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return songs;
    }
}
