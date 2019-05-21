package com.yug.fake3d;

import com.yug.common.DirectionKeyListener;
import com.yug.common.GameUpdateHandler;

import java.util.HashMap;
import java.util.Map;

public class Player implements GameUpdateHandler
{
    private static final double TURN_VELOCITY_RADIANS_PER_SEC = 1;
    private static final double MOVE_VELOCITY_MAZE_POINTS_PER_SEC = 3.5;

    private double x;
    private double y;
    private double viewAngleRadians = Math.PI;
    private double fieldOfViewRadians = Math.PI/4;
    private DirectionKeyListener playerKeyListener = new PlayerKeyListener();

    private Map<GameUpdateHandler, Boolean> updateHandlers = new HashMap<>();
    private GameUpdateHandler turnLeftHandler = deltaTimeSec -> {viewAngleRadians = viewAngleRadians - TURN_VELOCITY_RADIANS_PER_SEC * deltaTimeSec;};
    private GameUpdateHandler turnRightHandler = deltaTimeSec -> {viewAngleRadians = viewAngleRadians + TURN_VELOCITY_RADIANS_PER_SEC * deltaTimeSec;};
    private GameUpdateHandler moveForwardHandler = deltaTimeSec -> {
        final Maze maze = GameContext.instance.getMaze();
        double newX = x + Math.sin(viewAngleRadians) * MOVE_VELOCITY_MAZE_POINTS_PER_SEC * deltaTimeSec;
        double newY = y + Math.cos(viewAngleRadians) * MOVE_VELOCITY_MAZE_POINTS_PER_SEC * deltaTimeSec;
        Maze.Item mazeItem = maze.getItem((int)newX, (int)newY);
        if (mazeItem!=null && Maze.Item.Type.WALL != mazeItem.getType())
        {
            x = newX;
            y = newY;
        }
    };
    private GameUpdateHandler moveBackwardHandler = deltaTimeSec -> {
        final Maze maze = GameContext.instance.getMaze();
        double newX = x - Math.sin(viewAngleRadians) * MOVE_VELOCITY_MAZE_POINTS_PER_SEC * deltaTimeSec;
        double newY = y - Math.cos(viewAngleRadians) * MOVE_VELOCITY_MAZE_POINTS_PER_SEC * deltaTimeSec;
        Maze.Item mazeItem = maze.getItem((int)newX, (int)newY);
        if (mazeItem!=null && Maze.Item.Type.WALL != mazeItem.getType())
        {
            x = newX;
            y = newY;
        }
    };
    {
        updateHandlers.put(turnLeftHandler, false);
        updateHandlers.put(turnRightHandler, false);
        updateHandlers.put(moveForwardHandler, false);
        updateHandlers.put(moveBackwardHandler, false);
    }

    @Override
    public void update(final float deltaTimeSec)
    {
        updateHandlers.entrySet().stream().filter(entry -> entry.getValue()).forEach(entry -> entry.getKey().update(deltaTimeSec));
    }

    public DirectionKeyListener getDirectionKeyListener()
    {
        return playerKeyListener;
    }

    private class PlayerKeyListener implements DirectionKeyListener
    {
        @Override
        public void onUpPressed()
        {
            updateHandlers.put(moveForwardHandler, true);
        }

        @Override
        public void onUpReleased()
        {
            updateHandlers.put(moveForwardHandler, false);
        }

        @Override
        public void onDownPressed()
        {
            updateHandlers.put(moveBackwardHandler, true);
        }

        @Override
        public void onDownReleased()
        {
            updateHandlers.put(moveBackwardHandler, false);
        }

        @Override
        public void onRightPressed()
        {
            updateHandlers.put(turnRightHandler, true);
        }

        @Override
        public void onRightReleased()
        {
            updateHandlers.put(turnRightHandler, false);
        }

        @Override
        public void onLeftPressed()
        {
            updateHandlers.put(turnLeftHandler, true);
        }

        @Override
        public void onLeftReleased()
        {
            updateHandlers.put(turnLeftHandler, false);
        }
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getViewAngleRadians()
    {
        return viewAngleRadians;
    }

    public void setViewAngleRadians(final double viewAngleRadians)
    {
        this.viewAngleRadians = viewAngleRadians;
    }

    public double getFieldOfViewRadians()
    {
        return fieldOfViewRadians;
    }

    public void setFieldOfViewRadians(final double fieldOfViewRadians)
    {
        this.fieldOfViewRadians = fieldOfViewRadians;
    }
}
