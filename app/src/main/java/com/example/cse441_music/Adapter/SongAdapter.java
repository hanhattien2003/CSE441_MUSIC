package com.example.cse441_music.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_music.R;
import com.example.cse441_music.Model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.TrackViewHolder> {

    private List<Song> songList;
    private Context context;
    private MediaPlayer mediaPlayer;

    public SongAdapter(List<Song> trackList, Context context) {
        this.songList = trackList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.song.setText(song.getTitle());
        holder.artist.setText(song.getArtist().getName());

        // Xử lý sự kiện click vào bài nhạc
        holder.itemView.setOnClickListener(v -> {
            // Nếu mediaPlayer đã tồn tại và đang phát, dừng lại trước khi phát bài mới
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }

            // Tạo mediaPlayer mới và phát nhạc từ URL của bài nhạc
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(song.getFilePath()); // URL nhạc từ Deezer API
                mediaPlayer.prepare(); // Chuẩn bị phát nhạc
                mediaPlayer.start(); // Phát nhạc
                Toast.makeText(context, "Playing: " + song.getTitle(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error playing track", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView song;
        TextView artist;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            song = itemView.findViewById(R.id.fragment_search_song);
            artist = itemView.findViewById(R.id.fragment_search_artist);
        }
    }
}
