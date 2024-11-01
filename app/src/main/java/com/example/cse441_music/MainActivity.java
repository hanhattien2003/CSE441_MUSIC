package com.example.cse441_music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.cse441_music.Fragment.FavoriteFragment;
import com.example.cse441_music.Fragment.HomeFragment;
import com.example.cse441_music.Fragment.LibraryFragment;
import com.example.cse441_music.Fragment.SearchFragment;
import com.example.cse441_music.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private LinearLayout media_player_layout;

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
                String song_title = data.getStringExtra("song_title");
                String song_artist = data.getStringExtra("song_artist");
                String song_pic_url = data.getStringExtra("song_pic_url");

                TextView small_window_song_title = findViewById(R.id.small_window_song_title);
                TextView small_window_song_artist = findViewById(R.id.small_window_song_artist);
                ImageView small_window_pic = findViewById(R.id.small_window_pic);

                small_window_song_title.setText(song_title);
                small_window_song_artist.setText(song_artist);

                Glide.with(this)
                        .load(song_pic_url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(small_window_pic);




            } else {
            }
//        }
    }
}
