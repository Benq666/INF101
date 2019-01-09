package lab4.sem3;

/**
* INF 100 HOESTEN 2016
* Semesteroppgave 3, Oppgave 2
* 
* Laster labyrint fra en fil og lar spiller gaa i labyrinten og plukke opp gull.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LabyrintSkattejakt {
	public static boolean kanGaaTil(char[][] labyrint, int labyrintBredde, int labyrintHoeyde, int nyX, int nyY){
		if(nyX<0||nyY<0||nyX>=labyrintBredde||nyY>=labyrintHoeyde)
			return false;
		if(labyrint[nyY][nyX] == '*')
			return false;
		return true;
	}
	public static char[][] lesLabyrintFraFil(String bane){
	    File fil = new File(bane);
	    try{
		    Scanner in = new Scanner(fil);
		    // Les inn bredde
		    if(!in.hasNextInt()){
		    	System.out.println("Feil! Mangler bredde.");
		    	in.close();
		    	return null;
		    }
		    int labyrintBredde = in.nextInt();
		    if(labyrintBredde <= 0){
		    	System.out.println("Feil! Bredde maa vaere minst 1.");
		    	in.close();
		    	return null;
		    }
		    in.nextLine();
		    // Les inn hoeyde
		    if(!in.hasNextInt()){
		    	System.out.println("Feil! Mangler hoeyde.");
		    	in.close();
		    	return null;
		    }
		    int labyrintHoeyde = in.nextInt();
		    in.nextLine();
		    if(labyrintHoeyde <= 0){
		    	System.out.println("Feil! Hoeyde maa vaere minst 1.");
		    	in.close();
		    	return null;
		    }
		    // Les brettet
		    System.out.println(labyrintBredde);
		    System.out.println(labyrintHoeyde);
		    char[][] labyrint = new char[labyrintHoeyde][labyrintBredde];
		    for(int y = 0; y < labyrintHoeyde; y++){
		    	if(!in.hasNextLine()){
		    		System.out.println("Feil! Filen har ikke nok linjer.");
		    		in.close();
		    		return null;
		    	}
		    	String linje = in.nextLine();
		    	for(int x = 0; x < labyrintBredde; x++){
		    		if(x >= linje.length()){
			    		System.out.println("Feil! En linje er for kort.");
			    		in.close();
			    		return null;
		    		}
		    		labyrint[y][x] = linje.charAt(x);
		    	}
		    }
		    in.close();
		    return labyrint;
	    }catch(FileNotFoundException e){
	    	System.out.println("Feil! Filen kunne ikke åpnes.");
	    	return null;
	    }
	}
	public static void main(String[] args) {
	    Scanner tastatur = new Scanner(System.in);
		// Labyrint
		System.out.println("Oppgi filen som labyrinten skal lastes fra:");
		String labyrintBane = tastatur.nextLine().trim();
		char[][] labyrint = lesLabyrintFraFil(labyrintBane);
		if(labyrint == null){
			tastatur.close();
			return;
		}
		int labyrintBredde = labyrint[0].length;
		int labyrintHoeyde = labyrint.length;
		int labyrintGull = 0;
		// Finn spillerposisjon
		int spillerX = 0;
		int spillerY = 0;
		int spillerGull = 0;
		for(int y = 0; y < labyrintHoeyde; y++){
			for(int x = 0; x < labyrintBredde; x++){
				if(labyrint[y][x]=='s'){
					spillerX = x;
					spillerY = y;
					labyrint[y][x] = ' ';
				}else if(labyrint[y][x]=='g'){
					labyrintGull++;
				}
			}
		}
		// Gaa gjennom labyrint
	    while(true){
			// Skriv ut labyrint
	    	System.out.println("Spiller-gull: "+spillerGull);
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
	    	// Plukk opp gull
	    	if(labyrint[spillerY][spillerX] == 'g'){
	    		labyrint[spillerY][spillerX] = ' ';
	    		spillerGull++;
    			System.out.println("Du tok 1 gull!");
	    		if(spillerGull == labyrintGull){
	    			System.out.println("Du tok alt gullet i labyrinten!");
	    			break;
	    		}
	    	}
	    }
	    tastatur.close();
	    System.out.println("Spiller-gull: "+spillerGull);
	}
}
