package lab4.labyrinth;

import java.awt.Color;

public enum LabyrinthTile {
	WALL, OPEN, PLAYER, MONSTER, GOLD;

	public Color getColor() {
		switch (this) {
		case OPEN:
			return Color.WHITE;
		case GOLD:
			return Color.YELLOW;
		case MONSTER:
			return Color.RED;
		case PLAYER:
			return Color.BLUE;
		case WALL:
			return Color.BLACK;
		}
		return Color.BLACK;
	}
}
