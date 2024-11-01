package com.example.cse441_music;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.bumptech.glide.Glide;
import com.example.cse441_music.BackgroundService.MusicService;
import com.example.cse441_music.Model.Song;


import android.view.GestureDetector;
import android.view.MotionEvent;


import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    Song current_song;
    private ArrayList<Song> list_song;
    private int position;
    private SeekBar seekBar;
    private TextView currentTime, totalDuration;
    private ImageView songImage;
    private ObjectAnimator rotateAnimator;
    private TextView songTitleView, song_artist;
    private ImageView nextButton, preButton, statusSong, imgview_one_song;

    private int check_play = 0;
    private boolean play_one = false;

    private GestureDetectorCompat gestureDetector;


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
                if(play_one) {
                    playAudio();
                } else {
                    playNextSong();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        stopService(new Intent(this, MusicService.class));

        gestureDetector = new GestureDetectorCompat(this, new SwipeDownGestureListener());

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
        song_artist = findViewById(R.id.song_artist);

        imgview_one_song = findViewById(R.id.imgview_one_song);
        imgview_one_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!play_one) {
                    imgview_one_song.setImageResource(R.drawable.icon_ciclre);
                } else {
                    imgview_one_song.setImageResource(android.R.drawable.stat_notify_sync);
                }
                play_one = !play_one;
            }
        });

        songTitleView.setText(current_song.getName());
        song_artist.setText(current_song.getArtistName());
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
            playPretSong();
        });

        rotateAnimator = ObjectAnimator.ofFloat(songImage, "rotation", 0f, 360f);
        rotateAnimator.setDuration(10000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        statusSong.setOnClickListener(view -> {
            if (check_play == 0) {
                statusSong.setImageResource(android.R.drawable.ic_media_pause);
                playAudio();
                check_play = 1;
            } else {
                pauseAudio();
                statusSong.setImageResource(android.R.drawable.ic_media_play);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class SwipeDownGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getY() - e1.getY() > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("song_title", current_song.getName());
                resultIntent.putExtra("song_artist", current_song.getArtistName());
                resultIntent.putExtra("song_pic_url", current_song.getImageUrl());
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
            }
            return false;
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(seekBarReceiver);
//    }

    private void updateUIForCurrentSong() {
        songTitleView.setText(current_song.getName());
        song_artist.setText(current_song.getArtistName());
        Glide.with(this)
                .load(current_song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(songImage);
    }

    private void playAudio() {
        stopAudio();
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
            Toast.makeText(this, "This is the last Song in the list", Toast.LENGTH_SHORT).show();
        }
    }

    private void playPretSong() {
        if (position > 0) {
            position--;
            current_song = list_song.get(position);
            updateUIForCurrentSong();
            playAudio();
        } else {
            Toast.makeText(this, "This is the first Song in the list", Toast.LENGTH_SHORT).show();
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
