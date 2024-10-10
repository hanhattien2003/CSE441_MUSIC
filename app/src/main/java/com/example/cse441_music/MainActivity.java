package com.example.cse441_music;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cse441_music.Fragment.FavoriteFragment;
import com.example.cse441_music.Fragment.HomeFragment;
import com.example.cse441_music.Fragment.LibraryFragment;
import com.example.cse441_music.Fragment.SearchFragment;
import com.example.cse441_music.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Sử dụng binding.getRoot() để liên kết với layout
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Đảm bảo tiêu đề luôn hiển thị
        binding.bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        // Xử lý sự kiện chọn item
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

        // Mở mặc định home_fragment khi khởi động
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

    // Tiep tuc them xu ly tai day
}
