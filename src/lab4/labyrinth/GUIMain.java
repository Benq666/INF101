package lab4.labyrinth;

import lab4.labyrinth.gui.LabyrinthGUI;

public class GUIMain {
	// Labyrint
	public static char[][] testLabyrint = {//
			{ '*', '*', '*', '*' },//
			{ '*', ' ', ' ', '*' },//
			{ '*', ' ', '*', '*' },//
			{ '*', 's', '*', '*' },//
			{ '*', '*', '*', '*' }, };

	public static void main(String[] args) {
			//LabyrinthGUI.run(() -> new Labyrinth(LabyrinthHelper.loadGrid(testLabyrint)));
			LabyrinthGUI.run(() -> new Labyrinth(LabyrinthHelper.makeRandomGrid(20, 20)));
	}
}
