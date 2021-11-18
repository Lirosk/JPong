package com.example.l4.GameObjects;

import android.content.Context;

import com.example.l4.GameObjects.Shapes.Circle;
import com.example.l4.Point;
import com.example.l4.R;
import com.example.l4.Utils;

public class MovingCircle extends Circle {
    public MovingCircle(Context context, int colorId, float x, float y, float radius, Point velocity, float weight) {
        super(context, colorId, x, y, radius, weight);

        this.velocity = velocity;
    }

    public MovingCircle(Context context, int colorId, float x, float y, float radius, float weight) {
        super(context, colorId, x, y, radius, weight);

        velocity = new Point(0, 0);
    }

    public static MovingCircle withRandChars(Context context, float width, float height) {
        float movingCircleRadius = 50;
        float movingCircleX = Utils.rand(2*movingCircleRadius, width - 2*movingCircleRadius);
        float movingCircleY = 2 * movingCircleRadius;

        float movingCircleSpeed = Utils.rand(20, 40);
        float movingCircleMovementAngle = Utils.rand(210, 330);

        float movingCircleVelocityX = (float) (movingCircleSpeed * Math.cos(movingCircleMovementAngle));
        float movingCircleVelocityY = (float) (movingCircleSpeed * Math.sin(movingCircleMovementAngle));

        return new MovingCircle(context, R.color.enemy, movingCircleX, movingCircleY, movingCircleRadius, new Point(movingCircleVelocityX, movingCircleVelocityY), 1);
    }
}
