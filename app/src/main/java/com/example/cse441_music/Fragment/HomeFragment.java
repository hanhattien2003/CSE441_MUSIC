package com.example.cse441_music.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cse441_music.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Thiết lập layoutManager cho RecyclerViewCollections (GridLayoutManager với 3 cột)
        RecyclerView recyclerViewCollections = rootView.findViewById(R.id.recyclerViewCollections);
        recyclerViewCollections.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Thiết lập layoutManager cho RecyclerViewSongs (LinearLayoutManager theo chiều dọc)
        RecyclerView recyclerViewSongs = rootView.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        // Bạn cần thêm code adapter cho RecyclerView tại đây
        // recyclerViewCollections.setAdapter(yourAdapter);
        // recyclerViewSongs.setAdapter(yourAdapter);

        return rootView;
    }
}
