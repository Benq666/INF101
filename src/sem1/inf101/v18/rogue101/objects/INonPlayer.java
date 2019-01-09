package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.rogue101.game.IGame;

/**
 * An actor controlled by the computer
 *
 * @author anya
 *
 */
public interface INonPlayer extends IActor {
	/**
	 * Do one turn for this non-player
	 *
	 * This INonPlayer will be the game's current actor ({@link IGame#getActor()})
	 * for the duration of this method call.
	 *
	 * @param game
	 *            Game, for interacting with the world
	 */
	void doTurn(IGame game);

	/**
	 * Add the food to an actor (i.e. for surviving mechanics)
	 *
	 * @param f Int units of food to add
	 */
	void addFood(int f);
}
