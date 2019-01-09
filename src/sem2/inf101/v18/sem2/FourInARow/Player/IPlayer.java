package sem2.inf101.v18.sem2.FourInARow.Player;

import sem2.inf101.v18.sem2.FourInARow.Elements.Chip;
import sem2.inf101.v18.sem2.FourInARow.Utils.ConsoleColors;

import java.util.List;

/**
 * Representation of the player.
 * The player can be controlled either by human, or by computer.
 *
 * The class contains the symbol value that is used to mark the chips
 * that the player places on the board.
 *
 * It also stores the color to distinguish two players.
 *
 * Some methods are not used at the moment, but they can be useful
 * for potential new features.
 *
 */
public interface IPlayer {

        /**
         * Set symbol that will represent the chips played by the player.
         *
         * @param symb New symbol.
         */
        void setSymb(char symb);

        /**
         * Retrieve the symbol of the player.
         *
         * @return Player's symbol.
         */
        char getSymb();

        /**
         * Set the name of the player. Currently the names are already set.
         *
         * @param name Name for the player.
         */
        void setName(String name);

        /**
         * Retrieve the name of the player.
         *
         * @return Player's name.
         */
        String getName();

        /**
         * Set the color that will be used to paint the chips played by the player.
         *
         * @param color New color.
         */
        void setColor(String color);

        /**
         * Retrieve the value of the color variable.
         *
         * @return The color value.
         */
        String getColor();

        /**
         * Retrieve the actual color code from the {@link ConsoleColors}.
         *
         * @return The color code.
         */
        String getColorCode();

        /**
         * Retrieve the base chip assigned to the player.
         * Not used at the moment.
         *
         * @return Base chip
         */
        Chip getBaseChip();

        /**
         * Retrieve the chip from the list of the chips that were played by the player.
         * Not used at the moment.
         *
         * @param chip Chip to find.
         *
         * @return The chip, if it exists in the list. Null otherwise.
         */
        Chip getChip(Chip chip);

        /**
         * Generate a new chip and assign the color of the player to the chip.
         *
         * The location of position where the chip is being played on the board
         * is saved in the xPos and yPos vars.
         *
         * @param xPos X position of the chip.
         * @param yPos Y position of the chip.
         *
         * @return New chip.
         */
        Chip generateChip(int xPos, int yPos);

        /**
         * Retrieve the list of all the chips that were played by the player.
         * Not used at the moment.
         *
         * @return A list of played chips.
         */
        List<Chip> getPlayedChips();

        /**
         * Clear the list of played chips by the player.
         *
         */
        void clearPlayedChips();
}
