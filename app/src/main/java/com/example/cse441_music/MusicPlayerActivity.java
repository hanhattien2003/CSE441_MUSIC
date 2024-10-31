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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


import com.example.cse441_music.BackgroundService.MusicService;

public class MusicPlayerActivity extends AppCompatActivity {

    private String audioUrl;
    private SeekBar seekBar;
    private TextView currentTime, totalDuration;
    private ImageView songImage;
    private ObjectAnimator rotateAnimator;
    private Button statusSong;

    private int check_play = 0;

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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String imageUrl = intent.getStringExtra("imageUrl");
        audioUrl = intent.getStringExtra("audioUrl");

        songImage = findViewById(R.id.song_image);
        TextView songTitleView = findViewById(R.id.song_title);

        statusSong = findViewById(R.id.pause_button);
        Button stopButton = findViewById(R.id.stop_button);
        seekBar = findViewById(R.id.seek_bar);
        currentTime = findViewById(R.id.current_time);
        totalDuration = findViewById(R.id.total_duration);

        songTitleView.setText(songTitle);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(songImage);

        // Tạo ObjectAnimator để xoay tròn hình ảnh
        rotateAnimator = ObjectAnimator.ofFloat(songImage, "rotation", 0f, 360f);
        rotateAnimator.setDuration(10000); // Thời gian xoay 10 giây
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn


        statusSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (check_play){
                    case 0:
                        playAudio();
                        statusSong.setBackgroundResource(R.drawable.ic_toolbar);
                        check_play = 1;
                        break;
                    case 1:
                        pauseAudio();
                        statusSong.setBackgroundResource(R.drawable.ic_play);
                        check_play = 0;
                    default:

                }
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

    private void playAudio() {
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("audioUrl", audioUrl);
        startService(serviceIntent);
        rotateAnimator.start();
    }

    private void pauseAudio() {
        if (check_play == 0) {
            playAudio();

        } else {
            check_play = 1;
            Intent serviceIntent = new Intent(this, MusicService.class);
            serviceIntent.putExtra("pause", true);
            startService(serviceIntent);
            rotateAnimator.pause();
        }

    }

    private void stopAudio() {
        stopService(new Intent(this, MusicService.class));
        rotateAnimator.end();
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
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(seekBarReceiver);
    }
}