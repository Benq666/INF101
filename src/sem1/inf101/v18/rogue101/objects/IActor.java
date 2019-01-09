package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.rogue101.game.IGame;

/**
 * An actor is an IItem that can also do something, either controlled by the
 * computer (INonPlayer) or the user (IPlayer).
 *
 * @author anya
 *
 */
public interface IActor extends IItem {
	/**
	 * @return This actor's attack score (used against an item's
	 *         {@link #getDefence()} score to see if an attack is successful)
	 */
	int getAttack();

	/**
	 * @return The damage this actor deals on a successful attack (used together
	 *         with
	 *         {@link #handleDamage(sem1.inf101.v18.rogue101.game.IGame, IItem, int)} on
	 *         the target)
	 */
	int getDamage();

	/**
	 * @return How far this actor can see, i.e. number of locations from current location
	 * 		   (used with {@link IGame#getVisible()} when current actor searches for something
	 * 		   in visible area around)
	 */
	int getVisibility();
}
