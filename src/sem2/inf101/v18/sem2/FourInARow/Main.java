package sem2.inf101.v18.sem2.FourInARow;

import sem2.inf101.v18.sem2.FourInARow.Game.*;

import static sem2.inf101.v18.sem2.FourInARow.Utils.InputCheck.checkInputStr;

/**
 * A 4-in-a-row game.
 *
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to a \"Four-in-a-row\" game!");

        while (true) {
            IGame game = new Game(0);
            game.setup();

            while (!game.winner() && !game.draw())
                game.doTurn();
            if (game.draw())
                System.out.printf("You're out of moves! Game over.%n%n");

            System.out.println("Do you want to play again? (y/n)");
            if (checkInputStr(1).equals("n"))
                break;
        }

        System.out.println("Thanks for playing!");
    }
}
