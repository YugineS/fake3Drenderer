package com.yug.common;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameScreen implements GameLoopListener
{
    private int width;
    private int height;
    private GameRenderer renderer;
    private JFrame window;
    private Canvas canvas;
    BufferStrategy graphicsBuffer;

    public GameScreen(final int width, final int height, final String title)
    {
        this.width = width;
        this.height = height;
        window = new JFrame(title);
        window.setIgnoreRepaint(true);
        window.setResizable(false);
        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(width, height);
        window.add(canvas);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.createBufferStrategy(2);
        graphicsBuffer = canvas.getBufferStrategy();
    }

    public void onNextFrame(float deltaTimeSec)
    {
        Graphics graphics = null;
        try
        {
            graphics = graphicsBuffer.getDrawGraphics();
            renderer.render(graphics, deltaTimeSec);
            if (!graphicsBuffer.contentsLost())
            {
                graphicsBuffer.show();
            }
        } finally
        {
            if (graphics != null)
            {
                graphics.dispose();
            }
        }
    }

    public void setRenderer(final GameRenderer renderer)
    {
        this.renderer = renderer;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void addDirectionKeyListener(final DirectionKeyListener directionKeyListener)
    {
        window.addKeyListener(new AwtDirectionKeyAdapter(directionKeyListener));
    }
}
