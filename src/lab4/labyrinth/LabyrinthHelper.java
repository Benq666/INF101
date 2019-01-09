package lab4.labyrinth;

import java.util.Random;

import lab4.datastructures.IGrid;
import lab4.datastructures.MyGrid;

public class LabyrinthHelper {

	public static IGrid<LabyrinthTile> loadGrid(char[][] source) {
		int height = source.length;
		int width = source[0].length;

		IGrid<LabyrinthTile> grid = new MyGrid<LabyrinthTile>(width, height, LabyrinthTile.OPEN);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; ++j) {
				if (source[j][i] == 's')
					grid.set(i, height-j-1, LabyrinthTile.PLAYER);
				else if (source[j][i] == '*')
					grid.set(i, height-j-1, LabyrinthTile.WALL);
				else if (source[j][i] == 'g')
					grid.set(i, height-j-1, LabyrinthTile.GOLD);
				else if (source[j][i] == 'm')
					grid.set(i, height-j-1, LabyrinthTile.MONSTER);
				else if (source[j][i] == ' ')
					grid.set(i, height-j-1, LabyrinthTile.OPEN);
				else
					throw new RuntimeException("Incorrect format in labyrinth!");
			}
		}

		return grid;
	}

	public static IGrid<LabyrinthTile> makeRandomGrid(int width, int height) {
		Random random = new Random();

		IGrid<LabyrinthTile> grid = new MyGrid<LabyrinthTile>(width, height, LabyrinthTile.OPEN);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; ++y) {
				int r = random.nextInt(20);
				LabyrinthTile tile = null;
				if (r < 15)
					tile = LabyrinthTile.OPEN;
				else if (r < 18)
					tile = LabyrinthTile.WALL;
				else if (r < 19)
					tile = LabyrinthTile.GOLD;
				else
					tile = LabyrinthTile.MONSTER;
				grid.set(x, y, tile);
			}
		}

		grid.set(random.nextInt(width), random.nextInt(height), LabyrinthTile.PLAYER);

		return grid;
	}
}
