package sem1.inf101.v18.rogue101.examples;

import java.util.Collections;
import java.util.List;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.objects.IActor;
import sem1.inf101.v18.rogue101.objects.IItem;
import sem1.inf101.v18.rogue101.objects.INonPlayer;
import sem1.inf101.v18.rogue101.objects.IPlayer;

public class Rabbit implements INonPlayer {
	private int food = 0;
	private int hp = getMaxHealth();


	@Override
	public void doTurn(IGame game) {
        if (hp <= 0)
            return;
	    if (food > 0 && hp < getMaxHealth()) {
            hp++;
            food--;
        } else if (food > 0)
            food--;


        // looking for a target around the rabbit (only if the rabbit's hp is higher than certain value)
        if (getCurrentHealth() >= getMaxHealth() / 2) {
            for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
                for (IActor actor : (game.getMap().getActors(game.getLocation(dir)))) {
                    if (actor instanceof IPlayer) {
                        game.attack(dir, actor);
                        return;
                    }
                }
            }
        }

        // eating the carrot
        for (IItem item : game.getMap().getAll(game.getLocation())) {
            if (item instanceof Carrot && getCurrentHealth() < getMaxHealth() / 2) {
                //System.out.println("found carrot!");
                int eaten = item.handleDamage(game, this, 3);
                if (eaten > 0) {
                    //System.out.println("ate carrot worth " + eaten + "!");
                    food += eaten;
                    //game.getPrinter().printAt(1, Main.LINE_MSG3,"You hear a faint crunching (" +
                            //getName() + " eats " + item.getArticle() + " " + item.getName() + ")");
                    return;
                }
            }
        }

		// checking nearby cells for carrots (only if the rabbit's hp is lower than certain value)
        List<GridDirection> possibleMoves = game.getPossibleMoves();
        if (getCurrentHealth() < getMaxHealth() / 2) {
            for (GridDirection dir : possibleMoves) {
                List<IItem> items = game.getMap().getAll(game.getLocation(dir));
                for (IItem item : items) {
                    if (item instanceof Carrot) {
                        game.move(dir);
                        return;
                    }
                }
            }
        }

		// looking for a target in visible range (if hp is high - we search for player, otherwise - for carrot)
		List<ILocation> allVisibleNeighbours = game.getVisible();
		if (getCurrentHealth() >= (getMaxHealth() / 2)) {
            for (ILocation loc : allVisibleNeighbours) {
                for (IActor actor : game.getMap().getActors(loc)) {
                    if (actor instanceof IPlayer) {
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
        }

        if (getCurrentHealth() < (getMaxHealth() / 2)) {
            for (ILocation loc : allVisibleNeighbours) {
                for (IItem item : game.getMap().getItems(loc)) {
                    if (item instanceof Carrot) {
                        for (GridDirection dir : possibleMoves) {
                            if (game.getLocation(dir) == game.getLocation().gridLineTo(loc).get(0)) {
                                game.move(dir);
                                return;
                            }
                        }
                    }
                }
            }
        }

		// moving to one of the possible locations if no targets or carrots were found in visible range
		if (!possibleMoves.isEmpty()) {
        	Collections.shuffle(possibleMoves);
        	game.move(possibleMoves.get(0));
        	//game.getPrinter().clearLine(Main.LINE_MSG3);
        	//possibleMoves.clear();
        }
	}

	@Override
	public boolean draw(ITurtle painter, double w, double h) {
		return false;
	}

	@Override
	public int getAttack() {
		return 5;
	}

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public int getDefence() {
		return 1;
	}

	@Override
	public int getMaxHealth() {
		return 24;
	}

	@Override
	public String getName() {
		return "rabbit";
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public String getSymbol() {
		return hp > 0 ? BlocksAndBoxes.BLOCK_RABBIT : "¤";
	}

	@Override
	public int handleDamage(IGame game, IItem source, int amount) {
		hp -= amount;
		return amount;
	}

	@Override
	public int getVisibility() {
	    return 3;
    }

    @Override
    public void addFood(int f){
	    food += f;
    }
}
