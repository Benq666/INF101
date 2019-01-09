package lab5.v18.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A Grid contains a set of cells in a square 2D matrix.
 *
 */
public class MyGrid<T> implements IGrid<T> {
	private List<T> cells;
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
	public MyGrid(int width, int height, T initElement) {
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException();

		this.height = height;
		this.width = width;
		cells = new ArrayList<>(height * width);
		for (int i = 0; i < height * width; ++i) {
			cells.add(initElement);
		}
	}

	
	@Override
	public int getHeight() {
		return height;
		}


	@Override
	public int getWidth() {
		return width;
	}


	@Override
	public void set(int x, int y, T elem) {
		if(x < 0 || x >= width)
			throw new IndexOutOfBoundsException();
		if(y < 0 || y >= height)
			throw new IndexOutOfBoundsException();

		cells.set(x + (y * width), elem);
	}

	
	@Override
	public T get(int x, int y) {
		if(x < 0 || x >= width)
			throw new IndexOutOfBoundsException();
		if(y < 0 || y >= height)
			throw new IndexOutOfBoundsException();

		return cells.get(x + (y * width));
	}

	@Override
	public IGrid<T> copy() {
		MyGrid<T> newGrid = new MyGrid<>(getWidth(), getHeight(), null);

		for (int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				newGrid.set(x,  y,  get(x, y));
		return newGrid;
	}

}
