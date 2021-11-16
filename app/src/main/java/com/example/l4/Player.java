package com.example.l4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private float x;
    private float y;
    private float width;
    private float height;
    private Paint paint;

    public Player(Context context, float posX, float posY, float width, float height) {
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(
                x -width/2,
                y -height/2,
                x +width/2,
                y +height/2,
                paint
        );
    }

    public void update() {

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
