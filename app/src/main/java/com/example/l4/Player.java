package com.example.l4;

import android.content.Context;

public class Player extends Rectangle {
    private static final float SPEED_PIXELS_PER_SECOND = 400;
    private static final float MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final MovementListener movementListener;

    public Player(Context context, MovementListener movementListener, float x, float y, float width, float height) {
        super(context, x, y, width, height);
        this.movementListener = movementListener;

    }

    @Override
    public void update() {
        x += movementListener.getActuatorX() * MAX_SPEED;
    }
}
