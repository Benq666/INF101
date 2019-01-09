package lab3.cell;

import lab3.datastructures.IGrid;
import lab3.datastructures.MyGrid;

import java.awt.Color;
import java.util.Random;

/**
 * 
 * An ICellAutomata that implements the Seeds Cellular Automaton.
 * 
 * @see ICellAutomaton
 * 
 *      Every cell has two states: Alive or Dead. Each step the state of each
 *      cell is decided from its neighbors (diagonal, horizontal and lateral).
 *      If a dead cell has exactly two alive neighbors then it becomes alive,
 *      otherwise it dies.
 * 
 * @author eivind
 */
public class SeedsAutomaton implements ICellAutomaton {

	/**
	 * The grid containing the current generation.
	 */
	IGrid currentGeneration;

	/**
	 * 
	 * Construct a Seeds ICellAutomaton using a grid with the given height and
	 * width.
	 * 
	 * @param height
	 * @param width
	 */
	public SeedsAutomaton(int width, int height) {
		currentGeneration = new MyGrid(width, height,
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

		IGrid nextGeneration = new MyGrid(
				currentGeneration.getHeight(), currentGeneration.getWidth(),
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
						if (currentGeneration.get(x + dx, y + dy) == CellState.ALIVE) {
							numNeighbours++;
						}
					}
				}
				if (numNeighbours == 2) {
					nextGeneration.set(x, y, CellState.ALIVE);
				} else {
					nextGeneration.set(x, y, CellState.DEAD);
				}
			}
		}

		currentGeneration = nextGeneration;
	}
}