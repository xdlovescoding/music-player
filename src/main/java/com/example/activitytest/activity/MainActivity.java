package com.example.activitytest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioRouting;
import android.os.Bundle;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.SeekBar;
import android.Manifest;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.content.pm.PackageManager;

import com.example.activitytest.R;
import com.example.activitytest.bean.LrcUtil;
import com.example.activitytest.bean.Mp3Info;
import com.example.activitytest.bean.SongRow;
import com.example.activitytest.service.MyService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Handler myHandler = new Handler();


    private MediaPlayer mediaPlayer = new MediaPlayer();

    private SeekBar seekBar;
    private TextView timeTextView;
    private TextView musicText;
    private TextView musicText2;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private int i = 0;
    private int totalTime;
    private ImageView music;
    private ImageView play;
    Mp3Info mp3Info;
    private ListView MusicList;
    private List<SongRow> songRowList = new ArrayList<>();
    private ValueAnimator valueAnimator;
    //private ImageView album;
    Handler handler = new Handler();
    private LrcAdapter adapter;


    /*如何用list/arr添加音乐
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        play = findViewById(R.id.play);
        music = findViewById(R.id.music);
        seekBar = findViewById(R.id.seekbar);
        timeTextView = findViewById(R.id.text1);
        //musicText = findViewById(R.id.music);
        musicText2 = findViewById(R.id.musicName);
        MusicList = findViewById(R.id.musicList);
        mp3Info = LrcUtil.parseFromFile("/data/data/com.example.activitytest/files/突然好想你 - 徐佳莹.lrc");

        Log.d(TAG, "onCreate: " + mp3Info);
        for (int j = 0; j < mp3Info.getSongRowList().size(); j++) {
            Log.d(TAG, "onCreate: " + j + " " + mp3Info.getSongRowList().get(j));
        }
        adapter = new LrcAdapter(mp3Info);
        MusicList.setAdapter(adapter);
        MusicList.setDivider(null);
        initmed();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            initMediaPlayer(i);
        } else {
            initMediaPlayer(i);
        }

        startService(new Intent(this, MyService.class));


        EventBus.getDefault().post(new MessageEvent());


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (play.getTag() == "state1" && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    valueAnimator.pause();
                    handler.removeCallbacks(updateThread);
                    play.setImageResource(R.drawable.start);
                    play.setTag("state2");
                } else {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause11);
                    valueAnimator.resume();
                    handler.post(updateThread);
                    play.setTag("state1");

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            Log.d(TAG, "int value =" + value);
                            music.setRotation(value);
                        }
                    });
//                    valueAnimator.start();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //       Log.d(TAG, "run: music started" + mediaPlayer.getCurrentPosition());
                        }
                    }, 1000);

                }

            }
        });

    }

    private void initmed() {
        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        valueAnimator.setDuration(6000);
        valueAnimator.setRepeatCount(100);

    }

    public static final String TAG = "MainActivity";

    private void initMediaPlayer(int musicIndex) {
        try {
            File dataDirectory = getFilesDir();
            Log.d(TAG, "initMediaPlayer: " + dataDirectory);
            File file1 = new File(dataDirectory,
                    "突然好想你 - 徐佳莹.mp3");
//            File file2 = new File(dataDirectory, "突然好想你.mp3");
            File[] files = dataDirectory.listFiles();
            mediaPlayer.setDataSource(file1.getPath());

            mediaPlayer.prepare();
            long duration = mediaPlayer.getDuration();
            // Log.d(TAG, "duration: " + duration);


        } catch (Exception e) {
            e.printStackTrace();
        }

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        mediaPlayer.getCurrentPosition();

    }

    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            changeColor(mp3Info);
            Log.d(TAG, "run: " + (System.currentTimeMillis() - startTime));
//            move(mp3Info);
            handler.postDelayed(updateThread, 1000);

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    initMediaPlayer(0);
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    private void playNextMusic() {
        if (mediaPlayer != null && i <= 2 && i >= 0) {
            mediaPlayer.reset();
            if (i == 1) {
                i += 1;
            } else {
                initMediaPlayer(0);
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    private void init() {

    }

    private void playPreMusic() {
        if (mediaPlayer != null && i <= 2 && i >= 0) {
            mediaPlayer.reset();
            if (i == 2) {
                i -= 1;
            } else {
                initMediaPlayer(0);
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    public void changeColor(Mp3Info mp3Info) {
        int currentPosition = mediaPlayer.getCurrentPosition();
        for (int i = 0; i < mp3Info.getSongRowList().size() - 1; i++) {
            if (currentPosition >= mp3Info.getSongRowList().get(i).getTime() &&
                    currentPosition < mp3Info.getSongRowList().get(i + 1).getTime()) {

                int currentPos = adapter.getCurrentPos();
                if (i != currentPos) {
                    adapter.setCurrentPos(i);
                }
                if (i != currentPos && i >= 8) {
                    Log.d(TAG, "changeColor: " + i + " mediaPlayer.getCurrentPosition() " + currentPosition);
                    Log.d(TAG, "changeColor: mp3Info.getSongRowList().get(i).getTime() " + mp3Info.getSongRowList().get(i).getTime() + " mp3Info.getSongRowList().get(i + 1).getTime() " + mp3Info.getSongRowList().get(i + 1).getTime());

//                    MusicList.smoothScrollToPosition(i);
                    int y = adapter.getItemHeight() * (i - 8);
                    Log.d(TAG, "i =" + i + " currentPos " + currentPos + " y " + y);
                    //MusicList.smoothScrollToPosition(i);
                    MusicList.smoothScrollByOffset(1);
                    //MusicList.scrollTo(0, y);
                    //MusicList.requestLayout();
                    //MusicList.invalidate();


                }
                break;
            }
        }

    }

//    public void move(Mp3Info mp3Info) {
//        for (int i = 0; i < mp3Info.getSongRowList().size() - 1; i++) {
//            if (mediaPlayer.getCurrentPosition() >= mp3Info.getSongRowList().get(i + 1).getTime() && i > 9) {
//
//                MusicList.scrollBy(0, 30);
//                break;
//            }
//
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


}

