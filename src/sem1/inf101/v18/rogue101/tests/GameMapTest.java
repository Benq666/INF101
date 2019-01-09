package sem1.inf101.v18.rogue101.tests;

import sem1.inf101.v18.rogue101.examples.Carrot;
import sem1.inf101.v18.rogue101.examples.GreenMushroom;
import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.objects.Dust;
import sem1.inf101.v18.rogue101.objects.IItem;
import sem1.inf101.v18.rogue101.player.Player;
import org.junit.jupiter.api.Test;

import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.map.GameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameMapTest {

	@Test
	void testSortedAdd() {
		GameMap gameMap = new GameMap(20, 20);
		ILocation location = gameMap.getLocation(10, 10);
		gameMap.add(location, new Carrot());
		gameMap.add(location, new Dust());
		gameMap.add(location, new Rabbit());
		gameMap.add(location, new Dust());
		gameMap.add(location, new Carrot());
		gameMap.add(location, new GreenMushroom());
		List<IItem> list= gameMap.getAll(location);
		for (int i = 0; i < list.size() - 1; i++) {
			assert (list.get(i).compareTo(list.get(i + 1)) >= 0);
		}
		if (list.size() > 0) {
			System.out.println("Sorting test results:");
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					System.out.printf("%d. %s (size: %d).%n",
							i + 1, list.get(i).getName().toLowerCase(), list.get(i).getSize());
				} else {
					System.out.printf("%d. %s (size: %d);%n",
							i + 1, list.get(i).getName().toLowerCase(), list.get(i).getSize());
				}
			}
		} else {
			System.out.println("The list is empty!");
		}
	}

	@Test
	void getNeighbourhood() {
		GameMap gameMap = new GameMap(4, 7);
		int posX = gameMap.getWidth() / 2;
		int posY = gameMap.getHeight() / 2;
		System.out.printf("Neighbourhood test:%nGame map is %d by %d = %d, position at (%d, %d)%n",
				gameMap.getWidth(), gameMap.getHeight(), gameMap.getWidth() * gameMap.getHeight(),
				posX, posY);
		ILocation location = gameMap.getLocation(posX, posY);
		List<ILocation> neighbours;

		int dist = 1;
		neighbours = gameMap.getNeighbourhood(location, dist);
		assertEquals(8, neighbours.size());
		for (int i = 0; i < neighbours.size(); i++) {
			assert (location.gridDistanceTo(neighbours.get(i)) <= 1);
			for (int j = i + 1; j < neighbours.size() - 1; j++) {
				assert (location.gridDistanceTo(neighbours.get(j)) >= location.gridDistanceTo(neighbours.get(i)));
			}
		}
		System.out.printf("dist = %d. Total neighbours: %d%n",
				dist, neighbours.size());

		dist = 2;
		neighbours = gameMap.getNeighbourhood(location, dist);
		assertEquals(19, neighbours.size());
		for (int i = 0; i < neighbours.size(); i++) {
			assert (location.gridDistanceTo(neighbours.get(i)) <= 2);
			for (int j = i + 1; j < neighbours.size() - 1; j++) {
				assert (location.gridDistanceTo(neighbours.get(j)) >= location.gridDistanceTo(neighbours.get(i)));
			}
		}
		System.out.printf("dist = %d. Total neighbours: %d%n",
				dist, neighbours.size());

		dist = 3;
		neighbours = gameMap.getNeighbourhood(location, dist);
		assertEquals(27, neighbours.size());
		for (int i = 0; i < neighbours.size(); i++) {
			assert (location.gridDistanceTo(neighbours.get(i)) <= 3);
			for (int j = i + 1; j < neighbours.size() - 1; j++) {
				assert (location.gridDistanceTo(neighbours.get(j)) >= location.gridDistanceTo(neighbours.get(i)));
			}
		}
		System.out.printf("dist = %d. Total neighbours: %d%n",
				dist, neighbours.size());
	}

	@Test
	void testAddContains1() {
		// test with rabbit first then carrot
		GameMap gameMap = new GameMap(20, 20);
		ILocation location = gameMap.getLocation(10, 10);
		addContainsProperty(gameMap, location, new Rabbit());
		addContainsProperty(gameMap, location, new Carrot());
	}

	@Test
	void testAddContains2() {
		// test with carrot first then rabbit
		GameMap gameMap = new GameMap(20, 20);
		ILocation location = gameMap.getLocation(10, 10);
		addContainsProperty(gameMap, location, new Carrot());
		addContainsProperty(gameMap, location, new Rabbit());
	}

	/**
	 * After adding an item, that location in the map should contain the item.
	 *
	 * @param map
	 * @param loc
	 * @param item
	 */
	void addContainsProperty(GameMap map, ILocation loc, IItem item) {
		map.add(loc, item);
		assertTrue(map.getAll(loc).contains(item));
	}

	@Test
	void testAddAddsOne() {
		GameMap gameMap = new GameMap(20, 20);
		// add stuff at various locations, see that size of item list increases by one

		// (Dette kan gå galt både ved at tingen ikke ble lagt til, og ved at den
		// blir lagt til flere ganger, og oppførselen kan avhenge av hva som er på stedet fra
		// så vi bør prøve med mange varianter.)

		ILocation location = gameMap.getLocation(10, 10);
		addSizeProperty(gameMap, location, new Rabbit());

		location = gameMap.getLocation(8, 10);
		gameMap.add(location, new Player()/* erstatt med noe som er større enn rabbit */);
		addSizeProperty(gameMap, location, new Rabbit());

		location = gameMap.getLocation(9, 10);
		gameMap.add(location, new GreenMushroom() /* mindre enn rabbit */);
		addSizeProperty(gameMap, location, new Rabbit());
	}

	/**
	 * After adding an item to the map, the list of items on that location should
	 * increase by one.
	 *
	 * @param map
	 * @param loc
	 * @param item
	 */
	void addSizeProperty(GameMap map, ILocation loc, IItem item) {
		int size = map.getAll(loc).size();
		map.add(loc, item);
		assertEquals(size + 1, map.getAll(loc).size());
	}
}