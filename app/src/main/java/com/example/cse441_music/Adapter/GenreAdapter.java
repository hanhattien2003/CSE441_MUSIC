package com.example.cse441_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_music.Model.Genre;
import com.example.cse441_music.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private Context context;
    private List<Genre> genres;

    public GenreAdapter(Context context, List<Genre> genres) {
        this.context = context;
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_search_genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.textGenre.setText(genre.getName());

        // Load ảnh thể loại nhạc từ URL bằng Glide
        Glide.with(context)
                .load(genre.getPicture())
                .into(holder.imageGenre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView imageGenre;
        TextView textGenre;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imageGenre = itemView.findViewById(R.id.imageGenre);
            textGenre = itemView.findViewById(R.id.textGenre);
        }
    }
}