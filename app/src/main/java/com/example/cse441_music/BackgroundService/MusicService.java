package com.example.cse441_music.BackgroundService;



import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

public class MusicService extends Service {

    public static final String ACTION_UPDATE_SEEKBAR = "com.example.t1.UPDATE_SEEKBAR";
    public static final String EXTRA_CURRENT_POSITION = "current_position";
    public static final String EXTRA_DURATION = "duration";

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler(Looper.getMainLooper());
    private final IBinder binder = new MusicBinder();

    private String audioUrl;
    private boolean isPaused = false;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra("audioUrl")) {
                audioUrl = intent.getStringExtra("audioUrl");
                playAudio();
            } else if (intent.hasExtra("pause")) {
                pauseAudio();
            } else if (intent.hasExtra("seekTo")) {
                int seekPosition = intent.getIntExtra("seekTo", 0);
                seekTo(seekPosition);
            }
        }
        return START_NOT_STICKY;
    }






    private void playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
                isPaused = false;

                mediaPlayer.setOnCompletionListener(mp -> stopSelf());

                startSeekBarUpdates();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
            startSeekBarUpdates();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
            handler.removeCallbacks(updateSeekBar);
        }
    }

    private void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    private void startSeekBarUpdates() {
        handler.postDelayed(updateSeekBar, 1000);
    }

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();

                Intent intent = new Intent(ACTION_UPDATE_SEEKBAR);
                intent.putExtra(EXTRA_CURRENT_POSITION, currentPosition);
                intent.putExtra(EXTRA_DURATION, duration);
                sendBroadcast(intent);

                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            handler.removeCallbacks(updateSeekBar);
        }
    }
}

