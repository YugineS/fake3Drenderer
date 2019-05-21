package com.yug.common;

import java.util.concurrent.TimeUnit;

public class GameLoop
{
    private int fps = 60;
    private Thread gameLoopProcessorThread;
    private GameLoopListener gameLoopListener;

    public GameLoop(final GameLoopListener gameLoopListener)
    {
        this.gameLoopListener = gameLoopListener;
    }

    public GameLoop(final GameLoopListener gameLoopListener, final int fps)
    {
        this.gameLoopListener = gameLoopListener;
        this.fps = fps;
    }

    public void start()
    {
        gameLoopProcessorThread = new Thread(new GameLoopProcessor());
        gameLoopProcessorThread.setDaemon(true);
        gameLoopProcessorThread.setName("GameLoopProcessorThread");
        gameLoopProcessorThread.start();
    }

    public void stop()
    {
        if (gameLoopProcessorThread != null && !gameLoopProcessorThread.isInterrupted())
        {
            gameLoopProcessorThread.interrupt();
        }
    }

    private class GameLoopProcessor implements Runnable
    {
        public void run()
        {
            final float frameDeltaTimeReferenceSec = 1 / (float) fps;
            long prevIterationStartTimeMillis = 0;

            while (!Thread.currentThread().isInterrupted())
            {
                final long startTimeMillis = System.currentTimeMillis();
                final float frameDeltaTimeSec = prevIterationStartTimeMillis == 0 ? frameDeltaTimeReferenceSec : (float) (startTimeMillis - prevIterationStartTimeMillis) / 1000;
                prevIterationStartTimeMillis = startTimeMillis;
                gameLoopListener.onNextFrame(frameDeltaTimeSec);
                final long endTimeMillis = System.currentTimeMillis();
                final long gameProcessingTimeMillis = endTimeMillis - startTimeMillis;
                final long timeRemain = (long)(frameDeltaTimeReferenceSec * 1000 - gameProcessingTimeMillis);
                if (timeRemain > 0)
                {
                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(timeRemain);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
