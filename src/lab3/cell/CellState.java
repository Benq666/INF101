package lab3.cell;

import java.util.Random;

/**
 * 
 * The State of a cell
 */
public class CellState {
	
	public static final CellState ALIVE = new CellState(2),
								 DYING = new CellState(1),
								 DEAD = new CellState(0);
	
	public static final CellState ANT = new CellState(-1);
	
	private static final CellState[] values = new CellState[]{ALIVE,DYING,DEAD};
	
	protected int data;
	
	public CellState(int data) {
		this.data = data;
	}
	
	public int getValue() {
		return data;
	}
	
	/**
	 * Returns one of the 'standard' values.
	 * @param rand
	 * @return
	 */
	public static CellState random(Random rand){
		return values[rand.nextInt(3)];
	}
}

