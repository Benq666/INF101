package sem1.inf101.v18.rogue101.game;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

import sem1.inf101.v18.gfx.Screen;
import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.gfxmode.TurtlePainter;
import sem1.inf101.v18.gfx.textmode.Printer;
import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.grid.IGrid;
import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.Main;
import sem1.inf101.v18.rogue101.examples.Carrot;
import sem1.inf101.v18.rogue101.examples.GreenMushroom;
import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.map.GameMap;
import sem1.inf101.v18.rogue101.map.IGameMap;
import sem1.inf101.v18.rogue101.map.IMapView;
import sem1.inf101.v18.rogue101.map.MapReader;
import sem1.inf101.v18.rogue101.objects.*;
import sem1.inf101.v18.rogue101.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Game implements IGame {
	/**
	 * All the IActors that have things left to do this turn
	 */
	private List<IActor> actors = Collections.synchronizedList(new ArrayList<>());
	/**
	 * For fancy solution to factory problem
	 */
	private Map<String, Supplier<IItem>> itemFactories = new HashMap<>();
	/**
	 * Useful random generator
	 */
	private Random random = new Random();
	/**
	 * The game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	private IGameMap map;
	private IActor currentActor;
	private ILocation currentLocation;
	private int movePoints = 0;
	private final ITurtle painter;
	private final Printer printer;
	private int numPlayers = 0;
	private List<String> lastMessages = new ArrayList<>();
	private List<String> lastActions = new ArrayList<>();
	private int currLevel = 1;

	public Game(Screen screen, ITurtle painter, Printer printer) {
		this.painter = painter;
		this.printer = printer;

		// TODO: (*very* optional) for advanced factory technique, use
		// something like "itemFactories.put("R", () -> new Rabbit());"
		// must be done *before* you read the map

		// NOTE: in a more realistic situation, we will have multiple levels (one map
		// per level), and (at least for a Roguelike game) the levels should be
		// generated
		//
		// inputGrid will be filled with single-character strings indicating what (if
		// anything)
		// should be placed at that map square
		IGrid<String> inputGrid = MapReader.readFile("maps/level1.txt");
		if (inputGrid == null) {
			System.err.println("Map not found – falling back to builtin map");
			inputGrid = MapReader.readString(Main.BUILTIN_MAP);
		}
		this.map = new GameMap(inputGrid.getArea());
		for (ILocation loc : inputGrid.locations()) {
			IItem item = createItem(inputGrid.get(loc));
			if (item != null) {
				map.add(loc, item);
			}
			// showing the status message before player's first turn
			if (item instanceof IPlayer) {
                ((IPlayer) item).showStatus(this);
            }
		}
	}

	public Game(String mapString) {
		printer = new Printer(1280, 720);
		painter = new TurtlePainter(1280, 720);
		IGrid<String> inputGrid = MapReader.readString(mapString);
		this.map = new GameMap(inputGrid.getArea());
		for (ILocation loc : inputGrid.locations()) {
			IItem item = createItem(inputGrid.get(loc));
			if (item != null) {
				map.add(loc, item);
			}
		}
	}

	@Override
	public void addItem(IItem item) {
		map.add(currentLocation, item);
	}

	@Override
	public void addItem(String sym) {
		IItem item = createItem(sym);
		if (item != null)
			map.add(currentLocation, item);
	}

	@Override
	public ILocation attack(GridDirection dir, IItem target) {
		ILocation loc = currentLocation.go(dir);
		if (!map.has(loc, target))
			throw new IllegalMoveException("Target isn't there!");

		// rolling the dice to determine the success/fail of the attack
		if (currentActor.getAttack() + random.nextInt(20) + 1 >= target.getDefence() + 10) {
			int damageAmount = target.handleDamage(this, currentActor, currentActor.getDamage());
			if (!(currentActor instanceof Zombie) || target instanceof Zombie || target instanceof IPlayer) {
				formatMessage("%S attacks %S and hits for %d damage! %S HP:%d/%d",
						currentActor.getName(), target.getName(), damageAmount, target.getName(),
						target.getCurrentHealth(), target.getMaxHealth());
			}
		} else {
			if (!(currentActor instanceof Zombie) || target instanceof Zombie || target instanceof IPlayer) {
				formatMessage("%S could not inflict any damage to %S!",
						currentActor.getName(), target.getName());
			}
		}
		map.clean(loc);
		// if the player is killed then it's game over
		// the player becomes a zombie and wanders around endlessly
		if (target.isDestroyed() && target instanceof IPlayer) {
			displayMessage("YOU DIE!!!");
			map.remove(getLocation(dir), target);
			currLevel = 5;
			nextStage();
			return null;
		}
		if (target.isDestroyed()) {
			// zombies restore some hp if they defeat an enemy
			if (currentActor instanceof Zombie)
				((Zombie) currentActor).addFood(1);
			if (!(currentActor instanceof Zombie)) {
				formatMessage("%S dies!", target.getName());
			}
			return move(dir);
		} else {
			movePoints--;
			return currentLocation;
		}
	}

	/**
	 * Begin a new game turn, or continue to the previous turn
	 * 
	 * @return True if the game should wait for more user input
	 */
	public boolean doTurn() {
		do {
			if (actors.isEmpty()) {
				// System.err.println("new turn!");
				// no one in the queue, we're starting a new turn!
				// first collect all the actors:
				beginTurn();
			}

			// process actors one by one; for the IPlayer, we return and wait for keypresses
			// Possible TODO: for INonPlayer, we could also return early (returning
			// *false*), and then insert a little timer delay between each non-player move
			// (the timer
			// is already set up in Main)
			while (!actors.isEmpty()) {
                // System.out.println(actors);
				// get the next player or non-player in the queue
				currentActor = actors.remove(0);
				if (currentActor.isDestroyed()) // skip if it's dead
					continue;
				currentLocation = map.getLocation(currentActor);
				if (currentLocation == null) {
					displayDebug("doTurn(): Whoops! Actor has disappeared from the map: " + currentActor);
				}
				movePoints = 1; // everyone gets to do one thing

				if (currentActor instanceof INonPlayer) {
					// computer-controlled players do their stuff right away
					((INonPlayer) currentActor).doTurn(this);
					// remove any dead items from current location
					map.clean(currentLocation);
				    // items with turn mechanics do their turns
				} else if(currentActor instanceof ILivingItem) {
					((ILivingItem) currentActor).doTurn(this);
				} else if (currentActor instanceof IPlayer) {
					((IPlayer) currentActor).showStatus(this);
					if (currentActor.isDestroyed()) {
						// a dead human player gets removed from the game
						displayMessage("YOU DIE!!!");
						map.remove(currentLocation, currentActor);
						currentActor = null;
						currentLocation = null;
						currLevel = 5;
						nextStage();
					} else {
						// For the human player, we need to wait for input, so we just return.
						// Further keypresses will cause keyPressed() to be called, and once the human
						// makes a move, it'll lose its movement point and doTurn() will be called again
						//
						// NOTE: currentActor and currentLocation are set to the IPlayer (above),
						// so the game remembers who the player is whenever new keypresses occur. This
						// is also how e.g., getLocalItems() work – the game always keeps track of
						// whose turn it is.
						return true;
					}
				} else {
					displayDebug("doTurn(): Hmm, this is a very strange actor: " + currentActor);
				}
			}
		} while (numPlayers > 0); // we can safely repeat if we have players, since we'll return (and break out of
									// the loop) once we hit the player
		return true;
	}

	/**
	 * Go through the map and collect all the actors.
	 */
	private void beginTurn() {
		// placing a new carrot at a random location with % probability
		if (random.nextInt(100) < 2) {
			while (true) {
				ILocation randomLocation = map.getLocation(random.nextInt(map.getWidth()),
						random.nextInt(map.getHeight()));
				// checking if the location is occupied or null
				if (!map.hasActors(randomLocation) || !map.hasWall(randomLocation)) {
					randomLocation = map.getLocation(random.nextInt(map.getWidth()),
							random.nextInt(map.getHeight()));
					map.add(randomLocation, new Carrot());
					break;
				}
			}
		}

		numPlayers = 0;
        // System.out.println("collecting actors");
		// this extra fancy iteration over each map location runs *in parallel* on
		// multicore systems!
		// that makes some things more tricky, hence the "synchronized" block and
		// "Collections.synchronizedList()" in the initialization of "actors".
		// NOTE: If you want to modify this yourself, it might be a good idea to replace
		// "parallelStream()" by "stream()", because weird things can happen when many
		// things happen
		// at the same time! (or do INF214 or DAT103 to learn about locks and threading)
		map.getArea().parallelStream().forEach((loc) -> { // will do this for each location in map
			List<IItem> list = map.getAllModifiable(loc); // all items at loc
			Iterator<IItem> li = list.iterator(); // manual iterator lets us remove() items
			while (li.hasNext()) { // this is what "for(IItem item : list)" looks like on the inside
				IItem item = li.next();
				if (item.getCurrentHealth() < 0) {
					// normally, we expect these things to be removed when they are destroyed, so
					// this shouldn't happen
					synchronized (this) {
						formatDebug("beginTurn(): found and removed leftover destroyed item %s '%s' at %s%n",
								item.getName(), item.getSymbol(), loc);
					}
					li.remove();
					map.remove(loc, item); // need to do this too, to update item map
				} else if (item instanceof IPlayer) {
					actors.add(0, (IActor) item); // we let the human player go first
					synchronized (this) {
						numPlayers++;
					}
				} else if (item instanceof INonPlayer) {
					actors.add((IActor) item); // add other actors to the end of the list
                    // adding the item if it's growing on the ground and still alive
				} else if (item instanceof ILivingItem && !((ILivingItem) item).wasPickedUp()) {
                    actors.add((IActor) item);
                } else if (item instanceof Wall) {
					((Wall) item).setupWall(this, item);
				}
			}
		});
	}

	@Override
	public boolean canGo(GridDirection dir) {
		return map.canGo(currentLocation, dir);
	}

	@Override
	public IItem createItem(String sym) {
		switch (sym) {
			case "#":
				return new Wall();
			/*case "B":
				return new BreakWall();*/
			case ".":
				return new Dust();
			case "R":
				return new Rabbit();
			case "C":
				return new Carrot();
			case "@":
				return new Player();
			case ",":
				return new Shade();
			case "M":
				return new GreenMushroom();
			case " ":
				return null;
			case "E":
				return new Lock();
			case "K":
				return new Key();
			case "S":
				return new Sword();
			case "s":
				return new Shield();
			case "P":
				return new HealthPotion();
			case "Z":
				return new Zombie();
			/*case "c":
				return new Chest();*/
			default:
				// alternative/advanced method
				Supplier<IItem> factory = itemFactories.get(sym);
				if (factory != null) {
					return factory.get();
				} else {
					System.err.println("createItem: Don't know how to create a '" + sym + "'");
					return null;
				}
		}
	}

	@Override
	public void displayDebug(String s) {
		printer.clearLine(Main.LINE_DEBUG);
		printer.printAt(1, Main.LINE_DEBUG, s, Color.DARKRED);
		System.err.println(s);
	}

	@Override
	public void displayMessage(String s) {
		// save several messages and scroll them + timestamps
        String timeStamp = new SimpleDateFormat("hh.mm.ss ").format(new Date());
		lastMessages.add(timeStamp + s);
		while (lastMessages.size() > 20)
			lastMessages.remove(0);
		while (lastActions.size() > 20)
			lastActions.remove(0);
		printer.clearLine(Main.LINE_MSG2);
		printer.printAt(2, Main.LINE_MSG2, getLastMessage());
		if (getLastMessages().size() > 1) {
			printer.clearLine(Main.LINE_MSG3);
			printer.printAt(2, Main.LINE_MSG3, getLastMessages().get(getLastMessages().size() - 2));
		}
		if (getLastMessages().size() > 2) {
			printer.clearLine(Main.LINE_MSG4);
			printer.printAt(2, Main.LINE_MSG4, getLastMessages().get(getLastMessages().size() - 3));
		}
		if (getLastMessages().size() > 3) {
			printer.clearLine(Main.LINE_MSG5);
			printer.printAt(2, Main.LINE_MSG5, getLastMessages().get(getLastMessages().size() - 4));
		}
		if (getLastMessages().size() > 4) {
			printer.clearLine(Main.LINE_MSG6);
			printer.printAt(2, Main.LINE_MSG6, getLastMessages().get(getLastMessages().size() - 5));
		}
        if (getLastMessages().size() > 5) {
            printer.clearLine(Main.LINE_MSG7);
            printer.printAt(2, Main.LINE_MSG7, getLastMessages().get(getLastMessages().size() - 6));
        }
	}

	@Override
	public String getLastMessage() {
		if (lastMessages.isEmpty())
			return "";
		else
			return lastMessages.get(lastMessages.size() - 1);
	}

	public List<String> getLastMessages() {
		return lastMessages;
	}


	@Override
	public void displayStatus(String s) {
		// UI + status message and controls
		printer.clearLine(Main.LINE_STATUS);
		printer.printAt(1, Main.LINE_MAP_BOTTOM, "╭");
		printer.printAt(2, Main.LINE_MAP_BOTTOM, "───────────────────────────────────────" +
				"───────────────────────────────────────");
		printer.printAt(40, Main.LINE_MAP_BOTTOM, "┬");
		printer.printAt(80, Main.LINE_MAP_BOTTOM, "╮");
		printer.printAt(1, Main.LINE_STATUS, "│");
		printer.printAt(2, Main.LINE_STATUS, s);
		printer.printAt(40, Main.LINE_STATUS, "│");
		printer.printAt(1, Main.LINE_MSG1, "╞");
		printer.printAt(2, Main.LINE_MSG1, "═══════════════════════════════════════" +
				"═══════════════════════════════════════");
		printer.printAt(40, Main.LINE_MSG1, "╧");
		printer.printAt(80, Main.LINE_MSG1, "╡");
		printer.printAt(1, Main.LINE_MSG2, "│");
		printer.printAt(80, Main.LINE_MSG2, "│");
		printer.printAt(1, Main.LINE_MSG3, "│");
		printer.printAt(80, Main.LINE_MSG3, "│");
		printer.printAt(1, Main.LINE_MSG4, "│");
		printer.printAt(80, Main.LINE_MSG4, "│");
		printer.printAt(1, Main.LINE_MSG5, "│");
		printer.printAt(80, Main.LINE_MSG5, "│");
		printer.printAt(1, Main.LINE_MSG6, "│");
		printer.printAt(80, Main.LINE_MSG6, "│");
		printer.printAt(1, Main.LINE_MSG7, "│");
		printer.printAt(80, Main.LINE_MSG7, "│");
		printer.printAt(1, Main.LINE_MSG8, "╰");
		printer.printAt(2, Main.LINE_MSG8, "───────────────────────────────────────" +
				"───────────────────────────────────────");
		printer.printAt(80, Main.LINE_MSG8, "╯");
		printer.printAt(2, Main.LINE_MSGBOTTOM, "Movement:W,A,S,D,E,C,Z,Q; F=PickUp/Interact; X=Drop; V=Heal");

		printer.printAt(54, 2, " You find yourself in the");
		printer.printAt(54, 3, "       dark cellar.");
		printer.printAt(54, 4, "  Very strange noises are");
		printer.printAt(54, 5, "    coming from behind");
		printer.printAt(54, 6, "        the walls.");
		printer.printAt(54, 7, "    You need to escape");
		printer.printAt(54, 8, "       this place!");
		printer.printAt(54, 10, "   Find some equipment.");
		printer.printAt(54, 11, "       Search for a key.");
		printer.printAt(54, 12, "    Find the exit.");
		printer.printAt(54, 13, "         Survive!");
		printer.printAt(54, 14, "      ..............");
		printer.printAt(54, 16, "        Good luck!");
	}


	@Override
	public String getLastAction() {
		if (lastActions.isEmpty())
			return "";
		else
			return lastActions.get(lastActions.size() - 1);
	}

	public void draw() {
		map.draw(painter, printer);
	}

	@Override
	public boolean drop(IItem item) {
		if (item != null) {
			map.add(currentLocation, item);
			movePoints--;
			displayMessage("Dropped " + item.getName().toUpperCase());
			return true;
		} else
			return false;
	}

	/**
	 * Use something in the current location (i.e. pull lever or push button)
	 *
	 * @return True if the attempt was successful, false otherwise
	 */
	public boolean use() { // haven't implement it yet
		return false;
	}

	@Override
	public void formatDebug(String s, Object... args) {
		displayDebug(String.format(s, args));
	}

	@Override
	public void formatMessage(String s, Object... args) {
		displayMessage(String.format(s, args));
	}

	@Override
	public void formatStatus(String s, Object... args) {
		displayStatus(String.format(s, args));
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	@Override
	public List<IItem> getLocalItems() {
		return map.getItems(currentLocation);
	}

	public List<IItem> getAll() {
		return map.getAll(currentLocation);
	}

	@Override
	public ILocation getLocation() {
		return currentLocation;
	}

	@Override
	public ILocation getLocation(GridDirection dir) {
		if (currentLocation.canGo(dir))
			return currentLocation.go(dir);
		else
			return null;
	}

	/**
	 * Return the game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	@Override
	public IMapView getMap() {
		return map;
	}

	/**
	 * Return all possible directions for the current actor to go to.
	 *
	 */
	@Override
	public List<GridDirection> getPossibleMoves() {
		List<GridDirection> posMoves = new ArrayList<>();
		for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
			if (canGo(dir)) {
				posMoves.add(dir);
			}
		}
		//throw new UnsupportedOperationException();
		return posMoves;
	}

	@Override
	public List<ILocation> getVisible() {
		return map.getNeighbourhood(currentLocation, currentActor.getVisibility());
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	public boolean keyPressed(KeyCode code) {
	    // only an IPlayer/human can handle keypresses, and only if it's the human's turn
		if (currentActor instanceof IPlayer) {
			((IPlayer) currentActor).keyPressed(this, code);
			return movePoints > 0;
		} else {
			return false;
		}
	}

	@Override
	public ILocation move(GridDirection dir) {
		if (movePoints < 1)
			throw new IllegalMoveException("You're out of moves!");
		ILocation newLoc = map.go(currentLocation, dir);
		map.remove(currentLocation, currentActor);
		map.add(newLoc, currentActor);
		currentLocation = newLoc;
		movePoints--;
		return currentLocation;
	}

	@Override
	public IItem pickUp(IItem item) {
		if (item != null && map.has(currentLocation, item)) {

			// checking if we can go to the next stage
			if (currentActor instanceof IPlayer && item instanceof Lock &&
					((IPlayer) currentActor).hasKey() != null) {
				nextStage();
			} else {
				if (currentActor instanceof IPlayer && item instanceof Lock &&
						((IPlayer) currentActor).hasKey() == null) {
					displayMessage("The door won't budge. Find a KEY to open it!");
					return null;
				}
			}

			// item stops growing if we pick it up from the ground (for example, a carrot or a flower)
			if (item instanceof ILivingItem) {
				((ILivingItem) item).pickedUp(true);
			}
			if (!(item instanceof Lock)) {
				map.remove(currentLocation, item);
				// removing the living item from the current turn queue
				for (int i = 0; i < actors.size(); i++) {
					if (actors.get(i) == item) {
						actors.remove(i);
					}
				}
				movePoints--;
				displayMessage("Picked up " + item.getName().toUpperCase());
				return item;
			}
		}
		return null;
	}

	@Override
	public ILocation rangedAttack(GridDirection dir, IItem target) {
		return currentLocation;
	}

	@Override
	public ITurtle getPainter() {
		return painter;
	}

	@Override
	public Printer getPrinter() {
		return printer;
	}

	@Override
	public int[] getFreeTextAreaBounds() {
		int[] area = new int[4];
		area[0] = getWidth() + 1;
		area[1] = 1;
		area[2] = printer.getLineWidth();
		area[3] = printer.getPageHeight() - 5;
		return area;
	}

	@Override
	public void clearFreeTextArea() {
		printer.clearRegion(getWidth() + 1, 1, printer.getLineWidth() - getWidth(),
				printer.getPageHeight() - 5);
	}

	@Override
	public void clearFreeGraphicsArea() {
		painter.as(GraphicsContext.class).clearRect(getWidth() * printer.getCharWidth(), 0,
				painter.getWidth() - getWidth() * printer.getCharWidth(),
				(printer.getPageHeight() - 5) * printer.getCharHeight());
	}

	@Override
	public double[] getFreeGraphicsAreaBounds() {
		double[] area = new double[4];
		area[0] = getWidth() * printer.getCharWidth();
		area[1] = 0;
		area[2] = painter.getWidth();
		area[3] = getHeight() * printer.getCharHeight();
		return area;
	}

	@Override
	public IActor getActor() {
		return currentActor;
	}

	public ILocation setCurrent(IActor actor) {
		currentLocation = map.getLocation(actor);
		if (currentLocation != null) {
			currentActor = actor;
			movePoints = 1;
		}
		return currentLocation;
	}

	public IActor setCurrent(ILocation loc) {
		List<IActor> list = map.getActors(loc);
		if (!list.isEmpty()) {
			currentActor = list.get(0);
			currentLocation = loc;
			movePoints = 1;
		}
		return currentActor;
	}

	public IActor setCurrent(int x, int y) {
		return setCurrent(map.getLocation(x, y));
	}
	
	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public void setNumOfMoves(int numMoves) {
		this.movePoints = numMoves;
	}


	/**
	 * Change the map according to the next stage file
	 *
	 */
	private void nextStage() {
		if (currLevel == 4)
			System.exit(0);
		if (currLevel == 5)
			currLevel = 4;
		if (currentActor instanceof IPlayer)
			((IPlayer) currentActor).getInventory().remove(((IPlayer) currentActor).hasKey());
		IGrid<String> inputGrid = MapReader.readFile("maps/level" + ++currLevel + ".txt");
		if (inputGrid == null) {
			System.err.println("Map not found – falling back to builtin map");
			inputGrid = MapReader.readString(Main.BUILTIN_MAP);
		}
		this.map = new GameMap(inputGrid.getArea());
		for (ILocation loc : inputGrid.locations()) {
			IItem item2 = createItem(inputGrid.get(loc));
			if (item2 != null) {
				// transferring the current player to the next stage
				if (item2 instanceof IPlayer)
					item2 = currentActor;
				map.add(loc, item2);
			}
			// showing the status of the player before player's first turn
			if (item2 instanceof IPlayer) {
				((IPlayer) item2).showStatus(this);
			}
		}
		printer.clear();
		actors.clear();
		getLastMessages().clear();
		if (currLevel != 4 && currLevel != 5)
			formatMessage("Welcome to stage %d!", currLevel);
		if (currLevel == 5)
			displayMessage("YOU DIE!!!");
		doTurn();
	}
}
