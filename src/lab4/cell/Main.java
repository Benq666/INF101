package lab4.cell;

import lab4.cell.gui.CellAutomataGUI;

public class Main {

	public static void main(String[] args) {

		//ICellAutomaton ca = new GameOfLife(100,100);
		//Når du er klar til å teste ut BriansBrain
		//kan du bytte linjen over med det følgende:
		ICellAutomaton LangstonsAnt = new LangtonsAnt(10,10, "LR");

		CellAutomataGUI.run(LangstonsAnt);
		//TEST for LAB3
	}

}
