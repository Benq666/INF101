package lab3.cell;

import lab3.datastructures.IGrid;
import lab3.datastructures.MyGrid;

import java.awt.Color;


public class LangtonsAnt implements ICellAutomaton {
	
	/**
	 * The maximum length of any valid rule. Should not exceed 256, 
	 * otherwise not all colors will be distinct.
	 */
	public static final int MAX_RULE_LENGTH = 32;
	
	/**
	 * Stores the rule, in the following sense: Upon reading a state whose value is i, 
	 * char[i] indicates whether to turn left ('L') or right ('R')
	 */
	protected char[] rule;
	
	protected IGrid currentGeneration;
	
	protected Ant ant;
	
	/**
	 * The state the ant saw upon moving to the field it is placed on 
	 * currently. Determines the next move (using the given rule).
	 */
	protected CellState seenState;
	
	public LangtonsAnt(int width, int height, String rule) {
		this.currentGeneration = new MyGrid(width, height, null);
		checkRule(rule);
		this.rule = rule.toCharArray();
	}
	
	/**
	 * Checks the rule for validity.
	 * A string is not a rule its length exceeds the maximum length ({@link #MAX_RULE_LENGTH}) 
	 * or if it contains characters other than 'L' and 'R'.
	 * 
	 * @param rule
	 */
	private void checkRule(String rule) {
		// TODO check if the length of the rule is within MAX_RULE_LENGTH and
		// 	throw an exception otherwise.
        if (rule.length() > MAX_RULE_LENGTH) {
            throw new IndexOutOfBoundsException();
        }
		
		// TODO check if the string rule only consists of the characters 
		// 'L' and 'R' and throw an exception otherwise.
        if(!rule.matches("[LR]+")) {
            throw new IllegalArgumentException();
        }
	}

	@Override
	public Color getColorInCurrentGeneration(int x, int y) {
		// This method returns different shades of the same color.
		// The scaling depends on the length of the given rule.
		CellState state = currentGeneration.get(x, y);
		if (state == CellState.ANT) return Color.yellow;
		int val = (int) (state.getValue()*256/rule.length);
		return new Color(255, 255-val, 255-val);
	}

	@Override
	public void initializeGeneration() {
		// TODO Set all fields to be in state 0.
		for (int x = 0; x < currentGeneration.getWidth(); x++) {
			for (int y = 0; y < currentGeneration.getHeight(); y++) {
				currentGeneration.set(x, y, new CellState(0));
			}
		}

		// TODO Initialize the ant and place it in the middle of the grid.
		ant = new Ant(currentGeneration.getWidth() / 2, currentGeneration.getHeight() / 2,
				Direction.NORTH);

		// TODO Initialize the seenState field (to state 0).
		seenState = new CellState(0);
	}

	@Override
	public void stepAutomaton() {
		
		// Retrieve the color of the cell the ant is on using the seenState field.
		int color = seenState.getValue();
		
		// Initialize the next position of the ant by copying the ant field.
		// Make sure that all operations concerning the *next* position of the
		// ant in the grid are performed on this object.
		Ant nextAnt = ant.copy();
		
		if (rule[color] == 'L') {
			// TODO turn left
			nextAnt.turnLeft();
		}
		if (rule[color] == 'R') {
			// TODO turn right
			nextAnt.turnRight();
		}

		// TODO Check whether the new coordinates are within the grid, otherwise 
		// 	reset them in some way of your choosing. (Note that this will greatly
		//  influence the patterns being drawn by the ant.)
		if (nextAnt.getX() >= currentGeneration.getWidth()) {
		    nextAnt.setX(ant.getX() - 2);
		    seenState = currentGeneration.get(nextAnt.getX(), nextAnt.getY());
        }
        if (nextAnt.getX() < 0) {
			nextAnt.setX(ant.getX() + 2);
			seenState = currentGeneration.get(nextAnt.getX(), nextAnt.getY());
		}
        if (nextAnt.getY() >= currentGeneration.getHeight()) {
			nextAnt.setY(ant.getY() - 2);
			seenState = currentGeneration.get(nextAnt.getX(), nextAnt.getY());
        }
        if (nextAnt.getY() < 0) {
			nextAnt.setY(ant.getY() + 2);
			seenState = currentGeneration.get(nextAnt.getX(), nextAnt.getY());
		}
		
		// TODO Update the state of the filed the ant is leaving.
		currentGeneration.set(ant.getX(), ant.getY(),
                new CellState((seenState.getValue() + 1) % rule.length));
		
		// TODO Update the seenState field, i.e. the state the ant is reading in 
		//	the field it is moving to.
		CellState nextColor = currentGeneration.get(nextAnt.getX(), nextAnt.getY());
		seenState = nextColor;

		// TODO Move the ant to the new position in the grid.
		ant.setX(nextAnt.getX());
		ant.setY(nextAnt.getY());

		// TODO Update the ant field variable.
		ant = nextAnt;
	}

	@Override
	public int getHeight() {
		return this.currentGeneration.getHeight();
	}

	@Override
	public int getWidth() {
		return this.currentGeneration.getWidth();
	}

}
