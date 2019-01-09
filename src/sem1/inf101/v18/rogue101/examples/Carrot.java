package sem1.inf101.v18.rogue101.examples;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.objects.IItem;
import sem1.inf101.v18.rogue101.objects.ILivingItem;
import javafx.scene.paint.Color;

public class Carrot implements ILivingItem {
	private int hp = 1;
	private boolean pickedUp = false;

	@Override
	public void doTurn(IGame game) {
		if (hp < getMaxHealth()) {
			hp++;
		}
	}

	@Override
	public boolean draw(ITurtle painter, double w, double h) {
		painter.save();
		painter.turn(75);
		double size = ((double) hp + getMaxHealth()) / (2.0 * getMaxHealth());
		double carrotLength = size * h * .6;
		double carrotWidth = size * h * .4;
		painter.jump(-carrotLength / 6);
		painter.shape().ellipse().width(carrotLength).height(carrotWidth).fillPaint(Color.RED).fill();
		painter.jump(carrotLength / 2);
		painter.setInk(Color.BLACK);
		for (int i = -1; i < 2; i++) {
			painter.save();
			painter.turn(20 * i);
			painter.draw(carrotLength / 3);
			painter.restore();
		}
		painter.restore();
		return true;
	}

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	@Override
	public int getDefence() {
		return 0;
	}

	@Override
	public double getHealthStatus() {
		return getCurrentHealth() / getMaxHealth();
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public String getName() {
		return "carrot";
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public String getSymbol() {
		return "C";
	}

	@Override
	public int handleDamage(IGame game, IItem source, int amount) {
		hp -= amount;

		if (hp < 0) {
			// we're all eaten!
			hp = -1;
		}
		return amount;
	}

	@Override
	public int getAttack() {
		return 0;
	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public boolean wasPickedUp() {
		return pickedUp;
	}

	@Override
	public void pickedUp(boolean status) {
		pickedUp = status;
	}

	@Override
	public int getVisibility() {
		return 0;
	}
}
