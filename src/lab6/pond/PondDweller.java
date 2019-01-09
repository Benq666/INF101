package lab6.pond;

import javafx.scene.paint.Color;
import lab6.gfx.gfxmode.Direction;
import lab6.gfx.gfxmode.ITurtle;
import lab6.gfx.gfxmode.Point;

// abstract class – noen metoder kan mangle, men du kan ha implementasjon til
// andre metoder, og du kan ha feltvariabler
// du kan aldri lage objekter av en abstrakt klasse eller et interface
public abstract class PondDweller {
	protected double size = 1;
	protected Point pos = new Point(0, 0);
	protected Direction dir = new Direction(0);
	private double health = 1.0;
	protected Color bodyColor = getPrimaryColor();
	protected Color headColor = getPrimaryColor().darker();

	
	protected abstract Color getPrimaryColor();
	
	public void step(Pond pond) {
//		pos = pos.move(dir, 5);
		health = health * 0.999;
	}
	
	protected void drawHealth(ITurtle painter) {
		painter.save();
		painter.jumpTo(pos.getX(), pos.getY());
		painter.save();
		painter.setPenSize(8);
		painter.setInk(Color.BLACK);
		painter.draw(50);
		painter.restore();
		painter.setPenSize(5);
		painter.setInk(Color.GREEN);
		painter.draw(getHealth()*50);
		painter.setInk(Color.RED);
		painter.draw((1-getHealth())*50);
		painter.restore();
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double hp) {
		health = hp;
	}
}
