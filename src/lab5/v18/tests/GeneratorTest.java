package lab5.v18.tests;

import static org.junit.jupiter.api.Assertions.*;

import lab5.v18.util.IGenerator;
import lab5.v18.util.generators.IntGenerator;
import lab5.v18.util.generators.MyGridGenerator;
import lab5.v18.util.generators.StringGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;


public class GeneratorTest {
	private final Random rng = new Random();

	@Test
	public void testIntGenerator() {
		for (int i = 0; i < 10000; i++) {
			generateEqualsProperty(new IntGenerator());
			generateEqualsProperty(new IntGenerator(i));
			generateEqualsProperty(new IntGenerator(-i, i+1));
			generateNotNullProperty(new IntGenerator());
			generateNotNullProperty(new IntGenerator(i));
			generateNotNullProperty(new IntGenerator(-i, i+1));
		}
	}

	@Test
	public void testStringGenerator() {
		for (int i = 0; i < 1000; i++) {
			generateEqualsProperty(new StringGenerator());
			generateEqualsProperty(new StringGenerator(i+1));
			generateNotNullProperty(new StringGenerator());
			generateNotNullProperty(new StringGenerator(i+1));
		}
		generateEqualsProperty(new StringGenerator(10, 1000));
	}

	@Test
	public void testMyGridGenerator() {
		for (int i = 0; i < 10000; i++) {
			generateEqualsProperty(new MyGridGenerator<>(new StringGenerator()));
			generateNotNullProperty(new MyGridGenerator<>(new StringGenerator()));
		}
	}

	/**
	 * Test that all elements in the collection returned by gen.generateEquals are actually equal.
	 * 
	 * @param gen A generator
	 */
	public <T> void generateEqualsProperty(IGenerator<T> gen) {
		List<T> elements = gen.generateEquals(rng, 10);

		T obj = elements.get(0);

		for (T o : elements)
			assertEquals(obj, o);
	}
	
	/**
	 * Test that the generator returns non-null objects
	 * 
	 * @param gen
	 */
	public <T> void generateNotNullProperty(IGenerator<T> gen) {
		assertNotNull(gen.generate());
	}
}
