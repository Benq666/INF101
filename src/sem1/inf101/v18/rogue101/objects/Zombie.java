package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.game.IGame;

import java.util.Collections;
import java.util.List;

public class Zombie implements INonPlayer {
    private int food = 0;
    private int hp = getMaxHealth();


    @Override
    public void doTurn(IGame game) {
        if (hp <= 0)
            return;
        if (food > 0) {
            hp += 5;
            food--;
        }

        // looking for a target around
        for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
            for (IActor actor : (game.getMap().getActors(game.getLocation(dir)))) {
                if (actor instanceof IPlayer || actor instanceof Rabbit) {
                    game.attack(dir, actor);
                    return;
                }
            }
        }

        // looking for a target in visible range
        List<GridDirection> possibleMoves = game.getPossibleMoves();
        List<ILocation> allVisibleNeighbours = game.getVisible();
        for (ILocation loc : allVisibleNeighbours) {
            for (IActor actor : game.getMap().getActors(loc)) {
                if (actor instanceof IPlayer || actor instanceof Rabbit) {
                    for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
                        if (game.getLocation(dir) == game.getLocation().gridLineTo(loc).get(0)
                                && !game.getMap().hasWall(game.getLocation(dir)) &&
                                !game.getMap().hasActors(game.getLocation(dir))) {
                            game.move(dir);
                            return;
                        }
                    }
                }
            }
        }

        // moving to one of the possible locations if no targets were found in visible range
        if (!possibleMoves.isEmpty()) {
            Collections.shuffle(possibleMoves);
            game.move(possibleMoves.get(0));
        }
    }

    @Override
    public boolean draw(ITurtle painter, double w, double h) {
        return false;
    }

    @Override
    public int getAttack() {
        return 7;
    }

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDamage() {
        return 3;
    }

    @Override
    public int getDefence() {
        return 3;
    }

    @Override
    public int getMaxHealth() {
        return 41;
    }

    @Override
    public String getName() {
        return "zombie";
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String getSymbol() {
        return hp > 0 ? BlocksAndBoxes.BLOCK_ZOMBIE : "\u2620";
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        hp -= amount;
        return amount;
    }

    @Override
    public int getVisibility() {
        return 5;
    }

    @Override
    public void addFood(int f) {
        food += f;
    }
}
