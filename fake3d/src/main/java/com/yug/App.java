package com.yug;

import com.yug.common.Game;
import com.yug.common.GameLoop;
import com.yug.common.GameScreen;
import com.yug.fake3d.GameContext;
import com.yug.fake3d.Maze;
import com.yug.fake3d.Player;

import java.awt.*;


/**
 * Fake 3d Rendering application.
 */
public class App
{
    public static void main(String[] args) throws Exception
    {
        GameScreen gameScreen = new GameScreen(800, 600, "TestGame");
        Game game = new TestGame(gameScreen);
        final GameLoop loop = new GameLoop(game);
        loop.start();
    }

    public static class TestGame extends Game
    {
        private Player player = GameContext.instance.getPlayer();
        private Maze maze = GameContext.instance.getMaze();
        private double rayTraceStep = 0.01;

        public TestGame(GameScreen gameScreen)
        {
            super(gameScreen);
            gameScreen.addDirectionKeyListener(player.getDirectionKeyListener());
        }

        @Override
        public void update(float deltaTimeSec)
        {
            player.update(deltaTimeSec);
        }

        @Override
        public void render(Graphics graphics, float deltaTimeSec)
        {
            Maze.Item lastWallMazeItem = null;
            for (int screenX = 0; screenX < gameScreen.getWidth(); screenX++)
            {
                double rayAngle = (player.getViewAngleRadians() - player.getFieldOfViewRadians() / 2.0) + (player.getFieldOfViewRadians() / (double) gameScreen.getWidth()) * (double) screenX;
                double distanceToWall = 0.0;
                boolean rayTraceFinished = false;
                Maze.Item currentWallMazeItem = null;
                while (!rayTraceFinished)
                {
                    distanceToWall += rayTraceStep;
                    final double rayUnitVectorX = Math.sin(rayAngle);
                    final double rayUnitVectorY = Math.cos(rayAngle);
                    final int mazeX = (int)(rayUnitVectorX * distanceToWall + player.getX());
                    final int mazeY = (int)(rayUnitVectorY * distanceToWall + player.getY());
                    currentWallMazeItem = maze.getItem(mazeX, mazeY);
                    rayTraceFinished = currentWallMazeItem == null || Maze.Item.Type.WALL == currentWallMazeItem.getType();
                }
                final boolean isWallBorder = (lastWallMazeItem == null && currentWallMazeItem!=null) || (lastWallMazeItem!=null&&!lastWallMazeItem.equalsByCoordinates(currentWallMazeItem));
                lastWallMazeItem = currentWallMazeItem;
                Color wallColor = Color.BLACK;
                if (isWallBorder)
                {
                    wallColor = Color.WHITE;
                }
                else if(currentWallMazeItem == null)
                {
                    wallColor = Color.BLACK;
                }
                else
                {
                    final int colorChannel = distanceToWall == 0 || 200/distanceToWall > 200 ? 200:(int)(200/distanceToWall);
                    wallColor = new Color(0, 0, colorChannel);
                }
                Color cellingColor = Color.DARK_GRAY;
                Color floorColor = Color.GRAY;
                int cellingHeight = (int)(gameScreen.getHeight()/2.0d - gameScreen.getHeight()/(distanceToWall+1));
                cellingHeight = cellingHeight > 0 ? cellingHeight : 0;
                int wallHeight = gameScreen.getHeight() - cellingHeight*2;
                int floorHeight = cellingHeight;

                graphics.setColor(cellingColor);
                graphics.fillRect(screenX,0, 1, cellingHeight);
                graphics.setColor(wallColor);
                graphics.fillRect(screenX, cellingHeight, 1, wallHeight);
                graphics.setColor(floorColor);
                graphics.fillRect(screenX, cellingHeight+wallHeight, 1, floorHeight);
            }

        }

    }
}
