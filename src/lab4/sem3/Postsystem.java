package lab4.sem3;

/**
* INF 100 HOESTEN 2016
* Semesteroppgave 3, Oppgave 4
* 
* System for registrering av postpakker.
*/

import java.util.Scanner;

public class Postsystem {
	public static void main(String[] args) {
	    Scanner tastatur = new Scanner(System.in);
		System.out.println("Hvor mange postpakker skal registreres?");
		int antallPakker = tastatur.nextInt();
		if(antallPakker < 1){
			System.out.println("Feil! Kan ikke registrere mindre enn 1 pakke.");
			tastatur.close();
			return;
		}
		tastatur.nextLine();
		Postpakke[] pakker = new Postpakke[antallPakker];
		for(int i = 0; i < antallPakker; i++){
			System.out.println("Pakke "+(i+1));
			System.out.println("Oppgi mottakerens navn:");
			String mNavn = tastatur.nextLine().trim();
			System.out.println("Oppgi mottakerens addresse:");
			String mAddr = tastatur.nextLine().trim();
			System.out.println("Oppgi mottakerens postnummer:");
			int mPN = tastatur.nextInt();
			System.out.println("Oppgi pakkens vekt:");
			double pVekt = tastatur.nextDouble();
			tastatur.nextLine();
			pakker[i] = new Postpakke(mNavn, mAddr, mPN, pVekt);
		}
		System.out.println("Pakker:");
		for(int i = 0; i < antallPakker; i++){
			System.out.println("Pakke "+(i+1));
			System.out.println("Mottakers navn: "+pakker[i].hentMottakerNavn());
			System.out.println("Mottakers addresse: "+pakker[i].hentMottakerAddresse());
			System.out.println("Mottakers postnummer: "+pakker[i].hentMottakerPostnummer());
			System.out.println("Pakkens vekt: "+pakker[i].hentVekt());
		}
		tastatur.close();
	}
}
