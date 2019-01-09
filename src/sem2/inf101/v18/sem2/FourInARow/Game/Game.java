package sem2.inf101.v18.sem2.FourInARow.Game;

import sem2.inf101.v18.sem2.FourInARow.Elements.*;
import sem2.inf101.v18.sem2.FourInARow.Grid.IGrid;
import sem2.inf101.v18.sem2.FourInARow.Player.*;
import sem2.inf101.v18.sem2.FourInARow.Grid.MyGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static sem2.inf101.v18.sem2.FourInARow.Utils.ConsoleColors.RESET;
import static sem2.inf101.v18.sem2.FourInARow.Utils.InputCheck.*;

public class Game implements IGame {

    private IGrid<IGameElement> gameBoard;
    private List<Player> players = new ArrayList<>();
    private Scanner inp = new Scanner(System.in);
    private Random random = new Random();
    private Chip lastChip;
    private int numOfTurns;
    private int width;
    private int height;
    private boolean win = false;

    public Game(int size) {
        this.width = size;
        this.height = size;
    }

    @Override
    public void setup() {
        int userInp;
        String userInpStr;

        System.out.println("Please enter the number of the columns for the board:");
        userInp = checkInputInt(1, 999, 0);

        width = userInp;
        height = userInp;
        gameBoard = new MyGrid<>(width, height, new Space());

        Player player1 = new HumanPlayer("Garry", '%', "RED");
        Player player2 = new HumanPlayer("Zzed", '#', "BLUE");

        System.out.println("Please choose the number of human players (0-2):");
        userInp = checkInputInt(0, 2, 0);

        if (userInp == 2) {
            System.out.println("Do you want to customize the players? (y/n)");
            userInpStr = checkInputStr(1);

            if (userInpStr.equals("y")) {
                System.out.println("Please enter a name for Player 1:");
                userInpStr = checkInputStr(0);
                player1.setName(userInpStr);

                System.out.println("Please enter a name for Player 2:");
                userInpStr = checkInputStr(0);
                player2.setName(userInpStr);
            }

            addPlayer(player1);
            addPlayer(player2);

        } else if (userInp == 1) {
            System.out.println("Do you want to customize the human player(s)? (y/n)");
            userInpStr = checkInputStr(1);

            if (userInpStr.equals("y")) {
                System.out.println("Please enter a name for Player 1:");
                userInpStr = checkInputStr(0);
                player1.setName(userInpStr);
            }

            addPlayer(player1);
            addPlayer(new ComputerPlayer("Robot", 'R', "BLUE"));

        } else {
            addPlayer(new ComputerPlayer("Robot 1", 'R', "RED"));
            addPlayer(new ComputerPlayer("Robot 2", 'r', "BLUE"));
        }

        System.out.println("Game board:");
        printBoard();
    }

    @Override
    public void printBoard() {

        // first line ╔═╦═╗
        System.out.print("╔");
        for (int i = 0; i < width - 1; i++) {
            System.out.print("═╦");
        }
        System.out.println("═╗");

        // main field ║ ║ ║
        //            ╠═╬═╣
        for (int i = 0; i < width; i++) {
            System.out.print("║");
            for (int j = 0; j < height; j++) {
                IGameElement elem = gameBoard.get(i, j);

                if (!(elem instanceof Space)) {
                    if (win)
                        System.out.printf("%s%s%s║",
                                elem.getColorCode(), elem.getTempSymb(), RESET);
                    else {
                        if (elem == lastChip)
                            System.out.printf("%s%s%s║",
                                    elem.getColorCode("UNDERLINED"), elem.getOrigSymb(), RESET);
                        else
                            System.out.printf("%s%s%s║",
                                    elem.getColorCode(), elem.getOrigSymb(), RESET);
                    }
                } else
                    System.out.print(" ║");
            }
            if (i < width - 1) {
                System.out.printf("%n╠");
                for (int j = 0; j < width - 1; j++) {
                    System.out.print("═╬");
                }
                System.out.println("═╣");
            } else {

                //bottom line ╚═╩═╝
                System.out.printf("%n╚");
                for (int j = 0; j < width - 1; j++) {
                    System.out.print("═╩");
                }
                System.out.println("═╝");
            }
        }
    }

    @Override
    public void doTurn() {
        // players do their turns one after another
        for (Player player : players) {
            if (draw())
                return;

            int col;
            System.out.printf("%n%s%s(%s)%s's turn:%n",
                    player.getColorCode(), player.getName(), player.getSymb(), RESET);

            if (player instanceof HumanPlayer) {

                System.out.println("Choose a column: ");
                col = checkInputInt(1, width, 0);

                // if the column is occupied - choose a new one
                while (cantPlay(col)) {
                    printBoard();
                    System.out.println("Can't place the chip here!");
                    System.out.println("Choose a column: ");
                    col = inp.nextInt();
                }
                if (draw())
                    return;
            } else {
                // turn of the computer player

                // user needs to press Enter to advance the game
                // helps to better control the game flow
                inp.nextLine();

                // computer chooses a random column
                col = random.nextInt(height) + 1;
                while (cantPlay(col)) {
                    col = random.nextInt(height) + 1;
                    if (draw())
                        return;
                }
            }
            if (draw())
                return;

            int x = height - howManyElems(col) - 1;
            int y = col - 1;

            // generate and place a new chip on the board
            Chip newChip = player.generateChip(x, y);
            gameBoard.set(x, y, newChip);
            lastChip = newChip;

            // check if the placed chip finishes the game
            if (GameRules.fourConnected(newChip, this)) {
                win = true;
                printBoard();
                if (player instanceof HumanPlayer)
                    System.out.printf("%n%s%s(%s)%s won! Congrats!%n%n",
                            player.getColorCode(), player.getName(), player.getSymb(), RESET);
                else
                    System.out.printf("%n%s%s%s won! Bip bop!%n%n",
                            player.getColorCode(), player.getName(), RESET);
                break;
            }
            numOfTurns++;
            printBoard();
        }
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Check if the column is full.
     *
     * @param col Chosen column.
     *
     * @return True if the column is full. False otherwise.
     */
    private boolean cantPlay(int col) {
        return (howManyElems(col) == gameBoard.getHeight());
    }

    /**
     * Count the number of elements in a column.
     * Used to choose where to place a new chip on the board.
     *
     * @param col Chosen column.
     *
     * @return Number of elements in a column.
     */
    private int howManyElems(int col) {
        int counter = 0;
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            if (!(gameBoard.get(i, col - 1) instanceof Space))
                counter++;
        }
        return counter;
    }

    @Override
    public boolean winner() {
        return win;
    }

    @Override
    public boolean draw() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (gameBoard.get(i, j) instanceof Chip)
                    count++;
            }
        }
        return count == height * width;
    }

    /**
     * Reset the symbols of chips from the list.
     *
     * @param chips A list of chips.
     */
    private void resetSymbols (List<Chip> chips) {
        for (Chip chip : chips) {
            chip.resetSymb();
        }
    }

    @Override
    public void changeSymbols (List<Chip> chips, char symb) {
        for (Chip chip : chips) {
            gameBoard.get(chip.getXPos(), chip.getYPos()).setSymb(symb);
        }
    }

    @Override
    public IGrid<IGameElement> getBoard() {
        return gameBoard;
    }

    @Override
    public void setBoard(IGrid<IGameElement> gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }
}
