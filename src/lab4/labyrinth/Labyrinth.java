package lab4.labyrinth;

import lab4.datastructures.IGrid;

import java.awt.*;
import java.util.Random;

public class Labyrinth implements ILabyrinth {

    private IGrid<LabyrinthTile> tiles;
    private int playerPosX = 0;
    private int playerPosY = 0;
    private int gold = 0;
    private Random random = new Random();
    private int hp = random.nextInt(51) + 50;
    private int damaged = 0;


    public Labyrinth(IGrid<LabyrinthTile> tiles) {
        this.tiles = tiles.copy();
        int players = 0;

        for (int i = 0; i < tiles.getHeight(); i++) {
            for (int j = 0; j < tiles.getWidth(); j++) {
                if (tiles.get(j,i) == LabyrinthTile.PLAYER) {
                    players++;
                    playerPosX = j;
                    playerPosY = i;
                }
                if (tiles.get(j,i) == null) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (players > 1 || players == 0) {
            throw new IllegalArgumentException();
        }
    }


    public LabyrinthTile getCell(int x, int y) {
        if(x < 0 || x >= tiles.getWidth())
            throw new IllegalArgumentException();
        if(y < 0 || y >= tiles.getHeight())
            throw new IllegalArgumentException();

        return tiles.get(x, y);
    }


    public Color getColor(int x, int y) {
        if(x < 0 || x >= tiles.getWidth())
            throw new IllegalArgumentException();
        if(y < 0 || y >= tiles.getHeight())
            throw new IllegalArgumentException();

        return tiles.get(x, y).getColor();
    }


    public int getHeight() {
        return tiles.getHeight();
    }


    public int getWidth() {
        return tiles.getWidth();
    }


    public int getPlayerGold() {
        return gold;
    }


    public int getPlayerHitPoints() {
        return hp;
    }


    public boolean isPlaying() {
        return true;
    }

    public void setHealth(int newHP) {
        hp = newHP;
    }

    public int howMuchDamageTaken() {
        return damaged;
    }


    public void movePlayer(Direction dir) throws MovePlayerException {
            if (dir == Direction.NORTH && playerCanGo(dir)) {
                checkCell(dir);
                tiles.set(playerPosX, playerPosY + 1, LabyrinthTile.PLAYER);
                tiles.set(playerPosX, playerPosY, LabyrinthTile.OPEN);
                playerPosY++;
            } else if (dir == Direction.EAST && playerCanGo(dir)) {
                checkCell(dir);
                tiles.set(playerPosX + 1, playerPosY, LabyrinthTile.PLAYER);
                tiles.set(playerPosX, playerPosY, LabyrinthTile.OPEN);
                playerPosX++;
            } else if (dir == Direction.SOUTH && playerCanGo(dir)) {
                checkCell(dir);
                tiles.set(playerPosX, playerPosY - 1, LabyrinthTile.PLAYER);
                tiles.set(playerPosX, playerPosY, LabyrinthTile.OPEN);
                playerPosY--;
            } else if (dir == Direction.WEST && playerCanGo(dir)) {
                checkCell(dir);
                tiles.set(playerPosX - 1, playerPosY, LabyrinthTile.PLAYER);
                tiles.set(playerPosX, playerPosY, LabyrinthTile.OPEN);
                playerPosX--;
            } else {
                throw new MovePlayerException("Player can't move in this direction.");
        }
    }


    public boolean playerCanGo(Direction d) {
        if (d == Direction.NORTH) {
            return inBounds(d) && nextCell(d) != LabyrinthTile.WALL;
        } else if (d == Direction.EAST) {
            return inBounds(d) && nextCell(d) != LabyrinthTile.WALL;
        } else if (d == Direction.SOUTH) {
            return inBounds(d) && nextCell(d) != LabyrinthTile.WALL;
        } else {
            return inBounds(d) && nextCell(d) != LabyrinthTile.WALL;
        }
    }

    public LabyrinthTile nextCell(Direction d) {
        if (d == Direction.NORTH) {
            return getCell(playerPosX, playerPosY + 1);
        } else if (d == Direction.EAST) {
            return getCell(playerPosX + 1, playerPosY);
        } else if (d == Direction.SOUTH) {
            return getCell(playerPosX, playerPosY - 1);
        } else {
            return getCell(playerPosX - 1, playerPosY);
        }
    }

    public void checkCell(Direction dir) throws MovePlayerException {
        if (nextCell(dir) == LabyrinthTile.GOLD) {
            gold++;
        }
        if (nextCell(dir) == LabyrinthTile.MONSTER) {
            while (hp > 0) {
                int roll = random.nextInt(2);
                if (roll == 0) {
                    roll = random.nextInt(10);
                    hp -= roll;
                    damaged += roll;
                } else {
                    break;
                }
            }
        }
        if (hp <= 0) {
            throw new MovePlayerException("YOU DIED!");
        }
    }

    public boolean inBounds(Direction d) {
        if (d == Direction.NORTH) {
            return playerPosY + 1 < tiles.getHeight();
        } else if (d == Direction.EAST) {
            return playerPosX + 1 < tiles.getWidth();
        } else if (d == Direction.SOUTH) {
            return playerPosY - 1 >= 0;
        } else {
            return playerPosX - 1 >= 0;
        }
    }
}
