package com.example.l4.GameObjects;

import android.graphics.Canvas;

import com.example.l4.Engine.Game;
import com.example.l4.GameObjects.Shapes.Circle;
import com.example.l4.GameObjects.Shapes.Rectangle;
import com.example.l4.Point;

public abstract class GameObject {
    private static final Object locker = new Object();
    protected float mass;
    protected float x;
    protected float y;
    protected Point velocity;

    public GameObject(float x, float y, float mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.velocity = new Point(0, 0);
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

    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity = new Point(x, y);
    }

    public abstract void draw(Canvas canvas);
    public void update() {
        x += velocity.x;
        y += velocity.y;
    }

    protected abstract Point nearestPoint(float x, float y);
    protected Point nearestPoint(Point point) {
        return nearestPoint(point.x, point.y);
    }
    protected Point nearestPoint(GameObject gameObject){
        return nearestPoint(gameObject.x, gameObject.y);
    }
    protected abstract boolean collides(float x, float y);
    protected boolean collides(Point point) {
        return collides(point.x, point.y);
    }

    public boolean collides(GameObject gameObject) {
        Point nearest = gameObject.nearestPoint(this.x, this.y);
        return collides(nearest);
    }

    public abstract float surfaceAngle(Point point);

    public void collide(GameObject gameObject) {
        if (mass == 0 && gameObject.mass == 0) {
            return;
        }

        Point p1 = this.nearestPoint(gameObject);
        Point p2 = gameObject.nearestPoint(p1);

        boolean c1 = collides(p2);
        boolean c2 = gameObject.collides(p1);

        if (c1 || c2)
        {
            synchronized (locker) {
                // momentum conservation law
                float vx1 = velocity.x;
                float vy1 = velocity.y;

                float vx2 = gameObject.velocity.x;
                float vy2 = gameObject.velocity.y;

                float v1 = (float) Math.sqrt(vx1 * vx1 + vy1 * vy1);
                float v2 = (float) Math.sqrt(vx2 * vx2 + vy2 * vy2);

                float m1 = this.mass;
                float m2 = gameObject.mass;

                float theta1 = velocity.angle();
                float theta2 = gameObject.velocity.angle();

//                float phi = surfaceAngle(p1);
//                float dphi = Math.abs(phi - theta2);

                float rx1 = vx1, ry1 = vy1, rx2 = vx2, ry2 = vy2;
//                phi = surfaceAngle(p1);
//                if (Math.abs(phi) < Game.ERR) {
//                    ry2 = -vy2;
//                }
//                else if (Math.abs(Math.abs(phi) - Math.PI/2) < Game.ERR) {
//                    rx2 = - vx2;
//                }
//                else {
//                    rx1 = -vx1;
//                    ry1 = -vy1;
//                    rx2 = -vx2;
//                    ry2 = -vy2;
//                }

//
                float phi = (float) Math.abs(surfaceAngle(p1)-theta2);

                if (m1 == 0) {
                    phi = surfaceAngle(p1);
                    if (Math.abs(phi) < Game.ERR) {
                        ry2 = -vy2;
                    }
                    else if (Math.abs(Math.abs(phi) - Math.PI/2) < Game.ERR) {
                        rx2 = - vx2;
                    }
                    /*rx1 = vx1;
                    ry1 = vy1;

                    rx2 = (float)(
                            v2*Math.cos(theta2-phi)*Math.cos(phi)
                            +v2*Math.sin(theta2-phi)*Math.cos(phi+Math.PI/2)
                    );
                    ry2 = (float)(
                            v2*Math.cos(theta2-phi)*Math.sin(phi)
                            +v2*Math.sin(theta2-phi)*Math.sin(phi+Math.PI/2)
                    );*/
                } else if (m2 == 0) {
                    phi = surfaceAngle(p2);
                    if (Math.abs(phi) < Game.ERR) {
                        ry1 = -vy1;
                    }
                    else if (Math.abs(Math.abs(phi) - Math.PI/2) < Game.ERR) {
                        rx1 = -vx1;
                    }
                    /*rx1 = (float)(
                            v1*Math.cos(theta1-phi)*Math.cos(phi)
                            +v1*Math.sin(theta1-phi)*Math.cos(phi+Math.PI/2)
                    );
                    ry1 = (float)(
                            v1*Math.cos(theta1-phi)*Math.sin(phi)
                            +v1*Math.sin(theta1-phi)*Math.sin(phi+Math.PI/2)
                    );

                    rx2 = vx2;
                    ry2 = vy2;*/
                } else {
//                    rx1 = vx2;
//                    ry1 = vy2;
//                    rx2 = vx1;
//                    ry2 = vy1;
                    double a1 = v1*Math.cos(theta1-phi)*(m1-m2)+2*m2*v2*Math.cos(theta2-phi);
                    rx1 = (float)(
                        a1
                        *Math.cos(phi)
                        /(m1+m2)
                        +v1*Math.sin(theta1-phi)*Math.cos(phi+Math.PI/2)
                    );
                    ry1 = (float)(
                        a1
                        *Math.sin(phi)
                        /(m1+m2)
                        +v1*Math.sin(theta1-phi)*Math.sin(phi+Math.PI/2)
                    );

                    double a2 = v2*Math.cos(theta2-phi)*(m2-m1)+2*m1*v1*Math.cos(theta1-phi);
                    rx2 = (float)(
                        a2
                        *Math.cos(phi)
                        /(m1+m2)
                        +v2*Math.sin(theta2-phi)*Math.cos(phi+Math.PI/2)
                    );
                    ry2 = (float)(
                        a2
                        *Math.sin(phi)
                        /(m1+m2)
                        +v2*Math.sin(theta2-phi)*Math.sin(phi+Math.PI/2)
                    );
                }

                this.velocity = new Point(rx1, ry1);
                gameObject.velocity = new Point(rx2, ry2);

                while (collides(gameObject) || gameObject.collides(this)) {
                    if (m1 != 0) {
                        this.update();
                    }
                    if (m2 != 0) {
                        gameObject.update();
                    }
                }
            }
        }
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public void setVelocityX(float x) {
        velocity = new Point(x, this.y);
    }

    public void setVelocityY(float y) {
        velocity = new Point(this.x, y);
    }
}
