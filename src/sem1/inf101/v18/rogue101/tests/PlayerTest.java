package sem1.inf101.v18.rogue101.tests;

import sem1.inf101.v18.rogue101.examples.Rabbit;
import sem1.inf101.v18.rogue101.map.IMapView;
import org.junit.jupiter.api.Test;

import sem1.inf101.v18.grid.GridDirection;
import sem1.inf101.v18.grid.ILocation;
import sem1.inf101.v18.rogue101.game.Game;
import sem1.inf101.v18.rogue101.game.IGame;
import sem1.inf101.v18.rogue101.map.GameMap;
import sem1.inf101.v18.rogue101.objects.IItem;
import sem1.inf101.v18.rogue101.objects.IPlayer;
import javafx.scene.input.KeyCode;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    public static String TEST_MAP = "40 5\n" //
            + "########################################\n" //
            + "#...... ..C.R ......R.R......... ..R...#\n" //
            + "#.R@R...... ..........RC..R...... ... .#\n" //
            + "#... ..R........R......R. R........R.RR#\n" //
            + "########################################\n" //
            ;

    @Test
    void testPlayerGoNorth() {
        // new game with our test map
        Game game = new Game(TEST_MAP);
        // pick (3,2) as the "current" position; this is where the player is on the
        // test map, so it'll set up the player and return it
        IPlayer player = (IPlayer) game.setCurrent(3, 2);


        // find players location
        ILocation loc = game.getLocation();
        // press "UP" key
        player.keyPressed(game, KeyCode.W);
        // see that we moved north
        assertEquals(loc.go(GridDirection.NORTH), game.getLocation());
    }

    @Test
    void testPlayerMovement() {
        Game game = new Game(TEST_MAP);
        IPlayer player = (IPlayer) game.setCurrent(3, 2);
        game.setNumOfMoves(5);
        ILocation loc = game.getLocation();
        player.keyPressed(game, KeyCode.S);
        assertEquals(loc.go(GridDirection.SOUTH), game.getLocation());
        player.keyPressed(game, KeyCode.W);
        assertEquals(loc, game.getLocation());
        player.keyPressed(game, KeyCode.W);
        player.keyPressed(game, KeyCode.D);
        assertEquals(game.getMap().getLocation(4, 1), game.getLocation());
        player.keyPressed(game, KeyCode.A);
        assertEquals(loc.go(GridDirection.NORTH), game.getLocation());
    }

    @Test
    void testPlayerBumpsRabbit() {
        Game game = new Game(TEST_MAP);
        IPlayer player = (IPlayer) game.setCurrent(3, 2);
        ILocation loc = game.getLocation();
        player.keyPressed(game, KeyCode.A);
        assertEquals(loc, game.getLocation());
        player.keyPressed(game, KeyCode.D);
        assertEquals(loc, game.getLocation());
    }

    @Test
    void testPlayerBumpsWall() {
        Game game = new Game(TEST_MAP);
        IPlayer player = (IPlayer) game.setCurrent(3, 2);
        ILocation loc = game.getLocation();
        player.keyPressed(game, KeyCode.S);
        player.keyPressed(game, KeyCode.S);
        assertEquals(loc.go(GridDirection.SOUTH), game.getLocation());
    }

    @Test
    void testAttack() {
        TEST_MAP = "4 3\n" //
                + "####\n" //
                + "#@R#\n" //
                + "####\n" //
        ;
        // new game with our test map
        Game game = new Game(TEST_MAP);
        // pick (3,2) as the "current" position; this is where the player is on the
        // test map, so it'll set up the player and return it
        IMapView map = game.getMap();
        Rabbit rabbit = (Rabbit) map.getAll(map.getLocation(2, 1)).get(0);
        IPlayer player = (IPlayer) game.setCurrent(1,1);

        assertNotNull(rabbit);
        assertNotNull(player);

        int health = rabbit.getMaxHealth();
        player.keyPressed(game, KeyCode.D);

        // Two ways to test that the player attacked:

        // We can add stuff to Game so it stores the last message, then check it;
        // or have Game keep track of the last action(s) performed (e.g., attack, move, etc) and
        // then ask about it
        String lastMessage = game.getLastMessage();
        assertTrue(lastMessage.contains("inflict") || lastMessage.contains("attacks"),
                "moving right should result in attack");
        System.out.println(lastMessage);

        // Or we can check that the poor rabbit lost some health. NOTE: this will only
        // happen if the attack was successful (by default the rabbit is extremely tough and
        // the player has no chance of actually damaging it!).
        // The results are different depending on the success/fail of the attack due to random roll
        if (lastMessage.contains("hits")) {
            assertTrue(rabbit.getCurrentHealth() < health,
                    "Expected " + rabbit.getCurrentHealth() + " < " + health);
            System.out.println("Rabbit HP after attack: " + rabbit.getCurrentHealth() + "/" + health);
        }
        if (lastMessage.contains("inflict")) {
            assertTrue(rabbit.getCurrentHealth() == health,
                    "Expected " + rabbit.getCurrentHealth() + " = " + health);
        }
    }
}
