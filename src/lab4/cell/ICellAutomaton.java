package lab4.cell;

import java.awt.Color;

/**
 * 
 * An ICellAutomaton represents a Cellular Automaton. The 
 * automaton contains a cell generation organized in rows
 * and columns. Height and width refers to how many 
 * rows and columns there are in the Cellular Automaton
 * respectively.
 * @author eivind
 */
public interface ICellAutomaton {

	/**
	 * 
	 * Get the color of the cell in a given x,y location.
	 * @param x
	 * @param y
	 * @return The color of the cell in the given row and column.
	 */
	Color getColorInCurrentGeneration(int x, int y);
	/**
	 * The ICellAutomaton gives the cells their initial value.
	 * For instance the cellular automaton might give the cells
	 * a random state.
	 */
	void initializeGeneration();
	/**
	 * Progresses the state of the cell to the next generation.
	 */
	void stepAutomaton();
	
	/**
	 * @return The number of rows.
	 */
	int getHeight();
	/**
	 * @return The number of columns.
	 */
	int getWidth();

}
