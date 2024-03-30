package com.example.piano.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.piano.R;
import com.example.piano.model.Key;
import com.example.piano.utils.SoundManager;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NUMBER_WHITE_KEY = 14;
    public static final int NUMBER_BLACK_KEY = 10;
    private ArrayList<Key> whiteKeys;
    private ArrayList<Key> blackKeys;
    private int  keyWidth, keyHeight;

    Paint blackPen, whitePen, yellowPen;

    private SoundManager soundManager;

    public PianoView(Context context) {
        super(context);
    }

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        whiteKeys = new ArrayList<Key>();
        blackKeys = new ArrayList<Key>();

        whitePen = new Paint();
        whitePen.setColor(Color.WHITE);
        whitePen.setStyle(Paint.Style.FILL);

        blackPen = new Paint();
        blackPen.setColor(Color.BLACK);
        blackPen.setStyle(Paint.Style.FILL);

        yellowPen = new Paint();
        yellowPen.setColor(Color.YELLOW);
        yellowPen.setStyle(Paint.Style.FILL);

        soundManager = SoundManager.getInstance(getContext());
        soundManager.init(context);
    }

    public PianoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        keyWidth = w / NUMBER_WHITE_KEY;
        keyHeight = h;
        int blackCount = 15;


        for (int i = 0; i< NUMBER_WHITE_KEY; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            RectF rect = new RectF(left, 0, right, keyHeight);
            int soundId = getSoundIdForWhiteKey(i);
            whiteKeys.add(new Key(i+1, rect, false, soundId));

            if (i!= 0 && i != 3 && i!= 7 && i != 10 ){
                rect = new RectF((float)(i-1)*keyWidth + 0.75f*keyWidth,
                        0,
                        (float) i*keyWidth + 0.25f*keyWidth,
                        0.67f*keyHeight
                        );
                soundId = getSoundIdForBlackKey(blackCount - 15);
                blackKeys.add(new Key(blackCount , rect, false, soundId));
                blackCount++;
            }




        }


    }
    private int getSoundIdForWhiteKey(int keyIndex) {
        int[] whiteKeySounds = {
                R.raw.c3, R.raw.d3, R.raw.e3, R.raw.f3, R.raw.g3, R.raw.a3, R.raw.b3,
                R.raw.c4, R.raw.d4, R.raw.e4, R.raw.f4, R.raw.g4, R.raw.a4, R.raw.b4
        };

        return whiteKeySounds[keyIndex];
    }
    private int getSoundIdForBlackKey(int keyIndex) {
        int[] blackKeySounds = {
                R.raw.db3, R.raw.eb3, R.raw.gb3, R.raw.ab3, R.raw.bb3,
                R.raw.db4, R.raw.eb4, R.raw.gb4, R.raw.ab4, R.raw.bb4
        };

        return blackKeySounds[keyIndex];
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (Key k: whiteKeys) {
            canvas.drawRect(k.rect, k.down? yellowPen : whitePen);
        }
        for (int i = 1; i< NUMBER_WHITE_KEY; i++)
        {
            canvas.drawLine(i*keyWidth, 0, i*keyWidth, keyHeight , blackPen);
        }
        for(Key k : blackKeys){
            canvas.drawRect(k.rect, k.down? yellowPen : blackPen);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            boolean whiteKeyFound = false;
            boolean blackKeyFound = false;

            for (Key k : blackKeys) {
                if (k.rect.contains(x, y)) {
                    k.down = isDownAction;
                    blackKeyFound = true;
                    if (isDownAction) {
//                        soundManager.playSound(R.raw.a4);
                        soundManager.playSoundForKey(k);
                    }
                } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    k.down = false;
                }
            }

            for (Key k : whiteKeys) {
                if (k.rect.contains(x, y) && !blackKeyFound) {
                    k.down = isDownAction;
                    whiteKeyFound = true;
                    if (isDownAction) {
//                        soundManager.playSound(R.raw.a3);
                        soundManager.playSoundForKey(k);
                    }
                } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    k.down = false;
                }
            }
        }
        invalidate();
        return true;
    }
}
