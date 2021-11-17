package com.example.l4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Rectangle extends GameObject{
    protected final float width;
    protected final float height;
    protected final Paint paint;

    public Rectangle(Context context, float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(
                x -width/2,
                y -height/2,
                x +width/2,
                y +height/2,
                paint
        );
    }

    @Override
    public void update() {

    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
