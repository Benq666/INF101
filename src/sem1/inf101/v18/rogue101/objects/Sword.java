package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.rogue101.game.IGame;

public class Sword implements IItem {
    private int hp = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    public int getAttack() {
        return 3;
    }


    public int getDamage() {
        return 5;
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
        return "sword";
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_SWORD;
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
