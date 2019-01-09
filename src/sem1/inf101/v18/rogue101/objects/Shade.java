package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.rogue101.game.IGame;

public class Shade implements IItem {
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
        return "shade";
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_SHADE;
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        return 0;
    }
}
