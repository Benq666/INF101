package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.rogue101.game.IGame;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public interface IPlayer extends IActor {
	/**
	 * Send key presses from the human player to the player object.
	 * <p>
	 * The player object should interpret the key presses, and then perform its
	 * moves or whatever, according to the game's rules and the player's
	 * instructions.
	 * <p>
	 * This IPlayer will be the game's current actor ({@link IGame#getActor()}) and
	 * be at {@link IGame#getLocation()}, when this method is called.
	 * <p>
	 * This method may be called many times in a single turn; the turn ends
	 * {@link #keyPressed(IGame, KeyCode)} returns and the player has used its
	 * movement points (e.g., by calling {@link IGame#move(sem1.inf101.v18.grid.GridDirection)}).
	 *
	 * @param game
	 *            Game, for interacting with the world
	 */
	void keyPressed(IGame game, KeyCode key);

	/**
	 * Shows the current status of the player
	 */
	void showStatus(IGame game);

	/**
	 * Show the contents of the player's inventory
	 *
	 * @return
	 *         List of short item names
	 */
	ArrayList<String> showInventory();

	/**
	 * Return the player's inventory, i.e. the IItem list
	 *
	 * @return
	 *         List of items
	 */
	List<IItem> getInventory();

	/**
	 * Set the contents of the player's inventory
	 *
	 * @param oldInv Current player's inventory
	 */
	void setInventory(List<IItem> oldInv);

	/**
	 * Check if the player has a key to advance to the next level
	 *
	 * @return A key from the player's inventory
	 */
	IItem hasKey();
}
