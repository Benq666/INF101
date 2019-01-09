package lab3.datastructures;

import lab3.cell.CellState;

public interface IList {
	/**
	 * Legg til et element på slutten av listen.
	 * 
	 * @param s Elementet som skal legges til
	 * 
	 * Etterpå vil size() øke med én, og get(size()-1) vil returnere elementet.
	 */
	void add(CellState s);
	
	/**
	 * Fjern et element fra listen
	 * 
	 * @param i Indeksen til elementet
	 * @return elementet som ble fjernet
	 * 
	 * Etterpå vil alle senere indekser i listen flyttes én posisjon frem.
	 */
	CellState remove(int i);
	
	/**
	 * Returner elementet på posisjon i
	 * @param i
	 * @return
	 */
	CellState get(int i);
	
	/**
	 * @return True hvis listen er tom
	 */
	boolean isEmpty();
	
	/**
	 * @return Antall elementer i listen
	 */
	int size();
	
	/**
	 * Sett elementet på indeks i til s
	 * 
	 * @param i Index
	 * @param s Nytt element
	 * 
	 * Etterpå vil get(i) == s
	 */
	void set(int i, CellState s);

	/**
	 * Fjern alle elementer fra listen.
	 * 
	 * Etterpå vil size() == 0
	 */
	void clear();
}
