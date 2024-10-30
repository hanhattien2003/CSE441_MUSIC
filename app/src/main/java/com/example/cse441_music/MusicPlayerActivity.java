package com.example.cse441_music;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String audioUrl;
    private Handler handler = new Handler();
    private SeekBar seekBar;
    private TextView currentTime, totalDuration;
    private ImageView songImage;
    private ObjectAnimator rotateAnimator;

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
        Button playButton = findViewById(R.id.play_button);
        Button pauseButton = findViewById(R.id.pause_button);
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

        mediaPlayer = new MediaPlayer();

        playButton.setOnClickListener(v -> playAudio());
        pauseButton.setOnClickListener(v -> pauseAudio());
        stopButton.setOnClickListener(v -> stopAudio());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void playAudio() {
        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();

                seekBar.setMax(mediaPlayer.getDuration());
                totalDuration.setText(formatTime(mediaPlayer.getDuration()));

                handler.postDelayed(updateSeekBar, 1000);


                rotateAnimator.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            handler.removeCallbacks(updateSeekBar);

            rotateAnimator.pause();
        }else {
            mediaPlayer.start();
            handler.postDelayed(updateSeekBar, 1000);

            rotateAnimator.resume();
        }
    }

    private void stopAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            handler.removeCallbacks(updateSeekBar);
            seekBar.setProgress(0);
            currentTime.setText("00:00");
            totalDuration.setText("00:00");

            // Dừng xoay ảnh
            rotateAnimator.end();
        }
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                currentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this, 1000);
            }
        }
    };

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            handler.removeCallbacks(updateSeekBar);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}