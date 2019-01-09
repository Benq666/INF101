package sem1.inf101.v18.rogue101.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.game.Game;
import sem1.inf101.v18.rogue101.game.IllegalMoveException;
import sem1.inf101.v18.rogue101.map.IMapView;
import sem1.inf101.v18.rogue101.objects.IPlayer;
import javafx.scene.input.KeyCode;

class GameTest {
    public static String TEST_MAP = "4 3\n" //
            + "####\n" //
            + "#@R#\n" //
            + "####\n" //
            ;

    @Test
    void testAttack() {
        // new game with our test map
        Game game = new Game(TEST_MAP);
        IMapView map = game.getMap();
        // get item from a position (perhaps add a helper to GameMap to make this easier)
        Rabbit rabbit = (Rabbit) map.getAll(map.getLocation(2, 1)).get(0);
        // set player as current actor and get the player object
        IPlayer player = (IPlayer) game.setCurrent(1, 1);

        // Alternative: start with empty map, and add objects to it manually

        // Game game = new Game("4 3\n####\n#  #\n####\n");
        // Rabbit rabbit = new Rabbit();
        // IPlayer player = new Player();
        // map.add(map.getLocation(2,1), rabbit);
        // map.add(map.getLocation(1,1), player);

        assertNotNull(rabbit);
        assertNotNull(player);

        try {
            player.keyPressed(game, KeyCode.RIGHT);
        } catch (IllegalMoveException e) {
            fail("Move right should not throw IllegalMoveException");
        }
        // Rabbit rabbit = new Rabbit();
        // IPlayer player = new Player();
        // map.add(map.getLocation(2,1), rabbit);
        // map.add(map.getLocation(1,1), player);
    }
}