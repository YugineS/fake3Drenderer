package com.yug.common;

import java.awt.*;

public abstract class Game implements GameLoopListener, GameRenderer
{
    protected GameScreen gameScreen;

    public Game(final GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
        gameScreen.setRenderer(this);
    }

    public void onNextFrame(float deltaTimeSec)
    {
        update(deltaTimeSec);
        gameScreen.onNextFrame(deltaTimeSec);
    }

    public abstract void update(float deltaTimeSec);
    public abstract void render(Graphics graphics, float deltaTimeSec);

}
