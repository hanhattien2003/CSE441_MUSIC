package com.example.cse441_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.R;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songList;
    private Context context;
    private static final String FILENAME = "favorite_songs.txt";

    // Constructor without context for HomeFragment
    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    // Constructor with context for saving favorites
    public SongAdapter(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

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

        if (context != null) {
            holder.addToFavorite.setOnClickListener(v -> {
                addToFavorites(song.getId());
                Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    private void addToFavorites(String songId) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(songId + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName, artistName, albumNameTextView;
        ImageView songImage;
        Button addToFavorite;

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
