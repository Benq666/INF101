package lab6.pond;

import javafx.scene.paint.Color;
import lab6.gfx.gfxmode.Direction;
import lab6.gfx.gfxmode.ITurtle;
import lab6.gfx.gfxmode.Point;

/**
 * A duck class – recipe for making duck objects.
 * 
 * Describes the common aspects of and behaviour of duck objects (in methods)
 * and the individual differences between ducks (in field variables).
 */
public class Duck extends PondDweller implements IPondObject {
	// Dette er datastrukturen (sett av feltvariabler) som lagrer tilstanden til
	// andeobjektene:
	// "private" betyr at vi ønsker at dette skal være skjult, og at feltvariablene
	// ikke kan
	// endres eller leses av objekter (av andre klasser)

	// Her er det ingen feltvariabler igjen, siden alle har blitt flyttet til
	// superklassen.

	public Duck() {
		this(750, 400);
	}

	protected Color getPrimaryColor() {
		return Color.SADDLEBROWN;
	}

	public Duck(double x, double y) {
		this.pos = pos.move(x, y);
	}

	/**
	 * @param painter
	 */
	public void draw(ITurtle painter) {
		// first, save painter state, so we don't confuse the next duck
		painter.save();
		// go to our current coordinates
		painter.jumpTo(pos.getX(), pos.getY());
		// draw an ellipse, filled with bodyColor
		painter.shape().ellipse().width(getWidth()).height(getHeight()).fillPaint(bodyColor).fill(); // .draw();
		// jump to a position 50*size away at 30° angle, for drawing the head
		painter.turn(30);
		painter.jump(getWidth() / 2);
		// turn back so the head will be straight
		painter.turn(-30);
		// draw head as ellipse with headColor
		painter.shape().ellipse().width(getHeight()).height(0.6 * getHeight()).fillPaint(headColor).fill(); // .draw();
		// restore painter state
		painter.restore();
		drawHealth(painter);

	}

	public void step(Pond pond) {
		// dir = dir.turn(2);
		pos = pos.move(dir, 4);
		setHealth(getHealth() * 0.999);

		// return ducks when they reach the end of the screen
		/*if (pos.getX() > PondDemo.getInstance().getScreen().getWidth())
			pos = pos.move(-PondDemo.getInstance().getScreen().getWidth(), 0);
		if (pos.getY() > PondDemo.getInstance().getScreen().getHeight())
			pos = pos.move(0, -PondDemo.getInstance().getScreen().getHeight());*/
	}

	/**
	 * @return The duck's width
	 */
	public double getWidth() {
		return 100 * size;
	}

	/**
	 * @return The duck's height
	 */
	public double getHeight() {
		return 50 * size;
	}

	/**
	 * @return Current X position
	 */
	@Override
	public double getX() {
		return pos.getX();
	}

	/**
	 * @return Current Y position
	 */
	@Override
	public double getY() {
		return pos.getY();
	}

	@Override
	public Point getPosition() {
		return pos;
	}

	@Override
	public Direction getDirection() {
		return dir;
	}
}
