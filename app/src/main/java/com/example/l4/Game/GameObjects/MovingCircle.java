package com.example.l4.Game.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.l4.Game.Engine.Game;
import com.example.l4.Game.Engine.GameLoop;
import com.example.l4.Game.GameObjects.Shapes.Circle;
import com.example.l4.Point;
import com.example.l4.R;
import com.example.l4.Utils;

public class MovingCircle extends Circle {
    public MovingCircle(Context context, int colorId, float x, float y, float radius, Point velocity, float mass) {
        super(context, colorId, x, y, radius, mass);
        this.velocity = velocity;
    }

    public MovingCircle(Context context, int colorId, float x, float y, float radius, float mass) {
        super(context, colorId, x, y, radius, mass);

        velocity = new Point(0, 0);
    }

    public static MovingCircle withRandChars(Context context, float width, float height) {
        float movingCircleRadius = Utils.rand(25, 150);
        float mass = movingCircleRadius/50;
        float movingCircleX = Utils.rand(2.2f*movingCircleRadius, width - 2.2f*movingCircleRadius);
        float movingCircleY = Utils.rand(2.2f*movingCircleRadius, height/2 - 2.2f*movingCircleRadius);

        float movingCircleSpeed = Utils.rand(20, 30);
        float movingCircleMovementAngle = Utils.rand((float) (-Math.PI/2f), (float) (Math.PI*1.25f));

        float movingCircleVelocityX = (float) (movingCircleSpeed * Math.cos(movingCircleMovementAngle));
        float movingCircleVelocityY = (float) (movingCircleSpeed * Math.sin(movingCircleMovementAngle));

        return new MovingCircle(context, R.color.enemy, movingCircleX, movingCircleY, movingCircleRadius, new Point(movingCircleVelocityX, movingCircleVelocityY), mass*mass);
    }

    public static MovingCircle withRandChars(Context context, float width, float height, float radius, float mass) {
        MovingCircle res = withRandChars(context, width, height);
        res.radius = radius;
        res.mass = mass;
        return res;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float tmass =  Math.round(mass * 100f) / 100f;
        String text = Integer.toString((int)tmass);
        if (tmass == (float)((int)tmass)) {
            text = Integer.toString((int)tmass);
        }
        Paint paint = new Paint();
        int color = ContextCompat.getColor(Game.context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(radius);
        canvas.drawText(text, x-radius/3, y+radius/2, paint);
    }

    @Override
    public void update() {
//        setVelocityY(velocity.y);
        velocity = new Point(velocity.x, velocity.y + 1f/GameLoop.UPS_PERIOD);
        super.update();
    }
}
