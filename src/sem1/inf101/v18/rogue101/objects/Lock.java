package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.rogue101.game.IGame;

public class Lock implements IItem {

    @Override
    public boolean draw(ITurtle painter, double w, double h) {
        return false;
    }

    @Override
    public int getCurrentHealth() {
        return 0;
    }

    @Override
    public int getDefence() {
        return 0;
    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    public String getName() {
        return "locked door";
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_LOCKED_DOOR;
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        return 0;
    }

    /**
     * Set the game level label of the lock to determine where the door leads
     *
     * @param l To which game level this lock leads
     */
    /*public void setLevel(String l) {
        level = l;
    }*/

    /**
     * Return the level game label of the lock
     *
     * @return To which game level this lock leads
     */
    /*public String getLevel() {
        return level;
    }*/
}