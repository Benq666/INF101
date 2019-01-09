package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.rogue101.game.IGame;

public class HealthPotion implements IItem {
    private int hp = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return 1;
    }

    @Override
    public int getMaxHealth() {
        return 15;
    }

    @Override
    public String getName() {
        return "health potion";
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_POTION;
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        hp -= amount;

        if (hp < 0) {
            hp = -1;
        }
        return amount;
    }
}
