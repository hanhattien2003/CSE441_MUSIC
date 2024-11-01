package com.example.cse441_music.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cse441_music.APIService.Song.SongService;
import com.example.cse441_music.Adapter.TrackAdapter;
import com.example.cse441_music.Model.Track;
import com.example.cse441_music.R;
import com.example.cse441_music.Adapter.AlbumAdapter;
import com.example.cse441_music.APIService.ApiService;
import com.example.cse441_music.Model.Album;

import java.util.ArrayList;
import java.util.List;
public class LibraryFragment extends Fragment {

    private RecyclerView albumRecyclerView;
    private RecyclerView trackRecyclerView;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;
    private List<Album> albumList = new ArrayList<>();
    private List<Track> trackList = new ArrayList<>();

    private TextView tracksTitle;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this Fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        albumRecyclerView = view.findViewById(R.id.playlistList);
        trackRecyclerView = view.findViewById(R.id.trackRecyclerView);
        tracksTitle = view.findViewById(R.id.tracksTitle);

        // Set up Album RecyclerView
        albumRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        albumAdapter = new AlbumAdapter(albumList, new AlbumAdapter.OnAlbumClickListener() {
            @Override
            public void onAlbumClick(String albumId) {
                FetchTracksTask fetchTracksTask = new FetchTracksTask();
                fetchTracksTask.execute(albumId);
            }
        });
        albumRecyclerView.setAdapter(albumAdapter);

        // Set up Track RecyclerView
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trackAdapter = new TrackAdapter(trackList);
        trackRecyclerView.setAdapter(trackAdapter);

        // Fetch albums
        initializeAlbumData();

        return view;
    }

    private void initializeAlbumData() {
        new FetchAlbumsTask().execute();
    }

    private class FetchAlbumsTask extends AsyncTask<Void, Void, List<Album>> {

        @Override
        protected List<Album> doInBackground(Void... voids) {
            try {
                ApiService apiService = new ApiService();
                String jsonResponse = apiService.fetchAlbums(0, 10); // Fetch the first 10 albums
                return jsonResponse != null ? SongService.parseAlbumsJson(jsonResponse) : new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FetchAlbumsTask", "Error fetching albums: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Album> albums) {
            if (albums != null && !albums.isEmpty()) {
                FetchTracksTask fetchTracksTask = new FetchTracksTask();
                fetchTracksTask.execute(albums.get(0).getId());
                albumList.clear();
                albumList.addAll(albums);
                albumAdapter.notifyDataSetChanged();
            } else {
                Log.e("FetchAlbumsTask", "No albums found or error fetching albums.");
            }
        }
    }

    private class FetchTracksTask extends AsyncTask<String, Void, List<Track>> {
        @Override
        protected List<Track> doInBackground(String... albumIds) {
            try {
                ApiService apiService = new ApiService();
                String jsonResponse = apiService.fetchTracksByAlbum(albumIds[0]);
                return jsonResponse != null ? SongService.parseTracksJson(jsonResponse) : new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            if (tracks != null && !tracks.isEmpty()) {
                // Update track list and make the RecyclerView visible
                trackList.clear();
                trackList.addAll(tracks);
                trackAdapter.notifyDataSetChanged();

                // Make track list and title visible
                tracksTitle.setVisibility(View.VISIBLE);
                trackRecyclerView.setVisibility(View.VISIBLE);
            } else {
                Log.d("FetchTracksTask", "No tracks found for this album.");
            }
        }
    }
}
