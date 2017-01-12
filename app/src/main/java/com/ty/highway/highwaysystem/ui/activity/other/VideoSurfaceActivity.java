package com.ty.highway.highwaysystem.ui.activity.other;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.io.File;

/**
 * Created by fuweiwei on 2015/10/9.
 */
public class VideoSurfaceActivity extends BaseActivity  {

    private final String TAG = "VideoSurfaceActivity";
    private SurfaceView sv;
    private Button btn_play, btn_pause;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private int currentPosition = 0;
    private boolean isPlaying;
    private String mPath;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_surface);
        initTitle();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        sv = (SurfaceView) findViewById(R.id.surface);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(click);
        btn_pause.setOnClickListener(click);
        mPath = getIntent().getStringExtra("path");
        // 为SurfaceHolder添加回调
        sv.getHolder().addCallback(callback);
        // 4.0版本之下需要设置的属性
        // 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
        // sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(change);
    }

    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("查看视频");
        setTitleVisiable(View.VISIBLE);
    }
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder 被销毁");
            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder 被创建");
            if (currentPosition > 0) {
                // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
                play(currentPosition);
                currentPosition = 0;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i(TAG, "SurfaceHolder 大小被改变");
        }

    };

    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                // 设置当前播放的位置
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_play:
                    play(0);
                    break;
                case R.id.btn_pause:
                    if(isPlaying){
                        pause();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }

    /*
     * 停止播放
     */
    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            btn_play.setEnabled(true);
            isPlaying = false;
        }
    }

    /**
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void play(final int msec) {
        // 获取视频文件地址
        File file = new File(mPath);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            mediaPlayer.setDataSource(file.getAbsolutePath());
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(sv.getHolder());
            Log.i(TAG, "开始装载");
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "装载完成");
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);
                    // 设置进度条的最大进度为视频流的最大播放时长
                    seekBar.setMax(mediaPlayer.getDuration());
                    // 开始线程，更新进度条的刻度
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                isPlaying = true;
                                while (isPlaying) {
                                    int current = mediaPlayer
                                            .getCurrentPosition();
                                    Log.i(TAG, "播放中：" + current);
                                    seekBar.setProgress(current);
                                    sleep(500);
                                }
                            } catch (Exception e) {
                                new ExceptionUtils().doExecInfo(e.toString(), mContext);
                            }
                        }
                    }.start();

                    btn_play.setEnabled(false);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    btn_play.setEnabled(true);
                    mediaPlayer.reset();
                    isPlaying =false;
                    Toast.makeText(VideoSurfaceActivity.this, "播放完毕", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "播放完毕");
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放
                    play(0);
                    isPlaying = false;
                    Log.i(TAG, "播放错误");
                    return false;
                }
            });
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPlaying=false;
    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            Toast.makeText(this, "重新播放", Toast.LENGTH_SHORT).show();
            btn_pause.setText("暂停");
            return;
        }
        isPlaying = false;
        play(0);


    }

    /**
     * 暂停或继续
     */
    protected void pause() {
        if (btn_pause.getText().toString().trim().equals("继续")) {
            btn_pause.setText("暂停");
            mediaPlayer.start();
            Toast.makeText(this, "继续播放", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btn_pause.setText("继续");
            Toast.makeText(this, "暂停播放", Toast.LENGTH_SHORT).show();
        }

    }

}