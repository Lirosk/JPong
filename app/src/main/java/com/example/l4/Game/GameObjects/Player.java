package com.example.l4.Game.GameObjects;

import android.content.Context;

import com.example.l4.Game.Engine.Game;
import com.example.l4.Game.Engine.GameLoop;
import com.example.l4.Game.GameObjects.Shapes.Rectangle;
import com.example.l4.Listeners.MovementListener;
import com.example.l4.Point;

public class Player extends Rectangle {
    public static final float SPEED_PIXELS_PER_SECOND = 700;
    public static float MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final MovementListener movementListener;

    public Player(Context context, MovementListener movementListener, int colorId, float x, float y, float width, float height, float mass) {
        super(context, colorId, x, y, width, height, mass);
        this.movementListener = movementListener;

    }

    @Override
    public void update() {
        float velocityX = movementListener.getActuatorX() * MAX_SPEED;
        velocity = new Point(velocityX, 0);
        if (x + velocityX + width/2 > Game.width || x + velocityX - width/2 < 0) {
            return;
        }
        x += velocity.x;
    }
}
