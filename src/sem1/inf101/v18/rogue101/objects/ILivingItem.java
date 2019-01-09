package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.game.IGame;

/**
 * Dedicated interface for items that need to do something every turn.
 * Similar to {@link IPlayer} and {@link INonPlayer} objects.
 * These items will still be counted as items,
 * so the NPCs and Player will be able to interact with them.
 * For example, the rabbit will not eat an INonPlayer Carrot,
 * due to {@link sem1.inf101.v18.rogue101.map.GameMap#canGo(ILocation)} method.
 *
 */
public interface ILivingItem extends IActor {

    /**
     * Do one turn for this item
     *
     * This item will be the game's current "actor" ({@link IGame#getActor()})
     * for the duration of this method call.
     *
     * @param game
     *            Game, for interacting with the world
     */
    void doTurn(IGame game);

    /**
     * Check if the living item was picked up
     *
     * @return true if it was, false otherwise
     */
    boolean wasPickedUp();

    /**
     * Change the pickedUp status of an living item
     * For example, some items can continue to "live" even after they were picked up
     *
     * @param status
     *              True when someone picks an living item
     */
    void pickedUp(boolean status);
}
