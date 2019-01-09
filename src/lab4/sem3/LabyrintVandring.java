package lab4.sem3;

/**
* INF 100 HOESTEN 2016
* Semesteroppgave 3, Oppgave 1
* 
* Lar spiller gaa i en labyrint.
*/

import java.util.Scanner;

public class LabyrintVandring {
	public static boolean kanGaaTil(char[][] labyrint, int labyrintBredde, int labyrintHoeyde, int nyX, int nyY){
		if(nyX<0||nyY<0||nyX>=labyrintBredde||nyY>=labyrintHoeyde)
			return false;
		if(labyrint[nyY][nyX] == '*')
			return false;
		return true;
	}
	public static void main(String[] args) {
		// Labyrint
		int labyrintBredde = 4;
		int labyrintHoeyde = 5;
		char[][] labyrint = {
				{ '*', '*', '*', '*' },
				{ '*', ' ', ' ', '*' },
				{ '*', ' ', '*', '*' },
				{ '*', 's', '*', '*' },
				{ '*', '*', '*', '*' },
		};
		// Finn spillerposisjon
		int spillerX = 0;
		int spillerY = 0;
		for(int y = 0; y < labyrintHoeyde; y++){
			for(int x = 0; x < labyrintBredde; x++){
				if(labyrint[y][x]=='s'){
					spillerX = x;
					spillerY = y;
					labyrint[y][x] = ' ';
				}
			}
		}
		// Gaa gjennom labyrint
	    Scanner tastatur = new Scanner(System.in);
	    while(true){
			// Skriv ut labyrint
			System.out.println("Labyrint:");
			for(int y = 0; y < labyrintHoeyde; y++){
				for(int x = 0; x < labyrintBredde; x++){
					if(x == spillerX && y == spillerY)
						System.out.printf("s");
					else
						System.out.printf("%c",labyrint[y][x]);
				}
				System.out.printf("\n");
			}
	    	// Gaa
	    	System.out.println("Hvor vil du gaa? Skriv nord, soer, vest eller oest for aa gaa i respektiv retning, eller avslutt for aa avslutte.");
	    	String valg = tastatur.nextLine().trim();
	    	int nyX = spillerX;
	    	int nyY = spillerY;
	    	if(valg.equals("nord")){
	    		nyY--;
	    	}else if(valg.equals("soer")){
	    		nyY++;
	    	}else if(valg.equals("vest")){
	    		nyX--;
	    	}else if(valg.equals("oest")){
	    		nyX++;
	    	}else if(valg.equals("avslutt")){
		    	break;
	    	}else{
	    		System.out.printf("Ugyldig valg: %s\n",valg);
	    		continue;
	    	}
	    	if(kanGaaTil(labyrint, labyrintBredde, labyrintHoeyde, nyX, nyY)){
		    	spillerX = nyX;
		    	spillerY = nyY;
	    	}else{
	    		System.out.println("Veien er blokkert!");
	    		continue;
	    	}
	    }
	    tastatur.close();
	}
}
