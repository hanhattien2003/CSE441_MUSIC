package com.example.cse441_music.Adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.cse441_music.Model.Song;
import com.example.cse441_music.MusicPlayerActivity;
import com.example.cse441_music.R;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songList;
    private Song song;
    private Map<String, Long> downloadMap = new HashMap<>();
    private Context context;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        song = songList.get(position);

        holder.songName.setText(song.getName());
        holder.artistName.setText(song.getArtistName());
        holder.albumNameTextView.setText(song.getAlbumName());

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

        if (song.getAudiodownload_allowed() == 1) {
            holder.img_dowload.setVisibility(View.VISIBLE);
            holder.img_dowload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context != null) {
                        new DownloadFileTask().execute(song.getAudiodownload());
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    private class DownloadFileTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            String filePath = context.getExternalFilesDir(null) + "/"+song.getName()+".mp3"; // Đường dẫn lưu tệp

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                }

                // Tải xuống tệp
                InputStream input = new BufferedInputStream(connection.getInputStream());
                FileOutputStream output = new FileOutputStream(filePath);

                byte[] data = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                return "Download completed: " + filePath;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Hiển thị thông báo sau khi tải xuống
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }


    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName, artistName, albumNameTextView;
        ImageView songImage;
        ImageButton img_dowload;

        public SongViewHolder(View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            albumNameTextView = itemView.findViewById(R.id.albumNameTextView);
            songImage = itemView.findViewById(R.id.songImage);
            img_dowload = itemView.findViewById(R.id.img_dowload);

        }
    }
}
