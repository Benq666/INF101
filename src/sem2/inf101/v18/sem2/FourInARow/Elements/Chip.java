package sem2.inf101.v18.sem2.FourInARow.Elements;

import sem2.inf101.v18.sem2.FourInARow.Utils.ConsoleColors;

import java.lang.reflect.Field;

/**
 * Class that represents the chip that is used in the game.
 * If there are four of these chips in a row - the game is finished.
 *
 * The chip is represented on the game board by the symbol.
 * The symbol is saved in the {@link #origSymb} variable.
 * The color of the chip depends on the player that plays it.
 *
 */
public class Chip implements IGameElement {
    private String color;
    private char tempSymb;
    private char origSymb;
    private int xPos;
    private int yPos;

    public Chip(char symb, int xPos, int yPos) {
        this.origSymb = symb;
        this.tempSymb = symb;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void setColor(String s) {
        color = s;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getColorCode(){
        try {
            Field f = ConsoleColors.class.getField(color);
            return (String) f.get(null);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public String getColorCode(String mod) {
        try {
            Field f = ConsoleColors.class.getField(color + "_" + mod);
            return (String) f.get(null);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void setSymb(char symb) {
        this.tempSymb = symb;
    }

    @Override
    public char getTempSymb() {
        return tempSymb;
    }

    @Override
    public char getOrigSymb() {
        return origSymb;
    }

    @Override
    public void resetSymb() {
        tempSymb = origSymb;
    }

    @Override
    public int getXPos() {
        return xPos;
    }

    @Override
    public int getYPos() {
        return yPos;
    }

    @Override
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}
