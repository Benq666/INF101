package lab6.pond.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lab6.pond.Frog;

class FrogTest {

	@Test
	void testConstructor() {
		Frog f = new Frog(200, 200);
		
		assertEquals(200, f.getX());
		assertEquals(200, f.getY());
		
		
		// best å teste med forskjellige verdier også
		f = new Frog(200, 300);
		
		assertEquals(200, f.getX());
		assertEquals(300, f.getY());
		
		// sjekk med litt varierte verdier
		f = new Frog(1200, 1300);		
		assertEquals(1200, f.getX());
		assertEquals(1300, f.getY());

		f = new Frog(0, 0);		
		assertEquals(0, f.getX());
		assertEquals(0, f.getY());

		// negative verdier er også ok her!
		f = new Frog(-120, -130);		
		assertEquals(-120, f.getX());
		assertEquals(-130, f.getY());

	}
}
