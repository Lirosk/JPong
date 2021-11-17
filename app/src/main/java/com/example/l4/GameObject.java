package com.example.l4;

import android.graphics.Canvas;

public abstract class GameObject {
    protected float x;
    protected float y;

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();
}
