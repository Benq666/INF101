package sem2.inf101.v18.sem2.FourInARow.Game;

import sem2.inf101.v18.sem2.FourInARow.Elements.Chip;
import sem2.inf101.v18.sem2.FourInARow.Elements.IGameElement;
import sem2.inf101.v18.sem2.FourInARow.Grid.IGrid;
import sem2.inf101.v18.sem2.FourInARow.Grid.MyGrid;
import sem2.inf101.v18.sem2.FourInARow.Player.Player;

import java.util.List;

/**
 * Main representation of the game.
 *
 * Contains methods for turns, printing the game board
 * and various manipulations with a game board.
 *
 * After each turn the symbol of the last played chip is underlined.
 *
 */
public interface IGame {

    /**
     * A method for printing the game board. Goes through the {@link MyGrid}
     * and prints out the symbols of the elements of the grid as well as adds the colors,
     * depending on different conditions.
     *
     */
    void printBoard();

    /**
     * This method represents the game turn of the players. It goes through the list of players
     * and interacts with the game board by adding elements. It also checks for winning/losing
     * conditions after each player's turn.
     *
     */
    void doTurn();

    /**
     * Adds a {@link Player} to the player list. Currently there is only two players in total,
     * but it's absolutely possible to add more human or computer players.
     *
     * @param player Human/computer player.
     */
    void addPlayer(Player player);

    /**
     * Checks if the win conditions were met, i.e. there are four chips in a row.
     * It's the first condition to stop the loop that iterates through player list,
     * i.e. to finish the game.
     *
     * @return True if the win conditions are met. False otherwise.
     */
    boolean winner();

    /**
     * Checks if there is no more available moves to make, i.e. if it's a draw.
     * Second condition to stop the game.
     *
     * @return True if it's a draw. False otherwise.
     */
    boolean draw();

    /**
     * Change the symbols of the 4 connected chips.
     * Helps to better represent the winning row on the board.
     *
     * @param chips List of 4 connected chips.
     * @param symb Symbol to change the chips' symbols to.
     *             Symbol is the same for all rows at the moment, but can be changed to different one,
     *             depending on the matching row ("━" for horizontal or "│" for vertical, f.ex.).
     */
    void changeSymbols (List<Chip> chips, char symb);

    /**
     * Retrieve the game board.
     *
     * @return Game board.
     */
    IGrid<IGameElement> getBoard();

    /**
     * Set the new game board.
     *
     * @param gameBoard New game board.
     */
    void setBoard(IGrid<IGameElement> gameBoard);

    /**
     * Setup the new game by entering information with the number of players
     * and board size.
     */
    void setup();

    /**
     * Set new number of turns. Can be used to finish game early,
     * for example if a game takes too long.
     *
     * @param turns New turns value.
     */
    void setNumOfTurns(int turns);
}
