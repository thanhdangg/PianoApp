package com.example.piano.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SoundManager {
    private SoundPool soundPool;
    private SparseIntArray sparseArray;
    private boolean mute = false;
    private Context context;

    private static  final  int MAX_STREAM = 10;
    private static final int STOP_DELAY_MILIS =1000;
    private Handler handler;
    private static SoundManager instance = null;

    public SoundManager(){
        soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);

        sparseArray = new SparseIntArray();

        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

    }
    public static SoundManager getInstance(){
        if (instance == null){
            instance = new SoundManager();
        }
        return instance;
    }
    public void init(Context context){
        this.context = context;
        instance.init(context);
    }
}
