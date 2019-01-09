package lab1.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Random;

import javafx.scene.paint.Color;

public class BallTest {
	public static final double DELTA = 1e-10;
	private static final int N = 10000;
	private Random random = new Random();
	private final BallDemo demo = new BallDemo();

	private Ball generateBall() {
		Ball b = new Ball(Color.WHITE, 100 * random.nextDouble(), demo);
		for (int i = 0; i < random.nextInt(10); i++)
			b.accelerate(random.nextDouble(), random.nextDouble());
		for (int i = 0; i < random.nextInt(100); i++)
			b.step();

		return b;
	}

	@Test
	public void dimensionsTest() {
		for (int i = 0; i < N; i++) {
			dimensionsProperty(200 * random.nextDouble());
			badDimensionsProperty(-200 * random.nextDouble());
		}
	}

	public void dimensionsProperty(double radius) {
		radius = Math.abs(radius);
		Ball b = new Ball(Color.WHITE, radius, demo);
		assertEquals(radius, b.getRadius());
		assertEquals(2 * radius, b.getHeight(), DELTA);
		assertEquals(2 * radius, b.getWidth(), DELTA);
	}

	public void badDimensionsProperty(double radius) {
		radius = -Math.abs(radius);
		try {
			new Ball(Color.WHITE, radius, demo);
			fail("Should throw exception on negative radius");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void accelerateTest() {
		for (int i = 0; i < N; i++) {
			accelerateProperty(generateBall(), 10 * random.nextDouble(), 10 * random.nextDouble());
		}
	}

	public void accelerateProperty(Ball b, double ddx, double ddy) {
		double dx = b.getDeltaX();
		double dy = b.getDeltaY();
		b.accelerate(ddx, ddy);
		assertEquals(dx + ddx, b.getDeltaX(), DELTA);
		assertEquals(dy + ddy, b.getDeltaY(), DELTA);
	}

	@Test
	public void moveToTest() {
		for (int i = 0; i < N; i++) {
			moveToProperty(generateBall(), 10 * random.nextDouble(), 10 * random.nextDouble());
		}
	}

	public void moveToProperty(Ball b, double x, double y) {
		b.moveTo(x, y);
		assertEquals(x, b.getX(), DELTA);
		assertEquals(y, b.getY(), DELTA);
	}

	@Test
	public void stepTest() {
		for (int i = 0; i < N; i++) {
			stepProperty(generateBall());
		}
	}

	public void stepProperty(Ball b) {
		double x = b.getX();
		double y = b.getY();
		double dx = b.getDeltaX();
		double dy = b.getDeltaY();
		b.step();
		assertEquals(x + dx, b.getX(), DELTA);
		assertEquals(y + dy, b.getY(), DELTA);
	}

	@Test
	public void haltTest() {
		for (int i = 0; i < N; i++) {
			haltProperty(generateBall());
		}
	}

	public void haltProperty(Ball b) {
		b.halt();
		assertEquals(0.0, b.getDeltaX());
		assertEquals(0.0, b.getDeltaY());
	}

}
