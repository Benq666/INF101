package lab4.cell;

import java.awt.Color;
import java.util.Random;

import lab4.datastructures.IGrid;
import lab4.datastructures.MyGrid;

/**
 * 
 * An ICellAutomata that implements Conways game of life.
 * 
 * @see ICellAutomaton
 * 
 *      Every cell has two states: Alive or Dead. Each step the state of each
 *      cell is decided from its neighbors (diagonal, horizontal and lateral).
 *      If the cell has less than two alive Neighbors or more than three
 *      neighbors the cell dies. If a dead cell has exactly three neighbors it
 *      will become alive.
 * 
 * @author eivind
 */
public class GameOfLife implements ICellAutomaton {

	/**
	 * The grid the game is played in.
	 */
	IGrid<CellState> currentGeneration;

	/**
	 * 
	 * Construct a GameOfLife ICellAutomaton using a grid with the given height
	 * and width.
	 * 
	 * @param height
	 * @param width
	 */
	public GameOfLife(int width, int height) {
		currentGeneration = new MyGrid<CellState>(width, height,
				CellState.DEAD);
	}

	@Override
	public void initializeGeneration() {
		Random random = new Random();
		for (int x = 0; x < currentGeneration.getWidth(); x++) {
			for (int y = 0; y < currentGeneration.getHeight(); y++) {
				if (random.nextBoolean()) {
					currentGeneration.set(x, y, CellState.ALIVE);
				} else {
					currentGeneration.set(x, y, CellState.DEAD);
				}
			}
		}
	}

	@Override
	public int getHeight() {
		return currentGeneration.getHeight();
	}

	@Override
	public int getWidth() {
		return currentGeneration.getWidth();
	}

	@Override
	public Color getColorInCurrentGeneration(int x, int y) {
		if (currentGeneration.get(x, y) == CellState.ALIVE) {
			return Color.black;
		} else {
			return Color.white;
		}
	}

	@Override
	public void stepAutomaton() {

		IGrid<CellState> nextGeneration = new MyGrid<CellState>(
				currentGeneration.getWidth(), currentGeneration.getHeight(),
				CellState.ALIVE);

		for (int x = 0; x < currentGeneration.getWidth(); x++) {
			for (int y = 0; y < currentGeneration.getHeight(); y++) {
				int numNeighbours = 0;
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx == 0 && dy == 0)
							continue; // samme celle, hopp over
						if (y + dy < 0)
							continue; // utenfor brettet
						if (y + dy >= currentGeneration.getHeight())
							continue; // utenfor brettet
						if (x + dx < 0)
							continue; // utenfor brettet
						if (x + dx >= currentGeneration.getWidth())
							continue; // utenfor brettet
						
						// tell levende naboer
						if (currentGeneration.get(x + dx, y + dy) == CellState.ALIVE) {
							numNeighbours++;
						}
					}
				}
				if (numNeighbours < 2) {
					nextGeneration.set(x, y, CellState.DEAD);
				} else if (numNeighbours == 3) {
					nextGeneration.set(x, y, CellState.ALIVE);
				} else if (numNeighbours > 3) {
					nextGeneration.set(x, y, CellState.DEAD);
				} else {
					nextGeneration.set(x, y, currentGeneration.get(x, y));
				}
			}
		}

		currentGeneration = nextGeneration;

	}
}
