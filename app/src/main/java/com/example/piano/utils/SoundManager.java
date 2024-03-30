package com.example.piano.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.os.Handler;


import com.example.piano.R;
import com.example.piano.model.Key;

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

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                if(status != 0){
                    android.util.Log.e("SoundManager", "Error loading sound "+ status);
                }
                else {
                    android.util.Log.d("SoundManager", "Success loading sound "+ status);
                }
            }
        });
        sparseArray = new SparseIntArray();

        handler = new Handler();

    }
    public SoundManager(Context context){
        this.context = context;
        soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                if(status != 0){
                    Log.e("SoundManager", "Error loading sound "+ status);
                }
                else {
                    Log.d("SoundManager", "Success loading sound "+ status);
                }
            }
        });
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                if(status != 0){
                    Log.e("SoundManager", "Error loading sound "+ status);
                }
                else {
                    Log.d("SoundManager", "Success loading sound "+ status);
                }
            }
        });
        sparseArray = new SparseIntArray();

        handler = new Handler();

    }
    public static SoundManager getInstance(){
        if (instance == null){
            instance = new SoundManager();
        }
        return instance;
    }
    public static SoundManager getInstance(Context context){
        if (instance == null){
            instance = new SoundManager(context);
        }
        return instance;
    }

    public void initStreamTypeMedia(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public void addSound(int soundID){
        sparseArray.put(soundID, soundPool.load(context,soundID,1));
    }
    public void playSound(int soundID){
        if(mute){
            return;
        }
        boolean hasSound = sparseArray.indexOfKey(soundID) >= 0;

        if(!hasSound){
            return;
        }

        final int soundId = soundPool.play(sparseArray.get(soundID), 1, 1, 1, 0 , 1f);
        scheduleSoundStop(soundId);

    }
    public void setMute(boolean mute){
        this.mute = mute;
    }
    public void playSoundForKey(Key key) {
        if (sparseArray.indexOfKey(key.soundId) >= 0) {
            int soundId = sparseArray.get(key.soundId);
            soundPool.play(soundId, 1, 1, 1, 0, 1f);
        }
    }
    private void scheduleSoundStop(final  int soundID){
        handler.postDelayed(new Runnable(){
           @Override
           public void run(){
                soundPool.stop(soundID);
           }
        }, STOP_DELAY_MILIS);
    }
    public void init(Context context){
        this.context = context;
        instance.initStreamTypeMedia((Activity)context);
        instance.addSound(R.raw.c3);
        instance.addSound(R.raw.c4);
        instance.addSound(R.raw.d3);
        instance.addSound(R.raw.d4);
        instance.addSound(R.raw.e3);
        instance.addSound(R.raw.e4);
        instance.addSound(R.raw.f3);
        instance.addSound(R.raw.f4);
        instance.addSound(R.raw.a3);
        instance.addSound(R.raw.a4);
        instance.addSound(R.raw.b3);
        instance.addSound(R.raw.b4);
        instance.addSound(R.raw.db3);
        instance.addSound(R.raw.db4);
        instance.addSound(R.raw.eb3);
        instance.addSound(R.raw.eb4);
        instance.addSound(R.raw.gb3);
        instance.addSound(R.raw.gb4);
        instance.addSound(R.raw.ab3);
        instance.addSound(R.raw.ab4);
        instance.addSound(R.raw.bb3);
        instance.addSound(R.raw.bb4);
    }
}
