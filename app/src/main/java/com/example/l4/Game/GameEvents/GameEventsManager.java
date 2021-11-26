package com.example.l4.Game.GameEvents;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.l4.Game.Drawable;
import com.example.l4.Game.Engine.Game;
import com.example.l4.Game.Updatable;
import com.example.l4.R;
import com.example.l4.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class GameEventsManager implements Drawable, Updatable {
    public static int gameEventPeriod_ms = 15_000;

    private long lastEvent = 0;
    private Game game = null;
    private int color;

    public ArrayList<GameEvent> events = new ArrayList<>();
    public ArrayList<Object> eventTypes = new ArrayList<>();

    private int x;
    private int y;

    public GameEventsManager(Game game, int x, int y) {
        this.game = game;
        lastEvent = Calendar.getInstance().getTimeInMillis();

        this.x = x;
        this.y = y;
        this.color = ContextCompat.getColor(Game.context, R.color.gameEventTimer);
    }

    public void draw(Canvas canvas) {
        String time = Integer.toString((int)
                Math.abs(
                        Calendar.getInstance().getTimeInMillis()
                        - lastEvent
                        - (long)gameEventPeriod_ms
                )/(1_000) + 1
        );
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(75);
        canvas.drawText(time, x, y, paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        long currTime = Calendar.getInstance().getTimeInMillis();
        if (Math.abs(currTime - lastEvent) >= gameEventPeriod_ms) {
            createEvent();
            lastEvent = currTime;
        }

        GameEvent toRemove = null;
        for (GameEvent event: events) {
            if (currTime - event.startTime > event.duration) {
                event.stop();
                toRemove = event;
            }
            else {
                event.update();
            }
        }

        events.remove(toRemove);
    }

    public void createEvent() {
//        int len = events.size();
//        int chosenOne = (int)Utils.rand(0, len);
//        if (chosenOne == len) {
//            chosenOne -= 1;
//        }

        GameEvent gameEvent;
        float r = Utils.rand(0, 1);
        if (r < 0.5f) {
            gameEvent = new OneMoreEnemyGameEvent(game);
        }
        else {
            gameEvent = new PlayerSlowedMovementGameEvent(game);
        }

        events.add(gameEvent);
        gameEvent.start();
    }
}
