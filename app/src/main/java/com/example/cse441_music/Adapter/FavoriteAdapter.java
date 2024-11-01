package com.example.cse441_music.Adapter;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.SongViewHolder> {

    private List<Song> songList;
    private Context context;

    public FavoriteAdapter(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.songName.setText(song.getName());
        holder.artistName.setText(song.getArtistName());
        holder.albumName.setText(song.getAlbumName());

        Glide.with(holder.itemView.getContext())
                .load(song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.songImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MusicPlayerActivity.class);
            intent.putExtra("songPositon", position+"");
            intent.putParcelableArrayListExtra("songList", new ArrayList<>(songList));

            ((Activity) holder.itemView.getContext()).startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName, artistName, albumName;
        ImageView songImage;
//        ImageButton addToFavorite;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            albumName = itemView.findViewById(R.id.albumNameTextView);
            songImage = itemView.findViewById(R.id.songImage);
//            addToFavorite = itemView.findViewById(R.id.addToFavorite);
        }
    }
}
