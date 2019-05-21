package com.yug.fake3d;

public class GameContext
{
    private GameContext()
    {
        player.setX(7);
        player.setY(7);
    }

    public static final GameContext instance = new GameContext();

    private Player player = new Player();
    private Maze maze = new Maze();

    public Player getPlayer()
    {
        return player;
    }

    public Maze getMaze()
    {
        return maze;
    }
}
