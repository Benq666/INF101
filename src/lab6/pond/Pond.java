package lab6.pond;

import lab6.gfx.Screen;
import lab6.gfx.gfxmode.TurtlePainter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pond {
	private double x, y;
	private double width, height;

	/**
	 * All objects in the pond. 
	 */
	private List<IPondObject> objs = new ArrayList<>();

	public static Random random = new Random();

	public void step() {
		/*
		 * for (Object o : objs) { // vi må si at det er en and (eller frosk) for å få
		 * lov av kompilatoren til å // kalle // metoden – selv om vi vet at alle
		 * objektene våre har step-metoden if (o instanceof IPondObject) ((IPondObject)
		 * o).step(); }
		 * 
		 */
		for (IPondObject o : objs) {
			o.step(this);
		}
	}

	public void draw(TurtlePainter painter) {
		for (IPondObject o : objs) {
			o.draw(painter);
		}
	}
	public void add(IPondObject obj) {
		objs.add(obj);
	}

	public void setup(Screen screen) {
		// TODO: add some pond objects
		Duck duck = new Duck();
		add(duck);
		add(new Duckling(675,350, duck));
		add(new Duckling(675,450, duck));
		add(new Frog(50,150));
		add(new Frog(50,250));
		add(new Frog(50,350));
		add(new Frog(50,450));


		int n = 5;// 0+random.nextInt(100);
		for (int i = 0; i < n; i++) {
//			add(new Duck(random.nextInt((int) screen.getWidth()), random.nextInt((int) screen.getHeight())));
		}
		for (int i = 0; i < n; i++) {
//			add(new T());
			
			//new Frog(random.nextInt((int) screen.getWidth()), random.nextInt((int) screen.getHeight())));
		}
	}
}
