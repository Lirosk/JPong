package com.example.l4.GameObjects.Shapes;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.l4.GameObjects.GameObject;
import com.example.l4.Point;

public abstract class Shape extends GameObject {
    protected final Paint paint;

    public Shape(Context context, int colorId, float x, float y, float weight) {
        super(x, y, weight);
        paint = new Paint();
        int color = ContextCompat.getColor(context, colorId);
        paint.setColor(color);
    }
}
