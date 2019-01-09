package lab6.pond.tests;

import static org.junit.jupiter.api.Assertions.*;

import lab6.gfx.gfxmode.ITurtle;
import lab6.gfx.gfxmode.Point;
import lab6.gfx.gfxmode.TurtlePainter;
import lab6.util.IGenerator;
import lab6.util.generators.DoubleGenerator;
import org.junit.jupiter.api.Test;

import lab6.pond.Duck;
import lab6.pond.IPondObject;
import lab6.pond.Pond;

class DuckTest extends PondObjectProperties {
	
	protected IPondObject generate() {
		return new Duck();
	}
	
	@Test
	void constructorTest() {
		IGenerator<Double> gen = new DoubleGenerator(-100, 100);
		for(int i = 0; i < N; i++) {
			double x = gen.generate();
			double y = gen.generate();
			Duck d = new Duck(x, y);
			assertEquals(x, d.getX());
			assertEquals(y, d.getY());
		}
	}
	
	@Test
	void stepTest() {
		Pond p = new Pond();
		// kjedelig test for kjedelig oppførsel
		Duck d = new Duck(200, 200);

		p.add(d);
		// generelt vanskelig å teste, fordi i ikke nødvendigvis er
		// helt presise med hva den gjør
		d.step(p);

		// TODO: her må du evt. teste oppførselen du har implementert
		assertEquals(204, d.getX());
		assertEquals(200, d.getY());
	}

	@Test
	void drawTest() {
		// vi kan sjekke at tegningen blir det vi forventer, men det er kanskje
		// litt teit...

		// men: vi kan f.eks. kalle draw og sjekke at posisjonen til painter
		// (tegneskilpadden) ikke har endret seg

		// her trenger vi en "mock" turtle, som vi kan gjøre målinger på, men som ikke
		// faktisk trenger en skjerm å tegne på. Vi kan få tak i det ved å gi
		// skjerm-størrelsen til TurtlePainter, i stedet for å gi et Screen-objekt.
		// TurtlePainter er implementert slik at den da jobber uten å bruke
		// grafikk-motoren.
		//
		// vanlig i mange tilfeller der vi interagerer med kompliserte ting, slik
		// som jagerfly, aksjehandel, fysiske ting, brukere, andre store kompontenter,
		// ...
		ITurtle painter = new TurtlePainter(1280, 720);

		Duck d = new Duck(200, 200);
		Point pos = painter.getPos();
		double angle = painter.getAngle();
		d.draw(painter);
		assertEquals(pos, painter.getPos());
		assertEquals(angle, painter.getAngle());

	}

}
