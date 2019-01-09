package lab3.datastructures;

import lab3.cell.CellState;

/**
 * 
 * A Grid contains a set of cells in a square 2D matrix.
 *
 */
public class MyGrid implements IGrid {
	private IList cells;
	private int height;
	private int width;

	/**
	 * 
	 * Construct a grid with the given dimensions.
	 * 
	 * @param width
	 * @param height
	 * @param initElement
	 *            What the cells should initially hold (possibly null)
	 */
	public MyGrid(int width, int height, CellState initElement) {
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException();

		this.height = height;
		this.width = width;
		cells = new MyList(height * width);
		for (int i = 0; i < height * width; ++i) {
			cells.add(initElement);
		}
	}

	
	@Override
	public int getHeight() {
		return height; // TODO: fyll inn
	}


	@Override
	public int getWidth() {
		return width; // TODO: fyll inn
	}


	@Override
	public void set(int x, int y, CellState elem) {
		if(x < 0 || x >= width)
			throw new IndexOutOfBoundsException();
		if(y < 0 || y >= height)
			throw new IndexOutOfBoundsException();

		// TODO: fyll inn
		int i = x + (width * y);
		cells.set(i, elem);
	}

	
	@Override
	public CellState get(int x, int y) {
		if(x < 0 || x >= width)
			throw new IndexOutOfBoundsException();
		if(y < 0 || y >= height)
			throw new IndexOutOfBoundsException();

		// TODO: fyll inn
		return cells.get((width * y) + x);
	}

	@Override
	public IGrid copy() {
		MyGrid newGrid = new MyGrid(getWidth(), getHeight(), null);

		for (int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				newGrid.set(x,  y,  get(x, y));
		return newGrid;
	}

}
