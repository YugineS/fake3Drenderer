package com.yug.fake3d;

public class Maze
{

    public static class Item
    {
        private int x;
        private int y;
        private Type type;

        public Item(final int x, final int y, Type type)
        {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public int getX()
        {
            return x;
        }

        public void setX(final int x)
        {
            this.x = x;
        }

        public int getY()
        {
            return y;
        }

        public void setY(final int y)
        {
            this.y = y;
        }

        public Type getType()
        {
            return type;
        }

        public void setType(final Type type)
        {
            this.type = type;
        }

        public static enum Type
        {
            WALL(1),
            FLOOR(0);

            private int key;

            Type(final int key)
            {
                this.key = key;
            }

            public int getKey()
            {
                return key;
            }

            public static Type getByKey(final int key)
            {
                for (Type type : values())
                {
                    if (type.getKey() == key)
                    {
                        return type;
                    }
                }
                return null;
            }
        }

        public boolean equalsByCoordinates(final Item item)
        {
            if (this == item) return true;
            if (item == null) return false;
            return x == item.x &&
                    y == item.y;
        }

    }

    private static int[][] mapTemplate = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private Item[][] map = buildMap(mapTemplate);

    private int mapWidth = map.length;
    private int mapHeight = map[0].length;

    public Item getItem(int x, int y)
    {
        if (x<0 || y<0 || x>mapWidth-1 || y>mapHeight-1)
        {
            return null;
        }
        return map[x][y];
    }

    private Item[][] buildMap(final int[][] template)
    {
        final Item[][] result = new Item[template.length][template[0].length];
        for (int i=0; i<template.length; i++)
        {
            for (int j=0; j<template[i].length; j++)
            {
                result[i][j] = new Item(i, j, Item.Type.getByKey(template[i][j]));
            }
        }
        return result;
    }
}
