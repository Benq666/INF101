package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.rogue101.game.IGame;

public class Shield implements IItem {

    private int hp = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return 5;
    }

    public int getAttack() {
        return 0;
    }


    public int getDamage() {
        return 0;
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }

    @Override
    public String getName() {
        return "shield";
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_SHIELD;
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
