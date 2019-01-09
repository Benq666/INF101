package sem1.inf101.v18.rogue101.player;

import sem1.inf101.v18.gfx.gfxmode.ITurtle;
import sem1.inf101.v18.gfx.textmode.BlocksAndBoxes;
import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.rogue101.Main;
import sem1.inf101.v18.rogue101.examples.Carrot;
import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.objects.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {
    private int hp = getMaxHealth();

    private int modAttack = 0;          // mod values for the actual increase/decrease from the picked up items
    private int baseAttack = 5;
    private int modDamage = 0;
    private int baseDamage = 5;
    private int modDefence = 0;
    private int baseDefence = 3;
    private List<IItem> inventory = new ArrayList<>();

    @Override
    public boolean draw(ITurtle painter, double w, double h) {
        return false;
    }

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return baseDefence + modDefence;
    }

    @Override
    public int getMaxHealth() {
        return 150;
    }

    @Override
    public String getName() {
        return "Garry";
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public String getSymbol() {
        return BlocksAndBoxes.BLOCK_PLAYER;
    }

    @Override
    public int handleDamage(IGame game, IItem source, int amount) {
        hp -= amount;
        return amount;
    }

    @Override
    public int getAttack() {
        return baseAttack + modAttack;
    }

    @Override
    public int getDamage() {
        return baseDamage + modDamage;
    }

    @Override
    public void keyPressed(IGame game, KeyCode key) {
        game.getPrinter().clearLine(Main.LINE_DEBUG);
        if (key == KeyCode.A) {
            tryToMove(game, GridDirection.WEST);
        } else if (key == KeyCode.W) {
            tryToMove(game, GridDirection.NORTH);
        } else if (key == KeyCode.D) {
            tryToMove(game, GridDirection.EAST);
        } else if (key == KeyCode.S) {
            tryToMove(game, GridDirection.SOUTH);
        } else if (key == KeyCode.Q) {
            tryToMove(game, GridDirection.NORTHWEST);
        } else if (key == KeyCode.E) {
            tryToMove(game, GridDirection.NORTHEAST);
        } else if (key == KeyCode.C) {
            tryToMove(game, GridDirection.SOUTHEAST);
        } else if (key == KeyCode.Z) {
            tryToMove(game, GridDirection.SOUTHWEST);
        } else if (key == KeyCode.F) {
            tryToPickUp(game);
        } else if (key == KeyCode.X) {
            tryToDrop(game);
        } else if (key == KeyCode.R) {
            tryToUse(game);
        } else if (key == KeyCode.V) {
            tryToHeal(game);
        }
        showStatus(game);
    }

    /**
     * Check if the player can move in the chosen direction according to a pressed key.
     *
     * @param game
     *            Game, for interacting with the world
     * @param dir
     *            Direction according to a {@link KeyCode} of the key that was pressed
     */
    private void tryToMove(IGame game, GridDirection dir) {

        // if there is a target for an attack in the specified direction - attack it
        for (GridDirection direction : GridDirection.EIGHT_DIRECTIONS) {
            if (dir == direction) {
                List<IActor> actors = game.getMap().getActors(game.getLocation(dir));
                for (IActor actor : actors) {
                    if (actor instanceof Rabbit || actor instanceof Zombie) {
                        game.attack(dir, actor);
                        return;
                    }
                }
            }
        }

        // if the direction is valid - the player moves in this direction
        List<GridDirection> possibleMoves = game.getPossibleMoves();
        for (GridDirection direction : possibleMoves) {
            if (dir == direction) {
                game.move(dir);
                game.displayMessage("Went " + dir.toString().toLowerCase() + "!");
                return;
            }
        }


        // if the player can't go in the specified direction, then the message is displayed
        // it contains the direction and an item that blocks the way
        // plus there is a small chance that the player will take some damage
        for (IItem item : game.getMap().getAll(game.getLocation(dir))) {
            if (item instanceof Wall) {
                if (game.getRandom().nextInt(100) < 10) {
                    game.formatMessage("You hit your head while bumping into the %S! How unlucky! (-%d HP)",
                            item.getName(), handleDamage(game, item, 5));
                } else {
                    game.formatMessage("Can't go %s! A %S blocks your way!",
                            dir.toString().toLowerCase(), item.getName());
                }
            }
        }
    }

    /**
     * Check if the player can pick up an item.
     *
     * @param game
     *            Game, for interacting with the world
     */
    private void tryToPickUp (IGame game) {
        // first we get all the items in the current location, the we pick up the valid item
        List<IItem> localItems = game.getLocalItems();
        if (!localItems.isEmpty()) {
            for (IItem item : localItems) {
                if (!(item instanceof Dust) && !(item instanceof Shade)) {
                    game.pickUp(localItems.get(localItems.indexOf(item)));
                    if (!(item instanceof Lock)) {
                        inventory.add(item);
                        // change the stats based on the buff from the picked up item,
                        // can probably be done in a more clean way
                        if (item instanceof Sword) {
                            modAttack += ((Sword) item).getAttack();
                            modDamage += ((Sword) item).getDamage();
                            modDefence += item.getDefence();
                            game.formatMessage("ATK+%d, DAM+%d, DEF+%d",
                                    ((Sword) item).getAttack(), ((Sword) item).getDamage(), item.getDefence());
                            return;
                        } else if (item instanceof Shield) {
                            modAttack += ((Shield) item).getAttack();
                            modDamage += ((Shield) item).getDamage();
                            modDefence += item.getDefence();
                            game.formatMessage("ATK+%d, DAM+%d, DEF+%d",
                                    (((Shield) item).getAttack()), ((Shield) item).getDamage(), item.getDefence());
                            return;
                        } else return;
                    } else return;
                }
            }
        }
        game.displayMessage("Nothing to pick up!");
    }

    /**
     * Check if player can drop an item from inventory
     *
     * @param game
     *            Game, for interacting with the world
     */
    private void tryToDrop (IGame game) {
        // first we check if there is something in our inventory, then drop the item
        if (!inventory.isEmpty()) {
            if (game.drop(inventory.get(0))) {
                if (inventory.get(0) instanceof Sword) {
                    modAttack -= ((Sword) inventory.get(0)).getAttack();
                    modDamage -= ((Sword) inventory.get(0)).getDamage();
                    modDefence -= inventory.get(0).getDefence();
                }
                if (inventory.get(0) instanceof Shield) {
                    modAttack -= ((Shield) inventory.get(0)).getAttack();
                    modDamage -= ((Shield) inventory.get(0)).getDamage();
                    modDefence -= inventory.get(0).getDefence();
                }
                inventory.remove(0);
            } else {
                game.displayMessage("Can't drop the item!");
            }
        } else {
            game.displayMessage("Your inventory is empty!");
        }
    }

    // not implemented yet
    private void tryToUse (IGame game) {
        List<IItem> localItems = game.getLocalItems();
        for (IItem item : localItems) {

        }
    }

    /**
     * Check if the player can heal by using valid item from the inventory
     *
     * @param game
     *            Game, for interacting with the world
     */
    private void tryToHeal(IGame game) {
        // first we search our inventory for an valid item, then use it
        // item is used only if the hp is not full, otherwise the message is written
        if (!inventory.isEmpty()) {
            for (IItem item : inventory) {
                if (item instanceof HealthPotion || item instanceof Carrot) {
                    if (hp == getMaxHealth()) {
                        game.displayMessage("You don't need healing!");
                        return;
                    }
                    if (hp <= getMaxHealth() - item.getCurrentHealth()) {
                        hp += item.getCurrentHealth();
                        game.formatMessage("Used %S and healed for %d!",
                                item.getName(), item.getCurrentHealth());
                        inventory.remove(item);
                        return;
                    } else {
                        game.formatMessage("Used %S and healed for %d!",
                                item.getName(), getMaxHealth() - hp);
                        hp = getMaxHealth();
                        inventory.remove(item);
                        return;
                    }
                }
            }
            game.displayMessage("You don't have any healing item in your inventory!");
        } else {
            game.displayMessage("Your inventory is empty!");
        }
    }

    @Override
    public void showStatus(IGame game) {
        game.formatStatus("[%s]HP:%d/%d ATK%d+%d DAM%d+%d DEF%d+%d  INV:%S",
                getName(), getCurrentHealth(), getMaxHealth(), baseAttack, modAttack,
                baseDamage, modDamage, baseDefence, modDefence, showInventory());
    }

    /**
     * Show the String with contents of the player's inventory (for the status message)
     *
     * @return
     *         List of short item names
     */
    public ArrayList<String> showInventory() {
        ArrayList<String> invItems = new ArrayList<>();
        if (!inventory.isEmpty()) {
            for (IItem item : inventory) {
                invItems.add(item.getName().substring(0,3));
            }
        }
        return invItems;
    }

    public List<IItem> getInventory() {
        return inventory;
    }

    // not used
    public void setInventory(List<IItem> oldInv) {
        inventory.addAll(oldInv);
    }

    public IItem hasKey() {
        for (IItem item : inventory) {
            if (item instanceof Key)
                return item;
        }
        return null;
    }

    public int getVisibility() {
        return 3;
    }
}