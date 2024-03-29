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

import com.example.piano.model.Key;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NUMBER_WHITE_KEY = 14;
    public static final int NUMBER_BLACK_KEY = 10;
    private ArrayList<Key> whiteKeys;
    private ArrayList<Key> blackKeys;
    private int  keyWidth, keyHeight;

    Paint blackPen, whitePen, yellowPen;

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
            whiteKeys.add(new Key(i+1, rect,false));

            if (i!= 0 && i != 3 && i!= 7 && i != 10 ){
                rect = new RectF((float)(i-1)*keyWidth + 0.75f*keyWidth,
                        0,
                        (float) i*keyWidth + 0.25f*keyWidth,
                        0.67f*keyHeight
                        );
                blackKeys.add(new Key(blackCount , rect, false));
                blackCount++;
            }


        }


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

            // Kiểm tra các phím trắng
            for (Key k : whiteKeys) {
                if (k.rect.contains(x, y)) {
                    k.down = isDownAction;
                    whiteKeyFound = true;
                } else {
                    k.down = false; // Reset down state for other keys
                }
            }

            // Kiểm tra các phím đen
            for (Key k : blackKeys) {
                if (k.rect.contains(x, y)) {
                    k.down = isDownAction;
                    blackKeyFound = true;
                } else {
                    k.down = false; // Reset down state for other keys
                }
            }

            // Nếu không có phím trắng nào được nhấn và có phím đen được nhấn
            // thì cập nhật trạng thái của phím đen
            if (!whiteKeyFound && blackKeyFound) {
                for (Key k : blackKeys) {
                    if (k.rect.contains(x, y)) {
                        k.down = isDownAction;
                        break;
                    }
                }
            }
        }

        invalidate();
        return true;
    }




}
