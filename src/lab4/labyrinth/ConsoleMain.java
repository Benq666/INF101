package lab4.labyrinth;

import java.util.Scanner;

import lab4.datastructures.IGrid;
import lab4.datastructures.MyGrid;

public class ConsoleMain {
	// Labyrint
	public static char[][] testLabyrint = {//
			{ '*', '*', '*', '*' },//
			{ '*', ' ', ' ', '*' },//
			{ '*', ' ', '*', '*' },//
			{ '*', 's', '*', '*' },//
			{ '*', '*', '*', '*' }, };

	public static void main(String[] args) {
		Labyrinth labyrinth = new Labyrinth(LabyrinthHelper.loadGrid(testLabyrint));

		// Gaa gjennom labyrint
		Scanner tastatur = new Scanner(System.in);
		boolean going = true;
		while (going) {
			System.out.println(labyrinth);
			System.out.println("Hvor vil du gå ('nord', 'sør', 'vest' eller 'øst')?  Skriv 'avslutt' for å stoppe.");
			String valg = tastatur.nextLine().trim();
			Direction dir = null;
			if (valg.equals("nord")) {
				dir = Direction.NORTH;
			} else if (valg.equals("sør")) {
				dir = Direction.SOUTH;
			} else if (valg.equals("vest")) {
				dir = Direction.WEST;
			} else if (valg.equals("øst")) {
				dir = Direction.EAST;
			} else if (valg.equals("avslutt")) {
				going = false;
			} else {
				System.out.printf("Ugyldig valg: %s\n", valg);
				continue;
			}
			if (labyrinth.playerCanGo(dir)) {
				try {
					labyrinth.movePlayer(dir);
				} catch (Exception e) {
					System.out.println("Feil oppstod ved flytting av spiller!");
				}
			} else {
				System.out.println("Veien er blokkert!");
			}
		}
		tastatur.close();
	}
}
