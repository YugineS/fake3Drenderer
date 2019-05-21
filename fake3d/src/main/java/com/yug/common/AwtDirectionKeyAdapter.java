package com.yug.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AwtDirectionKeyAdapter implements KeyListener
{
    private DirectionKeyListener directionKeyListener;

    public AwtDirectionKeyAdapter(final DirectionKeyListener directionKeyListener)
    {
        this.directionKeyListener = directionKeyListener;
    }

    public void keyTyped(final KeyEvent e)
    {

    }

    public void keyPressed(final KeyEvent e)
    {
        if (KeyEvent.VK_UP == e.getKeyCode())
        {
            directionKeyListener.onUpPressed();
        }
        else if(KeyEvent.VK_DOWN == e.getKeyCode())
        {
            directionKeyListener.onDownPressed();
        }
        if (KeyEvent.VK_RIGHT == e.getKeyCode())
        {
            directionKeyListener.onRightPressed();
        }
        else if (KeyEvent.VK_LEFT == e.getKeyCode())
        {
            directionKeyListener.onLeftPressed();
        }
    }

    public void keyReleased(final KeyEvent e)
    {
        if (KeyEvent.VK_UP == e.getKeyCode())
        {
            directionKeyListener.onUpReleased();
        }
        else if(KeyEvent.VK_DOWN == e.getKeyCode())
        {
            directionKeyListener.onDownReleased();
        }
        if (KeyEvent.VK_RIGHT == e.getKeyCode())
        {
            directionKeyListener.onRightReleased();
        }
        else if (KeyEvent.VK_LEFT == e.getKeyCode())
        {
            directionKeyListener.onLeftReleased();
        }
    }
}
