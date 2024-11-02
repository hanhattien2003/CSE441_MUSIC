package com.example.cse441_music.Controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.cse441_music.BackgroundService.MusicService;
import com.example.cse441_music.Model.Song;

import java.util.ArrayList;

public class MusicPlayerController {
    private Context context;
    private ArrayList<Song> list_song;
    private int position;
    private Song current_song;
    private boolean isPlaying = false;
    private boolean playOne = false;

    public MusicPlayerController(Context context, ArrayList<Song> list_song, int position) {
        this.context = context;
        this.list_song = list_song;
        this.position = position;
        this.current_song = list_song.get(position);
    }

    public Song getCurrentSong() {
        return current_song;
    }

    public boolean isPlayOne() {
        return playOne;
    }

    public void togglePlayOne() {
        playOne = !playOne;
    }

    public int getPosition() {
        return position;
    }

    public ArrayList<Song> getSongList() {
        return list_song;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void playAudio() {
        Intent serviceIntent = new Intent(context, MusicService.class);
        serviceIntent.putExtra("audioUrl", current_song.getAudioUrl());
        context.startService(serviceIntent);
        isPlaying = true;
    }

    public void pauseAudio() {
        Intent serviceIntent = new Intent(context, MusicService.class);
        serviceIntent.putExtra("pause", true);
        context.startService(serviceIntent);
        isPlaying = false;
    }

    public void stopAudio() {
        context.stopService(new Intent(context, MusicService.class));
        isPlaying = false;
    }

    public void playNextSong() {
        if (position < list_song.size() - 1) {
            stopAudio();
            position++;
            current_song = list_song.get(position);
            playAudio();
        } else {
            Toast.makeText(context, "This is the last Song in the list", Toast.LENGTH_SHORT).show();
        }
    }

    public void playPreviousSong() {
        if (position > 0) {
            stopAudio();
            position--;
            current_song = list_song.get(position);
            playAudio();
        } else {
            Toast.makeText(context, "This is the first Song in the list", Toast.LENGTH_SHORT).show();
        }
    }


}
