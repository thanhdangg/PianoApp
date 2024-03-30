package com.example.piano.model;

import android.graphics.RectF;

public class Key {
    public int sound;
    public RectF rect;
    public boolean down;
    public int soundId; // ID âm thanh mới

    public Key(int sound, RectF rect, int soundId) {
        this.sound = sound;
        this.rect = rect;
        this.soundId = soundId; // Đặt giá trị cho ID âm thanh
    }

    public Key(int sound, RectF rect, boolean down, int soundId) {
        this.sound = sound;
        this.rect = rect;
        this.down = down;
        this.soundId = soundId; // Đặt giá trị cho ID âm thanh
    }

    public boolean contains(float x, float y) {
        return rect.contains(x, y);
    }
}