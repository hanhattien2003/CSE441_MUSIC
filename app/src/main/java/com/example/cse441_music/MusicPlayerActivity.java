package com.example.cse441_music;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cse441_music.BackgroundService.MusicService;
import com.example.cse441_music.Model.Song;

import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    Song current_song;
    private ArrayList<Song> list_song;
    private int position;
    private SeekBar seekBar;
    private TextView currentTime, totalDuration;
    private ImageView songImage;
    private ObjectAnimator rotateAnimator;
    private Button statusSong;
    private int check_play = 0;
    private TextView songTitleView;
    private Button nextButton, preButton;

    private BroadcastReceiver seekBarReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MusicService.ACTION_UPDATE_SEEKBAR)) {
                int currentPosition = intent.getIntExtra(MusicService.EXTRA_CURRENT_POSITION, 0);
                int duration = intent.getIntExtra(MusicService.EXTRA_DURATION, 0);

                seekBar.setMax(duration);
                seekBar.setProgress(currentPosition);
                currentTime.setText(formatTime(currentPosition));
                totalDuration.setText(formatTime(duration));
            } else if (intent.getAction().equals(MusicService.ACTION_SONG_COMPLETED)) {
                playNextSong();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        stopService(new Intent(this, MusicService.class));

        Intent intent = getIntent();
        list_song = intent.getParcelableArrayListExtra("songList");
        position = Integer.parseInt(intent.getStringExtra("songPositon"));
        current_song = list_song.get(position);

        songImage = findViewById(R.id.song_image);
        songTitleView = findViewById(R.id.song_title);
        statusSong = findViewById(R.id.pause_button);
        Button stopButton = findViewById(R.id.stop_button);
        seekBar = findViewById(R.id.seek_bar);
        currentTime = findViewById(R.id.current_time);
        totalDuration = findViewById(R.id.total_duration);

        songTitleView.setText(current_song.getName());
        Glide.with(this)
                .load(current_song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(songImage);

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            playNextSong();
        });

        preButton = findViewById(R.id.pre_button);
        preButton.setOnClickListener(v -> {
            if (position == 0) {
                Toast.makeText(this, "This is the first song of the list", Toast.LENGTH_LONG).show();
            } else {
                position -= 1;
                current_song = list_song.get(position);
                updateUIForCurrentSong();
                playAudio();
            }
        });

        rotateAnimator = ObjectAnimator.ofFloat(songImage, "rotation", 0f, 360f);
        rotateAnimator.setDuration(10000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        statusSong.setOnClickListener(view -> {
            if (check_play == 0) {
                statusSong.setBackgroundResource(R.drawable.ic_toolbar);
                playAudio();
                check_play = 1;
            } else {
                pauseAudio();
                statusSong.setBackgroundResource(R.drawable.ic_play);
                check_play = 0;
            }
        });

        stopButton.setOnClickListener(v -> stopAudio());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Intent serviceIntent = new Intent(MusicPlayerActivity.this, MusicService.class);
                    serviceIntent.putExtra("seekTo", progress);
                    startService(serviceIntent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void updateUIForCurrentSong() {
        songTitleView.setText(current_song.getName());
        Glide.with(this)
                .load(current_song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(songImage);
    }

    private void playAudio() {
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("audioUrl", current_song.getAudioUrl());
        startService(serviceIntent);
        rotateAnimator.start();
    }

    private void pauseAudio() {
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("pause", true);
        startService(serviceIntent);
        rotateAnimator.pause();
    }

    private void stopAudio() {
        stopService(new Intent(this, MusicService.class));
        rotateAnimator.end();
    }

    private void playNextSong() {
        if (position < list_song.size() - 1) {
            position++;
            current_song = list_song.get(position);
            updateUIForCurrentSong();
            playAudio();
        } else {
            Toast.makeText(this, "This is the last song in the list", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(seekBarReceiver, new IntentFilter(MusicService.ACTION_UPDATE_SEEKBAR), Context.RECEIVER_NOT_EXPORTED);
        registerReceiver(seekBarReceiver, new IntentFilter(MusicService.ACTION_SONG_COMPLETED), Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(seekBarReceiver);
    }
}
