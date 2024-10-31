package com.example.cse441_music.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.MusicPlayerActivity;
import com.example.cse441_music.R;
//import com.example.cse441_music.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songList;
//    private DatabaseHelper databaseHelper;


    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    // Constructor cho FavoriteFragment, bao gồm DatabaseHelper
//    public SongAdapter(List<Song> songList, DatabaseHelper databaseHelper) {
//        this.songList = songList;
//        this.databaseHelper = databaseHelper;
//    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.songName.setText(song.getName());
        holder.artistName.setText(song.getArtistName());
        holder.albumNameTextView.setText(song.getAlbumName());

        Glide.with(holder.itemView.getContext())
                .load(song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.songImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MusicPlayerActivity.class);
//            intent.putExtra("songTitle", Song.getName());
//            intent.putExtra("imageUrl", Song.getImageUrl());
//            intent.putExtra("audioUrl", Song.getAudioUrl());
            intent.putExtra("songPositon", position+"");
            intent.putParcelableArrayListExtra("songList", new ArrayList<>(songList));



            holder.itemView.getContext().startActivity(intent);
        });

        // Xử lý sự kiện nút yêu thích nếu có DatabaseHelper
//        if (databaseHelper != null) {
//            holder.addToFavorite.setOnClickListener(v -> {
//                List<String> favoriteIds = databaseHelper.getFavorites();
//                if (!favoriteIds.contains(Song.getId())) {
//                    databaseHelper.addFavorite(Song.getId());
//                    Toast.makeText(v.getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(v.getContext(), "Bài hát đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName, artistName, albumNameTextView;
        ImageView songImage;
        ImageButton addToFavorite;

        public SongViewHolder(View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            albumNameTextView = itemView.findViewById(R.id.albumNameTextView);
            songImage = itemView.findViewById(R.id.songImage);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
        }
    }
}
