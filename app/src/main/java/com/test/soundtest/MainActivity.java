package com.test.soundtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // MediaPlayer 객체생성
    MediaPlayer mediaPlayer;
    AudioManager mAudioManager;

    // 시작버튼
    Button startButton;
    //종료버튼
    Button stopButton;
    // 볼륨 크기 세팅 버튼
    Button volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        ButtonListener();
    }

    private void ButtonListener() {
        // 시작 버튼 클릭
        startButton.setOnClickListener(view -> {
            mediaPlayer.start();
        });

        // 정지 버튼 클릭
        stopButton.setOnClickListener(view -> {
            mediaPlayer.stop();
            // 초기화
            mediaPlayer.reset();
        });

        // 볼륨 크기 세팅
        volume.setOnClickListener(view -> {
            // 미디어 볼륨을 50 퍼로 세팅
            double percent = 50;

            mAudioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) * (percent/100)),
                    AudioManager.FLAG_PLAY_SOUND);
        });
    }

    private void Init() {
        // 버튼 아이디
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);
        volume = findViewById(R.id.vloume);

        // 미디어 객체
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sample01);

        // 볼륨 조절 매니저
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
    }


    // MediaPlayer는 시스템 리소스를 잡아먹는다.
    // MediaPlayer는 필요이상으로 사용하지 않도록 주의해야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}