package com.example.cse441_music.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_music.Model.Album;
import com.example.cse441_music.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> albumList;
    private OnAlbumClickListener onAlbumClickListener;
    public AlbumAdapter(List<Album> albumList, OnAlbumClickListener onAlbumClickListener) {
        this.albumList = albumList;
        this.onAlbumClickListener = onAlbumClickListener;

    }
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumName.setText(album.getName());
        Glide.with(holder.itemView).load(album.getImageUrl()).into(holder.albumImage);
        // Set up the nested RecyclerView for tracks


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAlbumClickListener != null) {
                    onAlbumClickListener.onAlbumClick(album.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView albumName;
        ImageView albumImage;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.albumName);
            albumImage = itemView.findViewById(R.id.albumImage);
        }
    }
    public interface OnAlbumClickListener {
        void onAlbumClick(String albumId);
    }

}
