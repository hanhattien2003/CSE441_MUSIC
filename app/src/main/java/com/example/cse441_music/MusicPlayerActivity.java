package com.example.cse441_music;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.bumptech.glide.Glide;
import com.example.cse441_music.BackgroundService.MusicService;
import com.example.cse441_music.Controller.MusicPlayerController;
import com.example.cse441_music.Model.Song;

import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    private MusicPlayerController musicPlayerController;
    private SeekBar seekBar;
    private TextView currentTime, totalDuration, songTitleView, song_artist;
    private ImageView songImage, statusSong, imgview_one_song, next_button, pre_button;
    private ObjectAnimator rotateAnimator;
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
                if (musicPlayerController.isPlayOne()) {
                    musicPlayerController.playAudio();
                } else {
                    musicPlayerController.playNextSong();
                    updateUIForCurrentSong();
                }
            }

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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        ArrayList<Song> list_song = intent.getParcelableArrayListExtra("songList");
        int position = Integer.parseInt(intent.getStringExtra("songPositon"));

        musicPlayerController = new MusicPlayerController(this, list_song, position);

        songImage = findViewById(R.id.song_image);
        songTitleView = findViewById(R.id.song_title);
        song_artist = findViewById(R.id.song_artist);
        seekBar = findViewById(R.id.seek_bar);
        currentTime = findViewById(R.id.current_time);
        totalDuration = findViewById(R.id.total_duration);
        statusSong = findViewById(R.id.pause_button);
        imgview_one_song = findViewById(R.id.imgview_one_song);
        next_button = findViewById(R.id.next_button);
        pre_button = findViewById(R.id.pre_button);

        imgview_one_song.setOnClickListener(view -> {
            musicPlayerController.togglePlayOne();
            imgview_one_song.setImageResource(musicPlayerController.isPlayOne() ?
                    R.drawable.icon_ciclre : android.R.drawable.stat_notify_sync);
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayerController.playNextSong();
            }
        });

        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayerController.playPreviousSong();
            }
        });

        rotateAnimator = ObjectAnimator.ofFloat(songImage, "rotation", 0f, 360f);
        rotateAnimator.setDuration(10000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        statusSong.setOnClickListener(view -> {
            if (musicPlayerController.isPlaying()) {
                musicPlayerController.pauseAudio();
                statusSong.setImageResource(android.R.drawable.ic_media_play);
                rotateAnimator.pause();
            } else {
                musicPlayerController.playAudio();
                statusSong.setImageResource(android.R.drawable.ic_media_pause);
                rotateAnimator.start();
            }
        });

        gestureDetector = new GestureDetectorCompat(this, new SwipeDownGestureListener());

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

        updateUIForCurrentSong();
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
                sendBackData();
                return true;
            }
            return false;
        }
    }

    private void updateUIForCurrentSong() {
        Song current_song = musicPlayerController.getCurrentSong();
        songTitleView.setText(current_song.getName());
        song_artist.setText(current_song.getArtistName());
        Glide.with(this)
                .load(current_song.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(songImage);
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

    @Override
    protected void onDestroy() {
        sendBackData();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        sendBackData();
        super.onBackPressed();
    }


    private void sendBackData() {
        Intent intent = new Intent();
        intent.putExtra("songPositon", musicPlayerController.getPosition() + "");
        intent.putParcelableArrayListExtra("songList", musicPlayerController.getSongList());
        setResult(RESULT_OK, intent);
        finish();
    }
}
