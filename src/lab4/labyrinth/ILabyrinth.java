package lab4.labyrinth;

import java.awt.Color;

public interface ILabyrinth {

	/**
	 * Get labyrinth cell contents at x,y
	 * 
	 * @param x
	 * @param y
	 * @return The tile at x,y
	 * @throws IllegalArgumentException
	 *             unless 0 <= x < {@link #getWidth()} and 0 <= y <
	 *             {@link #getHeight()}
	 */
	LabyrinthTile getCell(int x, int y);

	/**
	 * 
	 * Get the color of the cell in a given x,y location.
	 * 
	 * @param x
	 * @param y
	 * @return The color of the cell in the given row and column.
	 */
	Color getColor(int x, int y);

	/**
	 * @return The number of rows.
	 */
	int getHeight();

	/**
	 * @return Current amount of player gold
	 */
	int getPlayerGold();

	/**
	 * @return Current number of player hitpoints
	 */
	int getPlayerHitPoints();

	/**
	 * @return The number of columns.
	 */
	int getWidth();

	/**
	 * @return True if the game is active
	 */
	boolean isPlaying();

	/**
	 * Move player in direction dir
	 * 
	 * @throws MovePlayerException
	 *             if move is invalid (e.g. tile is occupied)
	 */
	void movePlayer(Direction dir) throws MovePlayerException;

	/**
	 * Check if a move is valid
	 * 
	 * @param d
	 *            A direction
	 * @return True if movePlayer(d) is a valid move
	 */
	boolean playerCanGo(Direction d);

}
