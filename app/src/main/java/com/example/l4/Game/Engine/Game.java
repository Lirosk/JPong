package com.example.l4.Game.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.l4.Activities.ScoresActivity;
import com.example.l4.Game.GameObjects.GameObject;
import com.example.l4.Game.GameObjects.MovingCircle;
import com.example.l4.Game.GameObjects.Player;
import com.example.l4.Game.GameObjects.Shapes.Rectangle;
import com.example.l4.Listeners.MovementListener;
import com.example.l4.Point;
import com.example.l4.R;
import com.example.l4.Game.Score;

import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    public static final float ERR = 1;

    private final GameLoop gameLoop;
    private final Player player;
    private final MovementListener movementListener;
    private final Score score;

    private final int numCores;

    public static int width;
    public static int height;

    ArrayList<GameObject> gameObjects;
    public static ArrayList<Point> points = new ArrayList<>();
    public static Context context;

    public Game(Context context) {
        super(context);
        Game.context = context;

        numCores = Runtime.getRuntime().availableProcessors();;

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameObjects = new ArrayList<>();

        movementListener = new MovementListener(context);
        setOnTouchListener(movementListener);
        player = new Player(context, movementListener, R.color.player, width/2f, height-150, 250, 25, 0) {
            @Override
            public void onCollision(GameObject sender, GameObject collisionWith) {
                super.onCollision(sender, collisionWith);
                score.inc();
            }
        };
        gameObjects.add(player);

        int boundColor = R.color.border;
        float woh = 20;
        Rectangle topBound = new Rectangle(context, boundColor, width/2f, 1, (float) width, woh, 0) {
            @Override
            public void update() {

            }
        };
        Rectangle leftBound = new Rectangle(context, boundColor, 1, height/2f, woh, (float) height, 0) {
            @Override
            public void update() {

            }
        };
        Rectangle rightBound = new Rectangle(context, boundColor, width-1, height/2f, woh, (float) height, 0) {
            @Override
            public void update() {

            }
        };
        Rectangle bottomBound = new Rectangle(context, boundColor, width/2f, (float) height-1, (float) width, woh, 0) {
            @Override
            public void update() {

            }

            @Override
            public void onCollision(GameObject sender, GameObject collisionWith) {
                gameOver();
            }
        };

        gameObjects.add(topBound);
        gameObjects.add(leftBound);
        gameObjects.add(rightBound);
        gameObjects.add(bottomBound);

        gameObjects.add(MovingCircle.withRandChars(context, width, height, 50, 1));
//        gameObjects.add(MovingCircle.withRandChars(context, width, height, 25, 0.25f));
//        gameObjects.add(MovingCircle.withRandChars(context, width, height, 100, 4f));
//        gameObjects.add(MovingCircle.withRandChars(context, width, height, 50, 1));
//        gameObjects.add(MovingCircle.withRandChars(context, width, height, 150, 9));
//        gameObjects.add(MovingCircle.withRandChars(context, width, height));
//        gameObjects.add(new MovingCircle(context, R.color.enemy, 300, 200, 50, new Point(0, 10), 1));
//        gameObjects.add(new MovingCircle(context, R.color.enemy, 300, 1000, 50, new Point(0, 20), 1));

        score = new Score(context, 25, 100);
        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);
    }

    @NonNull
    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        drawUPS(canvas);
//        drawFPS(canvas);

        Game.canvas = canvas;

        score.draw(canvas);

        for (GameObject gameObject: gameObjects) {
            gameObject.draw(canvas);
        }

        for (Point point: points) {
            point.draw(canvas);
        }
        points.clear();
    }

    public static Canvas canvas;
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.red);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.red);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        for (GameObject gameObject: gameObjects) {
            gameObject.update();
        }

        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.subList(i + 1, gameObjects.size()).parallelStream().forEach(gameObjects.get(i)::collide);
        }
    }

    public static final String SCORE_EXTRA = "com.example.l4.SCORE";
    public void gameOver() {
        gameLoop.interrupt();
//        gameLoop.join();
        Intent intent = new Intent(context, ScoresActivity.class);
        intent.putExtra(SCORE_EXTRA, score.getScore());
        context.startActivity(intent);
        ScoresActivity.score = score.getScore();
    }
}
