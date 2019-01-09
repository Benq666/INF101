package lab6.util.generators;

import lab6.grid.IArea;
import lab6.grid.ILocation;

import java.util.Random;

public class LocationGenerator extends AbstractGenerator<ILocation> {
	private final IArea area;

	/**
	 * New LocationGenerator, will generate locations within the area
	 */
	public LocationGenerator(IArea area) {
		this.area = area;
	}

	@Override
	public ILocation generate(Random r) {
		return area.location(r.nextInt(area.getWidth()), r.nextInt(area.getHeight()));
	}

}
