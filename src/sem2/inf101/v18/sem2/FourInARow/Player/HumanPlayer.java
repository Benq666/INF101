package sem2.inf101.v18.sem2.FourInARow.Player;

import sem2.inf101.v18.sem2.FourInARow.Elements.Chip;

/**
 * A class that represents human player.
 *
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, char symb, String color) {
        super(name, symb, color);
        chip = new Chip(symb, -1,-1);
    }
}
