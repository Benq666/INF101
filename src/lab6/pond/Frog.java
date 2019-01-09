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
public class Frog extends PondDweller implements IPondObject {
	// Dette er datastrukturen (sett av feltvariabler) som lagrer tilstanden til
	// andeobjektene:
	// "private" betyr at vi ønsker at dette skal være skjult, og at feltvariablene
	// ikke kan
	// endres eller leses av objekter (av andre klasser)
	/**
	 * True if we're in the middle of a jump – in this case, we'll continue the jump
	 * movement until completed.
	 */
	private boolean jumping = false;
	/**
	 * True if we're in the middle of a "croak" (inflating throat and making a
	 * croaking sound (which we haven't implemented)) – in this case, we'll continue
	 * until the movement is complete (throat is back to normal).
	 */
	private boolean croaking = false;
	/**
	 * Step counter, used to keep track of the passage of time.
	 */
	private int step = 0;
	/**
	 * A number from 0 to 1, where 0 is the start of a movement (jump or croak) and
	 * 1 is the end.
	 */
	private double linearStep;
	/**
	 * these will be 0 when the frog is not jumping / just starting a jump, and 1
	 * when the frog is mid-jump (i.e., fully stretched out and "flying"). Obtained
	 * from linearStep using sin/cos, which gives a nice, natural feel to the move.
	 */
	private double jumpStageA, jumpStageB;
	/**
	 * Will be 1 when the throat is fully inflated/bulging, and 0 when not currently
	 * "croaking"
	 */
	private double croakStage;
	/**
	 * This will be set randomly, in order to make some jumps shorter/longer than
	 * normal
	 */
	private double jumpVariant;

	public Frog() {
		this(50, 20);
	}

	protected Color getPrimaryColor() {
		return Color.GREEN;
	}

	public Frog(double x, double y) {
		this.pos = pos.move(x, y);
		this.size = 1 + Math.min(0, Math.pow(Pond.random.nextGaussian() / 2, 2));
	}

	// Dette er definisjonen av metodene som utgjør oppførselen til andeobjektene
	// – brukes til å observere og manipulere endene

	// "public" betyr at objekter av andre klasser har lov å kalle disse metodene

	public void step(Pond pond) {
		// i stedet for vanlig Java-valg av hvilken kode som blir kjørt,
		// så vil vi kjøre koden som er i PondDweller (superklassen)
		// (hvis vi sier bare step() eller this.step() – så vil Java se på klassen
		// til objektet, og finne metoden der, eller i en superklasse)
		super.step(pond);

		
		int slow = 0;
		if (jumping) {
			int speed = (int) (1 + 3 * jumpVariant);
			// health = health * 0.999;
			double stepLength = (3 * speed * (1 + size));
			// will increase linearly from 0 to 1
			linearStep = Math.min(1.0, step % stepLength / (stepLength - 1));
			// calculate leg positions with a magic formula (found by experimentation)
			jumpStageB = Math.pow(Math.sin(3 * Math.PI / 2 - 2 * Math.PI * linearStep), 1);
			jumpStageA = linearStep > .25 && linearStep < .75 ? (1 + jumpStageB) / 2
					: (1 + (Math.sin(Math.PI * (jumpStageB) / 2))) / 2;
			jumpStageA = (1 + (Math.sin((1 + 0.7 * Math.abs(linearStep - 0.5)) * Math.PI * (jumpStageB) / 2))) / 2;
			jumpStageA -= 0.073;
			if (linearStep < 1.0) { // we're still moving
				pos = pos.move(dir, (getWidth() / (1 + 5 * jumpVariant)) * jumpStageA);
				// reposition if we're outside the screen
				if (pos.getX() > PondDemo.getInstance().getScreen().getWidth())
					pos = pos.move(PondDemo.getInstance().getScreen().getWidth(), 0);
				if (pos.getY() > PondDemo.getInstance().getScreen().getHeight())
					pos = pos.move(0, -PondDemo.getInstance().getScreen().getHeight());
			} else { // at the end of the jump, stop jumping
				jumping = false;
			}
			step++;
		} else if (croaking) { // currently inflating throat
			int speed = (int) (slow + 50 + 3 * jumpVariant);
			linearStep = Math.min(1.0, step % (10 * speed) / (9.0 * speed));
			croakStage = Math.sin(Math.sin(Math.sin(Math.PI * linearStep)));
			if (linearStep >= 1.0)
				croaking = false;
			step++;
		} // otherwise we're not currently doing anything, so we'll either continue
			// resting, or start doing something
		else if (Pond.random.nextInt((int) (1 + 10 * size)) == 0) {
			jumpVariant = Pond.random.nextDouble();
			// big frogs are more likely to sit around croaking, rather than jumping
			if (Pond.random.nextInt(5) < size)
				croaking = true;
			else
				jumping = true;
			step = 0;
		}
	}

