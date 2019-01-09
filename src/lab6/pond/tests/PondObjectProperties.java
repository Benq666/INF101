package lab6.pond.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lab6.pond.IPondObject;
import lab6.pond.Pond;

public abstract class PondObjectProperties {
	public static int N = 10000;

	/**
	 * @return A new object of the type we're currently testing
	 */
	protected abstract IPondObject generate();
	
	@Test
	public void healthTest() {
		Pond p = new Pond();
		for(int i = 0; i < N; i++) {
			IPondObject obj = generate();
			stepHealthProperty(p, obj);
		}
	}
	
	public void stepHealthProperty(Pond p, IPondObject obj) {
		double h = obj.getHealth();
		obj.step(p);
		assertTrue(h > obj.getHealth(), "Health should decrease: " + h + " > " + obj.getHealth());		
	}
}
