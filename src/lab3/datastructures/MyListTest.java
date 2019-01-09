package lab3.datastructures;

import static org.junit.jupiter.api.Assertions.*;

import lab3.cell.CellState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class MyListTest {
	
	/*@BeforeAll
	public void setUp() throws Exception {
	}

	@AfterAll
	public void tearDown() throws Exception {
	}*/
	
	@Test
	public void setGetTest() {
		IList list = new MyList();
		Random rand = new Random();
		
		for(int i = 0; i < 1000; i++)
			list.add(CellState.random(rand));
		
		for(int i = 0; i < 1000; i++) {
			CellState element = CellState.random(rand);
			list.set(i, element);
			assertEquals(element, list.get(i));
		}
	}
}
