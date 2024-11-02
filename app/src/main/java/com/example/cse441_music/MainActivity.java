package com.example.cse441_music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.cse441_music.BackgroundService.MusicService;
import com.example.cse441_music.Fragment.FavoriteFragment;
import com.example.cse441_music.Fragment.HomeFragment;
import com.example.cse441_music.Fragment.LibraryFragment;
import com.example.cse441_music.Fragment.SearchFragment;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private LinearLayout media_player_layout;
    private ImageButton btn_previous, btn_play_pause, btn_next;

    private ArrayList<Song> list_song;
    private int position;
    private Song curent_song;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Sử dụng binding.getRoot() để liên kết với layout
        setContentView(binding.getRoot());

        media_player_layout = findViewById(R.id.media_player_layout);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_fragment) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.search_fragment) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.library_fragment) {
                replaceFragment(new LibraryFragment());
            } else if (itemId == R.id.favorite_fragment) {
                replaceFragment(new FavoriteFragment());
            } else {
                return false; // Trường hợp ID không khớp
            }
            return true;
        });


        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment()); // Mở HomeFragment mặc định
        }



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        media_player_layout.setVisibility(View.VISIBLE);

            if (data != null) {
                list_song = data.getParcelableArrayListExtra("songList");
                position = Integer.parseInt(data.getStringExtra("songPositon"));
                curent_song = list_song.get(position);

                TextView small_window_song_title = findViewById(R.id.small_window_song_title);
                TextView small_window_song_artist = findViewById(R.id.small_window_song_artist);
                ImageView small_window_pic = findViewById(R.id.small_window_pic);

                small_window_song_title.setText(curent_song.getName());
                small_window_song_artist.setText(curent_song.getArtistName());

                Glide.with(this)
                        .load(curent_song.getImageUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(small_window_pic);

                btn_previous = findViewById(R.id.btn_previous);
                btn_next = findViewById(R.id.btn_next);
                btn_play_pause = findViewById(R.id.btn_play_pause);

                btn_previous.setOnClickListener(v -> playPreviousSong());
                btn_next.setOnClickListener(v -> playNextSong());
                btn_play_pause.setOnClickListener(v -> togglePlayPause());


            }
    }
    private void playNextSong() {
        if (position < list_song.size() - 1) {
            position++;
            curent_song = list_song.get(position);
            updateUIForCurrentSong();
            startMusicService();
            isPlaying = true;
        } else {
            Toast.makeText(this, "This is the last Song in the list", Toast.LENGTH_SHORT).show();
        }
    }

    private void playPreviousSong() {
        if (position > 0) {
            position--;
            curent_song = list_song.get(position);
            updateUIForCurrentSong();
            startMusicService();
            isPlaying = true;
        } else {
            Toast.makeText(this, "This is the first Song in the list", Toast.LENGTH_SHORT).show();
        }
    }

    private void togglePlayPause() {
        if (isPlaying) {
            stopMusicService();
            btn_play_pause.setImageResource(android.R.drawable.ic_media_play);
            isPlaying = false;
        } else {
            startMusicService();
            btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
            isPlaying = true;
        }
    }

    private void updateUIForCurrentSong() {
        TextView small_window_song_title = findViewById(R.id.small_window_song_title);
        TextView small_window_song_artist = findViewById(R.id.small_window_song_artist);
        ImageView small_window_pic = findViewById(R.id.small_window_pic);

        small_window_song_title.setText(curent_song.getName());
        small_window_song_artist.setText(curent_song.getArtistName());

        Glide.with(this)
                .load(curent_song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(small_window_pic);
    }

    private void startMusicService() {
        stopMusicService();
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("audioUrl", curent_song.getAudioUrl());
        startService(serviceIntent);
    }

    private void stopMusicService() {
        stopService(new Intent(this, MusicService.class));
    }
}
