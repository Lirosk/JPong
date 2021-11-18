package com.example.l4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.l4.Engine.Game;

public class Point {
    public final float x;
    public final float y;

    public static int counter = 0;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point neg() {
        return new Point(-x, -y);
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point sub(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    public Point mul(float a) {
        return new Point(x * a, y * a);
    }

    public Point div(float a) {
        return new Point(x / a, y / a);
    }

    public float angle() {
        if (y == 0) {
            return 0;
        }
        if (x == 0) {
            return (float) Math.PI/2;
        }
        return (float) Math.atan((float) y/x);
    }

    public float angle(Point point) {
        float x1 = x;
        float y1 = y;
        float x2 = point.x;
        float y2 = point.y;

        float l1 = (float) Math.sqrt(x1*x1 + y1*y1);
        float l2 = (float) Math.sqrt(x2*x2 + y2*y2);

        return (float) Math.acos((x1*x2+y1*y2)/(l1*l2));
    }

    public Point rotate(float angle) {
        float nx = (float) (x*Math.cos(angle) - y*Math.sin(angle));
        float ny = (float) (x*Math.sin(angle) + y*Math.cos(angle));

        return new Point(nx, ny);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        if (counter % 3 == 0) {
            paint.setColor(ContextCompat.getColor(Game.context, R.color.point));
        }
        else if (counter % 3 == 1) {
            paint.setColor(ContextCompat.getColor(Game.context, R.color.white));
        }
        else {
            paint.setColor(ContextCompat.getColor(Game.context, R.color.blue));
        }
        counter ++;
        canvas.drawCircle(x, y, 10, paint);
    }
}