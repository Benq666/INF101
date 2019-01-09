package sem1.inf101.v18.rogue101.examples;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.objects.IItem;

public class GreenMushroom implements IItem {
    private int hp = getMaxHealth();

    @Override
    public boolean draw(ITurtle painter, double w, double h) {
        return false;
    }

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return 0;
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }

    @Override
    public String getName() {
        return "mushroom";
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        hp -= amount;
        return amount;
    }
}
