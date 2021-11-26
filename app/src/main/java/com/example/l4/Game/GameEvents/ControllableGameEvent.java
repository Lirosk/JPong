package com.example.l4.Game.GameEvents;

public interface ControllableGameEvent {
    public int duration = 10_000;

    public void start();
    public void stop();
}
