package com.example.l4.Game.GameEvents;

import com.example.l4.Game.Engine.Game;
import com.example.l4.Game.GameObjects.Player;
import com.example.l4.Game.Updatable;
import com.example.l4.Point;

public class PlayerSlowedMovementGameEvent extends GameEvent implements Updatable {
    private boolean isActive = false;

    public PlayerSlowedMovementGameEvent(Game game) {
        super(game);
    }

    @Override
    public void start() {
        super.start();
        isActive = true;
        Player.MAX_SPEED /= 2;
    }

    @Override
    public void stop() {
        isActive = false;
        Player.MAX_SPEED *= 2;
    }

    @Override
    public void update() {
//        if (isActive) {
//            Point velocity = game.player.getVelocity();
//            velocity.setLen(velocity.getLen()*0.5f);
//            game.player.setVelocity(velocity);
//        }
    }
}
