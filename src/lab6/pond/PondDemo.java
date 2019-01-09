package lab6.pond;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lab6.gfx.Screen;
import lab6.gfx.gfxmode.TurtlePainter;
import lab6.gfx.textmode.Printer;

/**
 * A demo application
 * 
 * @author anya
 *
 */
public class PondDemo extends Application {
	/**
	 * The currently running demo
	 */
	private static PondDemo demo;
	/**
	 * How many frames we normally want to render per second
	 */
	private static final int DEFAULT_FPS = 20;
	/**
	 * Number of nanoseconds in a second
	 */
	private static final long NANOS_PER_SEC = 1_000_000_000;
	/**
	 * How many frames we want to render per second
	 */
	private int targetFps = DEFAULT_FPS;
	/**
	 * How many nanoseconds to spend per frame, in order to reach {@link #targetFps}
	 */
	private long nanosPerFrame = NANOS_PER_SEC / targetFps;
	/**
	 * Running average of time spent spent per frame
	 */
	private long runningAverageTimePerFrame = nanosPerFrame;
	/**
	 * Time stamp of last frame drawn (or 0 for the first frame)
	 */
	private long lastUpdateTime = 0L;
	/**
	 * How long we slept last time
	 */
	private long lastSleep = 0L;
	/**
	 * A timer
	 */
	private AnimationTimer timer;
	/**
	 * Sequential numbering of frames
	 */
	private long frameCounter = 0L;
	/**
	 * The Screen we're drawing to
	 */
	private Screen screen;
	/**
	 * True if we're currently paused
	 */
	boolean paused;
	/**
	 * A printer for outputting text
	 */
	private Printer printer;

	/**
	 * A painter for drawing graphics
	 */
	private TurtlePainter painter;
	/**
	 * A duck pond
	 */
	private Pond pond;

	/**
	 * @return The currently running demo
	 */
	public static PondDemo getInstance() {
		return demo;
	}

	/**
	 * Start the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * Setup – runs before the first frame is drawing.
	 * 
	 * (Typically drawing frames will clear the screen, so any graphics drawn to
	 * {@link #painter} will disappear.)
	 */
	private void setup() {
		screen.setBackground(Color.BLACK);
		
		pond = new Pond();
		pond.setup(screen);
	}

	/**
	 * Application startup.
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		demo = this;
		screen = Screen.startPaintScene(stage);
		screen.setFullScreen(true);
		painter = screen.createPainter();
		printer = screen.createPrinter();
		printer.setInk(Color.DARKGREEN);
		printer.setFont(Printer.FONT_MONOSPACED);

		screen.setKeyPressedHandler((KeyEvent event) -> {
			KeyCode code = event.getCode();
			if (event.isShortcutDown()) {
				if (code == KeyCode.Q) {
					System.exit(0);
				} else if (code == KeyCode.P) {
					if(paused)
						unpause();
					else
						pause();
					return true;
				} else if (code == KeyCode.R) {
					printer.cycleMode(true);
					return true;
				} else if (code == KeyCode.F) {
					stage.setFullScreen(!stage.isFullScreen());
					return true;
				} else if (code == KeyCode.L) {
					printer.redrawTextPage();
					return true;
				}
			} else if (code == KeyCode.ENTER) {
				printer.print("\n");
				return true;
			}
			return false;
		});
		screen.setKeyTypedHandler((KeyEvent event) -> {
			if (event.getCharacter() != KeyEvent.CHAR_UNDEFINED) {
				printer.print(event.getCharacter());
				return true;
			}
			return false;
		});
		setup();

		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (lastUpdateTime > 0) {
					long timeSinceLast = now - lastUpdateTime;
					lastSleep = (lastSleep + nanosPerFrame - timeSinceLast);
					runningAverageTimePerFrame = (runningAverageTimePerFrame * 4 + timeSinceLast) / 5;
					if (frameCounter % 100 == 0) {
						System.out.printf("fps=%1.2f time=%10d avgtime=%10d ±time=%10d%n",
								NANOS_PER_SEC / (double) runningAverageTimePerFrame, timeSinceLast,
								runningAverageTimePerFrame, lastSleep);
					}
					if (lastSleep > 0L) {
						try {
							Thread.sleep(lastSleep / 1000_000L, (int) (lastSleep % 1000_000));
						} catch (InterruptedException e) {
						}
					} else {
						lastSleep = 0L;
					}
				}
				lastUpdateTime = now;
				drawFrame();
				frameCounter++;
			}

		};

		screen.setBackground(Color.CORNFLOWERBLUE);
		screen.clearBackground();
		unpause();
		stage.show();

	}

	private void unpause() {
		paused = false;
		timer.start();
	}

	private void pause() {
		paused = true;
		timer.stop();
	}

	protected void drawFrame() {
		painter.clear();
		pond.step();
		pond.draw(painter);
	}

	public Screen getScreen() {
		return screen;
	}

}
