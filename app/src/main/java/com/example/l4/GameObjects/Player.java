package com.example.l4.GameObjects;

import android.content.Context;

import com.example.l4.Engine.GameLoop;
import com.example.l4.GameObjects.Shapes.Rectangle;
import com.example.l4.Listeners.MovementListener;
import com.example.l4.Point;

public class Player extends Rectangle {
    public static final float SPEED_PIXELS_PER_SECOND = 600;
    public static final float MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final MovementListener movementListener;

    public Player(Context context, MovementListener movementListener, int colorId, float x, float y, float width, float height, float weight) {
        super(context, colorId, x, y, width, height, weight);
        this.movementListener = movementListener;

    }

    @Override
    public void update() {
        float velocityX = movementListener.getActuatorX() * MAX_SPEED;
        x += velocityX;
        velocity = new Point(velocityX, 0);
    }
}
