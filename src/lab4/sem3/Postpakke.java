package lab4.sem3;

/**
* INF 100 HOESTEN 2016
* Semesteroppgave 3, Oppgave 4
* 
* Klasse som representerer en postpakke.
*/

public class Postpakke {
	private String mottakerNavn;
	private String mottakerAddresse;
	private int mottakerPostnummer;
	private double vekt;
	Postpakke(String mNavn, String mAddr, int mPostnummer, double v){
		mottakerNavn = mNavn;
		mottakerAddresse = mAddr;
		mottakerPostnummer = mPostnummer;
		vekt = v;
	}
	public String hentMottakerNavn(){
		return mottakerNavn;
	}
	public String hentMottakerAddresse(){
		return mottakerAddresse;
	}
	public int hentMottakerPostnummer(){
		return mottakerPostnummer;
	}
	public double hentVekt(){
		return vekt;
	}
}
