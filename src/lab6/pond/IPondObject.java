package lab6.pond;

import lab6.gfx.gfxmode.Direction;
import lab6.gfx.gfxmode.ITurtle;
import lab6.gfx.gfxmode.Point;

public interface IPondObject {
	/**
	 * Do one time step of actions for the object
	 */
	/* public */ /* abstract */ void step(Pond pond);

	/**
	 * Draw the object on the screen.
	 * 
	 * The object must position the drawing itself.
	 * 
	 * @param painter
	 *            A painter object used for the drawing
	 */
	void draw(ITurtle painter);

	/**
	 * @return Current health of object, with 1.0 being fully healthy and 0.0 being
	 *         dead.
	 */
	double getHealth();

	void setHealth(double hp);

	/**
	 * @return Current X-position (same as getPosition.getX())
	 */
	double getX();

	/**
	 * @return Current Y-position (same as getPosition.getY())
	 */
	double getY();

	/**
	 * @return Current position
	 */
	Point getPosition();

	/**
	 * @return Current direction
	 */
	Direction getDirection();

	double getWidth();

	double getHeight();
}
