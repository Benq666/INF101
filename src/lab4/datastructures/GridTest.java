package lab4.datastructures;

import java.util.Random;

import lab4.cell.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GridTest {
	Random random = new Random();

	@Test
	public void constructTest1() {
		IGrid grid = new MyGrid<CellState>(11, 17, CellState.DEAD);
		
		
		// TODO: sjekk at bredde og høyde faktisk er 11 og 17
		assertEquals(11, grid.getWidth());
		assertEquals(17, grid.getHeight());
	}
	
	/**
	 * Tests that trying to access outside of the dimensions of the grid throws
	 * an IndexOutOfBoundsException.
	 */
	@Test
	public void outOfBoundsTest() {
		IGrid<CellState> grid = new MyGrid<CellState>(10, 10, CellState.DEAD);

		try {
			grid.set(11, 0, CellState.DEAD);
			fail("Should throw exception");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		try {
			grid.set(0, 11, CellState.DEAD);
			fail("Should throw exception");
		} catch (IndexOutOfBoundsException e) {
			;
		}

		// check negative indexes
		try {
			grid.set(-1, 0, CellState.DEAD);
			fail("Should throw exception");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		try {
			grid.set(0, -1, CellState.DEAD);
			fail("Should throw exception");
		} catch (IndexOutOfBoundsException e) {
			;
		}
	}

	@Test
	public void setGetTest1() {
		IGrid<CellState> grid = new MyGrid<CellState>(100, 100, CellState.DEAD);

		for (int x = 0; x < 100; x++)
			for (int y = 0; y < 100; y++) {
				CellState cs = CellState.random(random);
				grid.set(x, y, cs);
				assertEquals(cs, grid.get(x, y));
			}
	}

	@Test
	public void setGetTest2() {
		IGrid<CellState> grid = new MyGrid<>(100, 100, CellState.DEAD);

		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				grid.set(x, y, CellState.random(random));
			}
		}

		for (int x = 0; x < 100; x++)
			for (int y = 0; y < 100; y++) {
				CellState cs = CellState.random(random);
				grid.set(x, y, cs);
				assertEquals(cs, grid.get(x, y));
			}
	}
	
	
	/*@Test
	public void setGetTest3() {
		
		IGrid grid = new MyGrid(0, 1, CellState.DEAD);
		IGrid grid2 = new MyGrid(1, 0, CellState.DEAD);
		
			grid.set(0, 1, CellState.DEAD);
			grid2.set(0, 1, CellState.DEAD);
			fail("gettest3: grid finnes ikke");
	
		
	}
*/
	@Test
	public void copyTest() {
		IGrid<CellState> grid = new MyGrid<>(100, 100, CellState.DEAD);

		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				CellState cs = CellState.random(random);
				grid.set(x, y, cs);
			}
		}

		IGrid newGrid = grid.copy();
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				assertEquals(grid.get(x, y), newGrid.get(x, y));
			}
		}
	}

	/**
	 * This test checks that the getHeight() returns the height MyGrid was constructed with.
	 */
	@Test
	public void heightTest() {
		int x = 100;
		int y = 100;
		IGrid<CellState> grid = new MyGrid<>(x, y, CellState.DEAD);

		assertEquals(grid.getHeight(), y);
	}

	/**
	 * This test checks that the getWidth() returns the width MyGrid was constructed with.
	 */
	@Test
	public void widthTest() {
		int x = 100;
		int y = 100;
		IGrid<CellState> grid = new MyGrid<>(x, y, CellState.DEAD);

		assertEquals(grid.getWidth(), x);
	}

	/**
	 * This test checks that if we're trying to create MyGrid with width/height = 0,
	 * we get an Runtime Exception.
	 */
	@Test
	public void RuntimeExceptionTest() {

		try {
			IGrid<CellState> grid = new MyGrid<>(0, 1, CellState.DEAD);
		} catch (RuntimeException e) {
			;
		}

		try {
			IGrid<CellState> grid = new MyGrid<>(1, 0, CellState.DEAD);
		} catch (RuntimeException e) {
			;
		}
	}
}
