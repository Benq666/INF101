package sem2.inf101.v18.sem2.FourInARow.Elements;

import sem2.inf101.v18.sem2.FourInARow.Grid.MyGrid;
import sem2.inf101.v18.sem2.FourInARow.Utils.ConsoleColors;

/**
 *  An element of the game board. Contains information about it's position,
 *  color and symbol, that represents the element.
 *  There are several methods set() and get() methods as well.
 *
 */
public interface IGameElement {

    /**
     * Set the color for the element.
     *
     * @param s New color.
     */
    void setColor(String s);

    /**
     * Returns the actual code for the color from the {@link ConsoleColors} class.
     * This code is used in the System.out printing statement to color the output.
     * The code is retrieved with the help of the reflection. The method extracts
     * the field from the class and then extracts the value from this field.
     *
     * The code is used in the System.out statements and works like a wrapper.
     * First, the code is inserted before the string that need to be colored.
     * Then goes the string itself. After that goes the {@link ConsoleColors} #RESET
     * value, which restores the default color.
     *
     * Example - System.out.println(ConsoleColors.RED + "RED text" + ConsoleColors.RESET + "NORMAL text");
     *
     * @param mod Modification of the color. It can be "BOLD" or "UNDERLINED", f.ex.
     *
     * @return The color code.
     */
    String getColorCode(String mod);

    /**
     * Same as above, but without the modifier. This method is used to get the code of the original color.
     *
     * @return The color code.
     */
    String getColorCode();

    /**
     * Return current color of the element. Instead of returning the color code,
     * this method return the string that contains the actual color word, "RED" or "BLUE" f.ex.
     *
     * @return Color of the element.
     */
    String getColor();

    /**
     * Set the symbol for the element which will represent it on the game board.
     *
     * @param symb New symbol.
     */
    void setSymb(char symb);

    /**
     * Return current symbol of the element. It can be different from the original symbol.
     * Temporal symbol is used to keep the symbols of the connected elements
     * to represent a connected row.
     *
     * @return Temporal symbol of the element.
     */
    char getTempSymb();

    /**
     * Return the original symbol. This symbol is set when the element is created
     * and can't be changed.
     *
     * @return The original symbol of the element.
     */
    char getOrigSymb();

    /**
     * Resets the temporal symbol of the element to the original value.
     *
     */
    void resetSymb();

    /**
     * Returns the X position of the element in the the {@link MyGrid}.
     *
     * @return X position of the element.
     */
    int getXPos();

    /**
     * Returns the Y position of the element in the the {@link MyGrid}.
     *
     * @return Y position of the element.
     */
    int getYPos();

    /**
     * Set new X position for the element.
     *
     * @param xPos X position.
     */
    void setXPos(int xPos);

    /**
     * Set new Y position for the element.
     *
     * @param yPos Y position.
     */
    void setYPos(int yPos);
}
