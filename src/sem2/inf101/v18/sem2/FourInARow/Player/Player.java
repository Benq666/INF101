package sem2.inf101.v18.sem2.FourInARow.Player;

import sem2.inf101.v18.sem2.FourInARow.Elements.Chip;
import sem2.inf101.v18.sem2.FourInARow.Utils.ConsoleColors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {
    private String name;
    private char symb;
    private String color;
    Chip chip;
    private List<Chip> playerChips = new ArrayList<>();

    Player(String name, char symb, String color) {
        setName(name);
        setSymb(symb);
        setColor(color);
        chip = new Chip(symb, -1, -1);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setSymb(char symb) {
        this.symb = symb;
    }

    @Override
    public char getSymb() {
        return symb;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }

    public String getColorCode() {
        try {
            Field f = ConsoleColors.class.getField(color);
            return (String) f.get(null);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Chip getBaseChip() {
        return chip;
    }

    @Override
    public Chip getChip(Chip chip) {
        for (Chip chp: playerChips) {
            if (chip == chp)
                return chp;
        }
        return null;
    }

    @Override
    public List<Chip> getPlayedChips() {
        return playerChips;
    }

    @Override
    public void clearPlayedChips() {
        playerChips.clear();
    }

    @Override
    public Chip generateChip(int xPos, int yPos) {
        Chip newChip = new Chip(chip.getTempSymb(), xPos, yPos);
        newChip.setColor(getColor());
        playerChips.add(newChip);
        return newChip;
    }
}
