package lab4.labyrinth;

import lab4.datastructures.IGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LabyrinthTest {
	private ILabyrinth labyrinth;

	private IGrid<LabyrinthTile> testGrid() {
		return LabyrinthHelper.loadGrid(//
				new char[][]{ //
						{'*', '*', '*', '*'}, //
						{'*', ' ', ' ', '*'}, //
						{'*', ' ', '*', '*'}, //
						{'*', 's', '*', '*'}, //
						{'*', '*', '*', '*'},});
	}


	@BeforeEach
	public void setup() {
		labyrinth = new Labyrinth(testGrid());
	}

	@Test
	public void badMoveTestS() {
		assertFalse(labyrinth.playerCanGo(Direction.SOUTH));

		try {
			labyrinth.movePlayer(Direction.SOUTH);
			fail("Moving south should fail");
		} catch (MovePlayerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void badMoveTestW() {
		assertFalse(labyrinth.playerCanGo(Direction.WEST));

		try {
			labyrinth.movePlayer(Direction.WEST);
			fail("Moving west should fail");
		} catch (MovePlayerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void badMoveTestE() {
		assertFalse(labyrinth.playerCanGo(Direction.EAST));

		try {
			labyrinth.movePlayer(Direction.EAST);
			fail("Moving east should fail");
		} catch (MovePlayerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void goodMoveTestN() throws MovePlayerException {
		assertTrue(labyrinth.playerCanGo(Direction.NORTH));
		labyrinth.movePlayer(Direction.NORTH);
		assertEquals(LabyrinthTile.PLAYER, labyrinth.getCell(1, 2));
	}

	@Test
	public void goodMoveTestNNE() throws MovePlayerException {
		labyrinth.movePlayer(Direction.NORTH);
		labyrinth.movePlayer(Direction.NORTH);
		labyrinth.movePlayer(Direction.EAST);
		assertEquals(LabyrinthTile.PLAYER, labyrinth.getCell(2, 3));
	}

	@Test
	public void badGrid1() {
		try {
			new Labyrinth(LabyrinthHelper.loadGrid(new char[][]{ //
					{'*', '*', '*', '*'}, //
					{'*', ' ', 's', '*'}, //
					{'*', ' ', '*', '*'}, //
					{'*', 's', '*', '*'}, //
					{'*', '*', '*', '*'},}));
			fail("Should throw exception if board has more than one player");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void gridGet() {
		assertEquals(LabyrinthTile.WALL, labyrinth.getCell(0, 0));
		assertEquals(LabyrinthTile.WALL, labyrinth.getCell(2, 1));
		assertEquals(LabyrinthTile.WALL, labyrinth.getCell(3, 4));
		assertEquals(LabyrinthTile.PLAYER, labyrinth.getCell(1, 1));
		assertEquals(LabyrinthTile.OPEN, labyrinth.getCell(1, 2));
	}

	@Test
	public void badGrid2() {
		try {
			new Labyrinth(LabyrinthHelper.loadGrid(new char[][]{ //
					{'*', '*', '*', '*'}, //
					{'*', ' ', ' ', '*'}, //
					{'*', ' ', '*', '*'}, //
					{'*', ' ', '*', '*'}, //
					{'*', '*', '*', '*'},}));
			fail("Should throw exception if board has more than one player");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void goldHoarding() throws MovePlayerException {
		Labyrinth testlab = new Labyrinth(LabyrinthHelper.loadGrid(new char[][]{ //
				{'*', '*', '*', '*'}, //
				{'*', ' ', ' ', '*'}, //
				{'*', 'g', '*', '*'}, //
				{'*', 's', '*', '*'}, //
				{'*', '*', '*', '*'},}));

		testlab.movePlayer(Direction.NORTH);
		assertEquals(1, testlab.getPlayerGold());
	}


	@Test
	public void checkHP() throws MovePlayerException {
		Labyrinth testlab = new Labyrinth(LabyrinthHelper.loadGrid(new char[][]{ //
				{'*', '*', '*', '*'}, //
				{'*', ' ', ' ', '*'}, //
				{'*', 'm', '*', '*'}, //
				{'*', 's', '*', '*'}, //
				{'*', '*', '*', '*'},}));

		testlab.setHealth(100);
		testlab.movePlayer(Direction.NORTH);
		assertEquals(100 - testlab.howMuchDamageTaken(), testlab.getPlayerHitPoints());
	}
}
