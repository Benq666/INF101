package lab6.util.generators;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lab6.util.IGenerator;

public class LinkedListGenerator<T> extends AbstractGenerator<List<T>>
	implements IGenerator<List<T>> {
	/**
	 * Generator for the length of the list
	 */
	private final IGenerator<Integer> lengthGenerator;

	/**
	 * Generator for one element of a random grid
	 */
	private final IGenerator<T> elementGenerator;

	public LinkedListGenerator(IGenerator<T> elementGenerator) {
		this.elementGenerator = elementGenerator;
		this.lengthGenerator = new IntGenerator(0, 100);
	}

	public LinkedListGenerator(IGenerator<T> elementGenerator, int maxLength) {
		if (maxLength < 1) {
			throw new IllegalArgumentException("Length must be 1 or greater");
		}

		this.elementGenerator = elementGenerator;
		this.lengthGenerator = new IntGenerator(0, maxLength);
	}

	@Override
	public List<T> generate(Random r) {
		int l = lengthGenerator.generate(r);
		List<T> result = new LinkedList<>();

		for (int i = 0; i < l; i++) {
			result.add(elementGenerator.generate(r));
		}
		return result;
	}
}