	/**
	 * @param painter
	 */
	public void draw(ITurtle painter) {
		// first, save painter state, so we don't confuse the next duck
		painter.save();
		// go to our current coordinates – computation for Y is a bit complicated, since
		// the frog moves by jumping, so we draw it a bit higher up on the screen when
		// it is mid-jump (NOTE: this only works if the frog is moving horzontally...)
		painter.jumpTo(pos.getX(), pos.getY() - 3 * getHeight() * jumpStageA * (0.5 + jumpVariant));

		// FAR SIDE LEG (behind the body)
		painter.save();
		// painter.turn(10*jumpStageB);
		painter.setInk(bodyColor.darker());
		drawLeg(painter, jumpStageA);
		painter.restore();

		// BODY
		painter.save();

		// draw the body at an angle
		painter.turn(45 * jumpStageA * (0.5 + jumpVariant));
		// draw an ellipse, filled with bodyColor
		painter.shape().ellipse().width(getWidth()).height(getHeight()).fillPaint(bodyColor).fill();

		// THROAT
		painter.save();
		// move to correct throat position, taking into account current body angle due
		// to jumping
		painter.turn(5 - 10 * jumpStageA);
		painter.jump(getWidth() / (2.2 - 0.4 * jumpStageA));
		painter.turn(-5 + 10 * jumpStageA);
		// draw the throat (only visible when "inflated")
		painter.shape().ellipse().width(getHeight() * croakStage * 1.5).height(getHeight() * croakStage * 1.5)
				.fillPaint(bodyColor).fill();
		// restore painter state
		painter.restore();

		// HEAD
		painter.save();
		painter.turn(25 - 15 * jumpStageA);
		painter.jump(getWidth() / (2 - 0.4 * jumpStageA));
		// turn back so the head will be straight
		painter.turn(-25 + 15 * jumpStageA);
		// draw head as ellipse with headColor
		painter.shape().ellipse().width(getHeight()).height(0.6 * getHeight()).fillPaint(headColor).fill();
		// restore painter state
		painter.restore();

		painter.restore();
		// END OF BODY

		// LEG (closest to viewer)
		painter.save();
		// painter.turn(10*(1-jumpStageB));
		painter.setInk(bodyColor.darker());
		drawLeg(painter, jumpStageA);
		painter.restore();

		painter.restore(); // END OF DRAW
		drawHealth(painter);
	}

	/**
	 * @param painter
	 * @param jumpStage
	 */
	public void drawLeg(ITurtle painter, double jumpStage) {
		painter.save();

		// This can make the lines a bit prettier:
		// GraphicsContext ctx = painter.as(GraphicsContext.class);
		// ctx.setLineCap(StrokeLineCap.ROUND);

		Color c = bodyColor;
		c = c.deriveColor(0.0, 1.0, 0.95, 1.0);

		// DRAW A LEG
		//
		// When we start, 0° is "forward", and -90°/270° is "down". So, a typical
		// standing animal would have the legs going in direction -90°. Frogs have legs
		// that are folded weirdly, in order to get a nice spring effect when jumping.
		// A simple anatomical drawing of the thing we're trying to draw makes things
		// a lot easier. E.g.:
		// https://en.wikipedia.org/wiki/Frog#/media/File:Rana_skeleton.png

		// We start at the center of the frog. This code draws the lower back, from the
		// center to the hip where the thigh will be connected. In a frog this is the
		// "urostyle", in other vertebrates this will normally be the lower part of the
		// spine / vertebrae.
		painter.turn(-170 + 40 * jumpStage); // in a resting position we'll be at a slight angle
		painter.setInk(c);
		painter.setPenSize(8 * size);
		painter.draw(getWidth() / 2); // half the width means we end up at the "tail"
		painter.turn(-170 - 40 * jumpStage);

		// The thigh / femur
		c = c.deriveColor(0.0, 1.0, 0.90, 1.0); // slight color change
		painter.turn(-25 - (160 * jumpStage)); // hip angle
		painter.setInk(c);
		painter.setPenSize(6 * size);
		painter.draw(getWidth() / 3); // the thigh is large (width/3)

		// The shin / tibia and fibula
		c = c.deriveColor(0.0, 1.0, 0.95, 1.0);
		painter.turn(-160 + (165 * jumpStage));
		painter.setInk(c);
		painter.setPenSize(5 * size);
		painter.draw(getWidth() / 3); // same length as femur in frogs

		// The heel / tarsals (could be ignored in many animals)
		c = c.deriveColor(0.0, 1.0, 0.95, 1.0);
		painter.turn(165 - (160 * jumpStage));
		painter.setInk(c);
		painter.setPenSize(4 * size);
		painter.draw(getWidth() / 6);

		// The foot (which is seriously long in frogs)
		c = c.deriveColor(0.0, 1.0, 0.8, 1.0);
		painter.turn(0 - 45 * jumpStage);
		// we make three "toes"
		for (int i = -1; i < 2; i++) {
			painter.save();
			// first, metatarsal (foot bone)
			painter.turn(i * 3 * jumpStageB);
			painter.setInk(c);
			painter.setPenSize(1.5 * size);
			painter.draw(getWidth() / 6);
			// then, the phalanges (drawn as one, since we probably don't need bendy toes)
			painter.turn(-i * 1 * jumpStageB + 115 * jumpStage * (1 - jumpStage));
			painter.setInk(c);
			painter.setPenSize(1.5 * size);
			painter.draw(getWidth() / 6);
			c = c.deriveColor(0.0, 1.0, 1.2, 1.0);
			painter.restore();
		}
		painter.restore();

	}

	/** @return The duck's width */
	public double getWidth() {
		return 70 * size;
	}

	/** @return The duck's height */
	public double getHeight() {
		return 30 * size;
	}

	/** @return Current X position */
	@Override
	public double getX() {
		return pos.getX();
	}

	/** @return Current Y position */
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

	/*@Override
	public double getHealth() {
		return 1.0;
	}*/
}