package com.example.cse441_music.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.Adapter.FavoriteAdapter;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private RecyclerView recyclerViewFavorite;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<Song> songList;
    private TextView emptyTextView; // TextView để hiển thị thông báo khi danh sách trống

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerViewFavorite = view.findViewById(R.id.recyclerViewFavorite);
        emptyTextView = view.findViewById(R.id.emptyTextView); // Lấy tham chiếu đến TextView
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        songList = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(songList, getContext());
        recyclerViewFavorite.setAdapter(favoriteAdapter);

        // Kiểm tra và yêu cầu quyền truy cập bộ nhớ
        checkAndRequestPermissions();

        return view;
    }

    // Kiểm tra và yêu cầu quyền cần thiết
    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 trở lên
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_PERMISSION);
            } else {
                loadSongs();
            }
        } else {
            // Android 10 trở xuống
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            } else {
                loadSongs();
            }
        }
    }

    // Xử lý kết quả yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadSongs();
            } else {
                Toast.makeText(getContext(), "Quyền truy cập bộ nhớ bị từ chối.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Xử lý kết quả yêu cầu quyền đặc biệt trên Android 11 trở lên
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                loadSongs();
            } else {
                Toast.makeText(getContext(), "Quyền truy cập bộ nhớ bị từ chối.", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    Hàm này ok
//    private void loadSongs() {
//        ContentResolver contentResolver = requireContext().getContentResolver();
//        Uri[] uris = {MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.INTERNAL_CONTENT_URI};
//
//        String[] projection = {
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.ALBUM,
//                MediaStore.Audio.Media.DATA
//        };
//
//        for (Uri songUri : uris) {
//            Cursor cursor = contentResolver.query(songUri, projection, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//                    String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//
//                    Song song = new Song(id, title, artist, data, null, album);
//                    songList.add(song);
//                } while (cursor.moveToNext());
//                cursor.close();
//            }
//        }
//
//        // Kiểm tra nếu danh sách rỗng, hiển thị thông báo
//        if (songList.isEmpty()) {
//            emptyTextView.setVisibility(View.VISIBLE);
//        } else {
//            emptyTextView.setVisibility(View.GONE);
//        }
//
//        favoriteAdapter.notifyDataSetChanged();
//    }

//    Ham nay ok s2
//    private void loadSongs() {
//        ContentResolver contentResolver = requireContext().getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; // Chỉ sử dụng EXTERNAL_CONTENT_URI
//
//        String[] projection = {
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.ALBUM,
//                MediaStore.Audio.Media.DATA
//        };
//
//        // Lọc để loại trừ các thư mục không mong muốn
//        String selection = MediaStore.Audio.Media.DATA + " NOT LIKE ? AND " +
//                MediaStore.Audio.Media.DATA + " NOT LIKE ? AND " +
//                MediaStore.Audio.Media.DATA + " NOT LIKE ? ";
//
//        String[] selectionArgs = new String[]{
//                "%/Ringtones/%",   // Loại trừ thư mục nhạc chuông
//                "%/Alarms/%",      // Loại trừ thư mục âm thanh báo thức
//                "%/Notifications/%" // Loại trừ thư mục âm thanh thông báo
//        };
//
//        Cursor cursor = contentResolver.query(songUri, projection, selection, selectionArgs, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//
//                Song song = new Song(id, title, artist, data, null, album);
//                songList.add(song);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        // Kiểm tra nếu danh sách rỗng, hiển thị thông báo
//        if (songList.isEmpty()) {
//            emptyTextView.setVisibility(View.VISIBLE);
//        } else {
//            emptyTextView.setVisibility(View.GONE);
//        }
//
//        favoriteAdapter.notifyDataSetChanged();
//    }

    private void loadSongs() {
        ContentResolver contentResolver = requireContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID // Lấy ALBUM_ID
        };

        String selection = MediaStore.Audio.Media.DATA + " NOT LIKE ? AND " +
                MediaStore.Audio.Media.DATA + " NOT LIKE ? AND " +
                MediaStore.Audio.Media.DATA + " NOT LIKE ? ";

        String[] selectionArgs = new String[]{
                "%/Ringtones/%",
                "%/Alarms/%",
                "%/Notifications/%"
        };

        Cursor cursor = contentResolver.query(songUri, projection, selection, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String albumId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                // Lấy đường dẫn ảnh bìa album
                String albumArtUri = getAlbumArtUri(albumId);

                // Tạo đối tượng Song với albumArtUri
                Song song = new Song(id, title, artist, data, albumArtUri, album);
                songList.add(song);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Kiểm tra nếu danh sách rỗng, hiển thị thông báo
        if (songList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }

        favoriteAdapter.notifyDataSetChanged();
    }

    // Hàm lấy URI bìa album
    private String getAlbumArtUri(String albumId) {
        return Uri.parse("content://media/external/audio/albumart/" + albumId).toString();
    }


}
