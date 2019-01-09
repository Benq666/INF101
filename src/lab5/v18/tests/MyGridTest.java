package lab5.v18.tests;

import static org.junit.jupiter.api.Assertions.*;

import lab5.v18.util.IGenerator;
import lab5.v18.util.generators.IntGenerator;
import lab5.v18.util.generators.MyGridGenerator;
import lab5.v18.util.generators.StringGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lab5.v18.datastructures.IGrid;

import java.util.List;


public class MyGridTest {
	private static final int N = 1000000;

	private IGenerator<String> strGen = new StringGenerator();
	private IGenerator<IGrid<String>> gridGen = new MyGridGenerator<>(strGen);
	private IGenerator<Integer> intGen = new IntGenerator(2, 100);

	@BeforeAll
	public static void setUp() throws Exception {
	}

	@AfterAll
	public static void tearDown() throws Exception {
	}

	/**
	 * Test that get gives back the same value after set.
	 */
	@Test
	public void setGetTest() {
		IGrid<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x = wGen.generate();
			int y = hGen.generate();
			String s = strGen.generate();
			
			setGetProperty(grid, x, y, s);
		}
	}

	/**
	 * Test that setting an element to (x1,y1) doesn't affect another element at a different position (x2,y2)
	 */
	@Test
	public void setGetIndependentTest() {
		IGrid<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x1 = wGen.generate();
			int y1 = hGen.generate();
			int x2 = wGen.generate();
			int y2 = hGen.generate();

			if (grid.getHeight() * grid.getWidth() > 1
					&& x2 == x1 && y2 == y1) {
				while (x2 == x1 && y2 == y1) {
					x2 = wGen.generate();
					if (x2 == x1) {
						y2 = hGen.generate();
					}
				}
			}

			String s = strGen.generate();
			setGetIndependentProperty(grid, x1, y1, x2, y2, s);
		}
	}

	/**
	 * Test that the size of a random grid is less or equal to the specified size
	 */
	@Test
	public void sizeTest() {
		for (int i = 0; i < N/10; i++) {
			int width = intGen.generate();
			int height = intGen.generate();
			IGenerator<IGrid<String>> gridGen = new MyGridGenerator<>(strGen, width, height);
			IGrid<String> grid = gridGen.generate();
			assertTrue(grid.getWidth() <= width && grid.getHeight() <= height);
		}
	}

	@Test
	public void equalsTest() {
		IGrid<String> grid = gridGen.generate();
		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N/100; i++) {
			int x = wGen.generate();
			int y = hGen.generate();
			assertEquals(grid.get(x, y), grid.get(x, y));

			List<IGrid<String>> gridList = gridGen.generateEquals(3);

			assertEquals(gridList.get(0), gridList.get(1)); // refleksivitet

			if (gridList.get(0).equals(gridList.get(1)))
				assertEquals(gridList.get(1), gridList.get(0)); // symmetri

			if (gridList.get(0).equals(gridList.get(1)) && gridList.get(1).equals(gridList.get(2)))
				assertEquals(gridList.get(0), gridList.get(2)); // transitivitet
		}
	}

	@Test
	public void hashTest() {
		IGrid<String> grid = gridGen.generate();
		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N/100; i++) {
			int x = wGen.generate();
			int y = hGen.generate();
			assertEquals(grid.get(x, y).hashCode(), grid.get(x, y).hashCode());

			List<IGrid<String>> gridList = gridGen.generateEquals(3);

			assertEquals(gridList.get(0).hashCode(), gridList.get(1).hashCode());

			if (gridList.get(0).equals(gridList.get(1)))
				assertEquals(gridList.get(1).hashCode(), gridList.get(0).hashCode());

			if (gridList.get(0).equals(gridList.get(1)) && gridList.get(1).equals(gridList.get(2)))
				assertEquals(gridList.get(0).hashCode(), gridList.get(2).hashCode());
		}
	}

	/**
	 * get(x,y) is val after set(x, y, val)
	 */
	private <T> void setGetProperty(IGrid<T> grid, int x, int y, T val) {
		grid.set(x, y, val);
		assertEquals(grid.get(x, y), val);
	}

	/**
	 * A set on (x1,y1) doesn't affect a get on a different (x2,y2)
	 */
	private <T> void setGetIndependentProperty(IGrid<T> grid, int x1,
			int y1, int x2, int y2, T val) {
		if (x1 != x2 && y1 != y2) {
			T s = grid.get(x2, y2);
			grid.set(x1, y1, val);
			assertEquals(s, grid.get(x2, y2));
		}
	}
}
