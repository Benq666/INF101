package lab5.v18.util.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lab5.v18.datastructures.IGrid;
import lab5.v18.datastructures.MyGrid;
import lab5.v18.util.IGenerator;

public class MyGridGenerator<T> implements IGenerator<IGrid<T>> {
	/**
	 * Generator for the width of a random grid
	 */
	private final IGenerator<Integer> widthGenerator;
	/**
	 * Generator for the height of a random grid
	 */
	private final IGenerator<Integer> heightGenerator;
	/**
	 * Generator for one element of a random grid
	 */
	private final IGenerator<T> elementGenerator;

	public MyGridGenerator(IGenerator<T> elementGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1,100); // bredde blir et tall fra 1 til 100
		this.heightGenerator = new IntGenerator(1, 100); // høyde blir et tall fra 1 til 100
	}	

	public MyGridGenerator(IGenerator<T> elementGenerator, int maxWidth, int maxHeight) {
		if(maxWidth < 1 || maxHeight < 1)
			throw new IllegalArgumentException("Width and height must be 1 or greater");
		
		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1, maxWidth);
		this.heightGenerator = new IntGenerator(1, maxHeight);
	}	

	public MyGridGenerator(IGenerator<T> elementGenerator, IGenerator<Integer> widthGenerator, IGenerator<Integer> heightGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = widthGenerator;
		this.heightGenerator = heightGenerator;
	}	

	@Override
	public IGrid<T> generate(Random r) {
		// TODO: fyll inn; bruk bredde/høyde-generatorer til å finne størrelsen på 
		// et nytt grid, opprett et MyGrid-objekt, og fyll det med elementer
		// fra elementgeneratoren (T element = elementGenerator.generate(r);)
		//
		// Viktig: sende med argumentet 'r' når du kaller generate på subgeneratorene
		// fra denne metoden.

		int width = widthGenerator.generate(r);
		int height = heightGenerator.generate(r);
		IGrid<T> generatedGrid = new MyGrid<>(width, height, elementGenerator.generate(r));

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				generatedGrid.set(i, j, elementGenerator.generate(r));
			}
		}

		return generatedGrid;
	}

	@Override
	public List<IGrid<T>> generateEquals(Random r, int n) {
		long seed = r.nextLong();
		IGrid<T> element = generate(new Random(seed));

		List<IGrid<T>> list = new ArrayList<>();
		for(int i = 0; i < n; i++)
			list.add(element);
		
		return list;
	}

	@Override
	public List<IGrid<T>> generateEquals(int n) {
		return generateEquals(IntGenerator.getRandom(), n); 
	}

	@Override
	public IGrid<T> generate() {
		return generate(IntGenerator.getRandom());
	}

}
