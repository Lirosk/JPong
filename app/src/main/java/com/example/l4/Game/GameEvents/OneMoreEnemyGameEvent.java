package com.example.l4.Game.GameEvents;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.l4.Game.Engine.Game;
import com.example.l4.Game.GameObjects.GameObject;
import com.example.l4.Game.GameObjects.MovingCircle;

import java.util.Calendar;

public class OneMoreEnemyGameEvent extends GameEvent {
    GameObject spawnedEnemy;

    public OneMoreEnemyGameEvent(Game game) {
        super(game);
        spawnedEnemy = MovingCircle.withRandChars(game.getContext(), game.getWidth(), game.getHeight());
    }

    @Override
    public void start() {
        super.start();

        for (int i = 0; i < game.gameObjects.size(); i++) {
            if (game.gameObjects.get(i).collides(spawnedEnemy)) {
                spawnedEnemy.randomMove();
                i = -1;
            }
        }
        game.addObject(spawnedEnemy);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void stop() {
        game.removeObject(spawnedEnemy);
    }

    @Override
    public void update() {

    }
}
