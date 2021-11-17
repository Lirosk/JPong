package com.example.l4;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import androidx.core.content.ContextCompat;

public class GameLoop extends Thread{
    public static final float MAX_UPS = 30;
    public static final float UPS_PERIOD = 1e3f/MAX_UPS;

    private boolean isRunning = false;

    private double averageUPS;
    private double averageFPS;

    private final SurfaceHolder surfaceHolder;
    private final Game game;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas;
        startTime = System.currentTimeMillis();
        while (isRunning) {
            canvas = surfaceHolder.lockCanvas();
            try {
                game.update();
                updateCount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (canvas != null) {
                    game.draw(canvas);
                    frameCount++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

//            canvas = surfaceHolder.lockCanvas();
//            try {
//                canvas = surfaceHolder.lockCanvas();
//                if (canvas != null) {
//                    synchronized (surfaceHolder) {
//                        game.update();
//                        updateCount++;
//                        game.draw(canvas);
//                    }
//                }
//            }
//            catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            }
//            finally {
//                if (canvas != null) {
//                    try {
//                        surfaceHolder.unlockCanvasAndPost(canvas);
//                        frameCount++;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while(sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > 1_000) {
                averageUPS = updateCount / (1e-3 * elapsedTime);
                averageFPS = frameCount / (1e-3 * elapsedTime);

                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }
}
