package lab3.datastructures;

import lab3.cell.CellState;

import java.util.Arrays;

public class MyList implements IList {
	private int length;
	private CellState[] data;

	public MyList() {
		length = 0;
		data = new CellState[10];
	}

	public MyList(int initialSize) {
		if(initialSize < 0)
			throw new IllegalArgumentException("initialSize must be positive: " + initialSize);
		length = 0;
		data =  new CellState[initialSize];
	}

	@Override
	public void add(CellState s) {
		if (length == data.length) {
			data = Arrays.copyOf(data, data.length * 2);
		}

		data[length] = s;
		length = length + 1;
	}

	@Override
	public CellState remove(int i) {
		CellState element = data[i];

		for (int x = i; x < length - 1; x++) {
			data[x] = data[x + 1];
		}

		length = length - 1;
		return element;
	}

	@Override
	public CellState get(int i) {
		return data[i];
	}

	@Override
	public boolean isEmpty() {
		return length == 0;
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public void set(int i, CellState s) {
		data[i] = s;
	}

	@Override
	public void clear() {
		length = 0;
	}

}
