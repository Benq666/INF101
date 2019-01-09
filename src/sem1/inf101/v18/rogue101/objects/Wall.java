package sem1.inf101.v18.rogue101.objects;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.map.IGameMap;
import sem1.inf101.v18.rogue101.map.IMapView;

import java.util.List;

public class Wall implements IItem {
	private int hp = getMaxHealth();
	private String symbol = BlocksAndBoxes.BLOCK_FULL2;

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
		return 10;
	}

	@Override
	public int getMaxHealth() {
		return 1000;
	}

	@Override
	public String getName() {
		return "wall";
	}

	@Override
	public int getSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int handleDamage(IGame game, IItem source, int amount) {
		hp -= amount;
		return amount;
	}

	/**
	 * Set the symbol of the wall to determine it's graphical representation
	 *
	 * @param s String with the symbol code
	 */
	private void setSymbol(String s) {
		symbol = s;
	}

	/**
	 * Find the neighbours of the wall to determine the symbol for the {@link Wall#setSymbol(String)}
	 *
	 * @param game The game to interact with the map
	 * @param wall Current wall
	 */
	public void setupWall(IGame game, IItem wall) {
		IMapView map = game.getMap();
		ILocation loc = map.getLocation(wall);
		int x = loc.getX();
		int y = loc.getY();
		int width = map.getWidth();
		int height = map.getHeight();
		if (x - 1 >= 0 && x + 1 < width) {
			if (map.hasWall(map.getLocation(x - 1, y)) && map.hasWall(map.getLocation(x + 1, y))) {
				setSymbol(BlocksAndBoxes.BLOCK_LEFT_TO_RIGHT);
				if (y - 1 >= 0) {
					if (map.hasWall(map.getLocation(x, y - 1)))
						setSymbol(BlocksAndBoxes.BLOCK_LEFT_RIGHT_TOP2);
					return;
				}
				if (y + 1 < height) {
					if (map.hasWall(map.getLocation(x, y + 1)))
						setSymbol(BlocksAndBoxes.BLOCK_LEFT_RIGHT_BOTTOM2);
					return;
				}
			}
			else if (map.hasWall(map.getLocation(x - 1, y)))
				setSymbol(BlocksAndBoxes.BLOCK_BOTTOM_LEFT_TOP2);
			else if (map.hasWall(map.getLocation(x + 1, y)))
				setSymbol(BlocksAndBoxes.BLOCK_BOTTOM_RIGHT_TOP2);
		}
		if (y - 1 >= 0 && y + 1 < height) {
			if (map.hasWall(map.getLocation(x, y - 1)) && map.hasWall(map.getLocation(x, y + 1))) {
				setSymbol(BlocksAndBoxes.BLOCK_FULL);
				if (x - 1 >= 0) {
					if (map.hasWall(map.getLocation(x - 1, y)))
						setSymbol(BlocksAndBoxes.BLOCK_BOTTOM_LEFT_TOP);
					return;
				}
				if (x + 1 < width) {
					if (map.hasWall(map.getLocation(x + 1, y)))
						setSymbol(BlocksAndBoxes.BLOCK_BOTTOM_RIGHT_TOP);
					return;
				}
			}
			else if (map.hasWall(map.getLocation(x, y - 1)))
				setSymbol(BlocksAndBoxes.BLOCK_LEFT_RIGHT_TOP);
			else if (map.hasWall(map.getLocation(x, y + 1)))
				setSymbol(BlocksAndBoxes.BLOCK_LEFT_RIGHT_BOTTOM);
		}
		if (x + 1 < width && y + 1 < height) {
			if (map.hasWall(map.getLocation(x + 1, y)) && map.hasWall(map.getLocation(x, y + 1)))
				setSymbol(BlocksAndBoxes.BLOCK_REVERSE_BOTTOM_RIGHT);
		}
		if (x - 1 >= 0 && y + 1 < height) {
			if (map.hasWall(map.getLocation(x - 1, y)) && map.hasWall(map.getLocation(x, y + 1)))
				setSymbol(BlocksAndBoxes.BLOCK_REVERSE_BOTTOM_LEFT);
		}
		if (x + 1 < width && y - 1 >= 0) {
			if (map.hasWall(map.getLocation(x + 1, y)) && map.hasWall(map.getLocation(x, y - 1)))
				setSymbol(BlocksAndBoxes.BLOCK_REVERSE_TOP_RIGHT);
		}
		if (x - 1 >= 0 && y - 1 >= 0) {
			if (map.hasWall(map.getLocation(x - 1, y)) && map.hasWall(map.getLocation(x, y - 1)))
				setSymbol(BlocksAndBoxes.BLOCK_REVERSE_TOP_LEFT);
		}
	}
}
