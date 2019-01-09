package lab3.cell.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import lab3.cell.ICellAutomaton;

/**
 * 
 * A Component that draws the cells in a ICellAutomaton.
 * 
 * @author eivind
 */
class AutomatonComponent extends Component {
	/**
	 * The dimension of the grid containing the cells.
	 */
	private Dimension gridDim;
	/**
	 * The automaton to paint the cells of.
	 */
	private ICellAutomaton automaton;

	/**
	 * The height of each cell in pixels.
	 */
	private final int cellHeight = 5;

	/**
	 * The width of each cell in pixels.
	 */
	private final int cellWidth = 5;
	/**
	 * The size of the space between each cell and between the cell and the edge
	 * of the window.
	 */
	private final int padding = 1;
	private static final long serialVersionUID = 4548104480314044678L;

	/**
	 * Construct a AutomatonComponent that will paint the given automaton.
	 * 
	 * @param grid
	 */
	public AutomatonComponent(ICellAutomaton automaton) {
		this.automaton = automaton;
		gridDim = new Dimension((cellWidth + padding) * automaton.getWidth()
				+ padding, (cellHeight + padding) * automaton.getHeight() + padding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize() Returns the dimensions of the
	 * grid.
	 */
	@Override
	public Dimension getPreferredSize() {
		return gridDim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics) Paints the automaton.
	 */
	public void paint(Graphics g) {
		for (int x = 0; x < automaton.getWidth(); x++) {
		for (int y = 0; y < automaton.getHeight(); y++) {
				g.setColor(automaton.getColorInCurrentGeneration(x, y));
				g.fillRect(x * (cellHeight + padding) + padding, y
						* (cellHeight + padding) + padding, cellHeight,
						cellWidth);
			}
		}
	}
}
