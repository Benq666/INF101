package lab2.cell;

import java.util.Random;

/**
 * 
 * The State of a cell
 */
public enum CellState {
	ALIVE,
	DYING,
	DEAD;
	
	public static CellState random(Random rand){
		return CellState.values()[rand.nextInt(3)];
	}
}

