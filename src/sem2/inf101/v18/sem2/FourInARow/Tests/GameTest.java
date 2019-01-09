package sem2.inf101.v18.sem2.FourInARow.Tests;

import sem2.inf101.v18.sem2.FourInARow.Elements.*;
import sem2.inf101.v18.sem2.FourInARow.Game.*;
import sem2.inf101.v18.sem2.FourInARow.Grid.*;
import sem2.inf101.v18.sem2.FourInARow.Player.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void gameBoardInteraction() {

        IGrid<IGameElement> gameBoard = new MyGrid<>(5,5,new Space());
        Player player = new HumanPlayer("Garry", '%', "RED");

        gameBoard.set(0,0, player.generateChip(0,0));
        assertEquals(gameBoard.get(0,0), player.getPlayedChips().get(0));

        gameBoard.set(0,0, new Chip('%', 0, 0));
        assertNotEquals(gameBoard.get(0,0), player.getPlayedChips().get(0));
    }

    @Test
    void winningConditions() {

        IGame game = new Game(5);
        IGrid<IGameElement> gameBoard = new MyGrid<>(5,5,new Space());
        Player player = new HumanPlayer("Garry", '%', "RED");
        game.addPlayer(player);
        game.setBoard(gameBoard);

        // horizontal win
        game.getBoard().set(0,0, player.generateChip(0, 0));
        game.getBoard().set(0,1, player.generateChip(0, 1));
        game.getBoard().set(0,2, player.generateChip(0, 2));
        game.getBoard().set(0,3, player.generateChip(0, 3));

        assertTrue(GameRules.fourConnected(player.getPlayedChips().get(3), game));

        // vertical win
        gameBoard = new MyGrid<>(5,5,new Space());
        game.setBoard(gameBoard);
        player.clearPlayedChips();

        game.getBoard().set(0,0, player.generateChip(0, 0));
        game.getBoard().set(1,0, player.generateChip(1, 0));
        game.getBoard().set(2,0, player.generateChip(2, 0));
        game.getBoard().set(3,0, player.generateChip(3, 0));

        assertTrue(GameRules.fourConnected(player.getPlayedChips().get(3), game));

        // diagonal wins
        gameBoard = new MyGrid<>(5,5,new Space());
        game.setBoard(gameBoard);
        player.clearPlayedChips();

        game.getBoard().set(0,0, player.generateChip(0, 0));
        game.getBoard().set(1,1, player.generateChip(1, 1));
        game.getBoard().set(2,2, player.generateChip(2, 2));
        game.getBoard().set(3,3, player.generateChip(3, 3));

        assertTrue(GameRules.fourConnected(player.getPlayedChips().get(3), game));

        gameBoard = new MyGrid<>(5,5,new Space());
        game.setBoard(gameBoard);
        player.clearPlayedChips();

        game.getBoard().set(0,3, player.generateChip(0, 3));
        game.getBoard().set(1,2, player.generateChip(1, 2));
        game.getBoard().set(2,1, player.generateChip(2, 1));
        game.getBoard().set(3,0, player.generateChip(3, 0));

        assertTrue(GameRules.fourConnected(player.getPlayedChips().get(3), game));
    }

    @Test
    void twoPlayersInteraction() {
        IGame game = new Game(6);
        IGrid<IGameElement> gameBoard = new MyGrid<>(6,6,new Space());
        Player player1 = new HumanPlayer("Garry", '%', "RED");
        Player player2 = new HumanPlayer("Zzed", '#', "BLUE");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.setBoard(gameBoard);

        // horizontal win check
        game.getBoard().set(1,0, player2.generateChip(1, 0));
        game.getBoard().set(0,0, player1.generateChip(0, 0));
        game.getBoard().set(2,0, player1.generateChip(2, 0));
        game.getBoard().set(3,0, player1.generateChip(3, 0));

        assertEquals(player2.getPlayedChips().get(0), gameBoard.get(1, 0));
        assertFalse(GameRules.fourConnected(player1.getPlayedChips().get(2), game));

        game.getBoard().set(4,0, player1.generateChip(4, 0));
        game.getBoard().set(5,0, player1.generateChip(5, 0));

        assertTrue(GameRules.fourConnected(player1.getPlayedChips().get(3), game));

        gameBoard = new MyGrid<>(6,6,new Space());
        game.setBoard(gameBoard);
        player1.clearPlayedChips();
        player2.clearPlayedChips();

        // vertical win check
        game.getBoard().set(0,1, player2.generateChip(0, 1));
        game.getBoard().set(0,0, player1.generateChip(0, 0));
        game.getBoard().set(0,2, player1.generateChip(0, 2));
        game.getBoard().set(0,3, player1.generateChip(0, 3));

        assertEquals(player2.getPlayedChips().get(0), gameBoard.get(0, 1));
        assertFalse(GameRules.fourConnected(player1.getPlayedChips().get(2), game));

        game.getBoard().set(0,4, player1.generateChip(0, 4));
        game.getBoard().set(0,5, player1.generateChip(0, 5));

        assertTrue(GameRules.fourConnected(player1.getPlayedChips().get(3), game));

        gameBoard = new MyGrid<>(6,6,new Space());
        game.setBoard(gameBoard);
        player1.clearPlayedChips();
        player2.clearPlayedChips();

        // diagonal win check
        game.getBoard().set(1,1, player2.generateChip(1, 1));
        game.getBoard().set(0,0, player1.generateChip(0, 0));
        game.getBoard().set(2,2, player1.generateChip(2, 2));
        game.getBoard().set(3,3, player1.generateChip(3, 3));

        assertEquals(player2.getPlayedChips().get(0), gameBoard.get(1, 1));
        assertFalse(GameRules.fourConnected(player1.getPlayedChips().get(2), game));

        game.getBoard().set(4,4, player1.generateChip(4, 4));
        game.getBoard().set(5,5, player1.generateChip(5, 5));

        assertTrue(GameRules.fourConnected(player1.getPlayedChips().get(3), game));
    }

    @Test
    void drawTest() {
        IGame game = new Game(2);
        IGrid<IGameElement> gameBoard = new MyGrid<>(2,2, new Space());
        Player player1 = new HumanPlayer("Garry", '%', "RED");
        game.addPlayer(player1);
        game.setBoard(gameBoard);

        game.getBoard().set(0,0, player1.generateChip(0, 0));
        game.getBoard().set(0,1, player1.generateChip(0, 1));

        assertFalse(game.draw());

        game.getBoard().set(1,0, player1.generateChip(1, 0));

        assertFalse(game.draw());

        game.getBoard().set(1,1, player1.generateChip(1, 1));

        assertTrue(game.draw());
    }
}