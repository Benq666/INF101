package sem2.inf101.v18.sem2.FourInARow.Game;

import sem2.inf101.v18.sem2.FourInARow.Elements.*;
import sem2.inf101.v18.sem2.FourInARow.Grid.IGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Separate class that represents game rules. New rules can be added here
 * and then they can be called from the game class to check the win conditions.
 *
 */
public class GameRules {

    /**
     * Check if the chip has enough neighbours in a row.
     *
     * @param chip Recently added chip.
     * @return If it's 4-in-a-row - return true. False otherwise.
     */
    public static boolean fourConnected(Chip chip, IGame game) {
        int xPos = chip.getXPos();
        int yPos = chip.getYPos();
        List<Chip> connectedChips = new ArrayList<>();
        int height = game.getBoard().getHeight();
        int width = game.getBoard().getWidth();
        IGrid<IGameElement> gameBoard = game.getBoard();

        // check the horizontal connection
        int counter = 0;

        for (int i = yPos; i < height; i++) {
            if ((gameBoard.get(xPos, i).getTempSymb() == chip.getTempSymb())) {
                connectedChips.add((Chip) gameBoard.get(xPos, i));
                counter++;
            }
            else break;
        }
        for (int i = yPos - 1; i >= 0 ; i--) {
            if (gameBoard.get(xPos, i).getTempSymb() == chip.getTempSymb()) {
                connectedChips.add((Chip) gameBoard.get(xPos, i));
                counter++;
            }
            else break;
        }

        if (counter >= 4) {
            game.changeSymbols(connectedChips, '\u2588');
            return true;
        } else connectedChips.clear();

        // check the vertical connection
        counter = 0;

        for (int i = xPos; i < width; i++) {
            if (gameBoard.get(i, yPos).getTempSymb() == chip.getTempSymb()) {
                connectedChips.add((Chip) gameBoard.get(i, yPos));
                counter++;
            }

            else break;
        }
        for (int i = xPos - 1; i >= 0; i--) {
            if (gameBoard.get(i, yPos).getTempSymb() == chip.getTempSymb()) {
                connectedChips.add((Chip) gameBoard.get(i, yPos));
                counter++;
            }

            else break;
        }

        if (counter >= 4) {
            game.changeSymbols(connectedChips, '\u2588');
            return true;
        } else connectedChips.clear();

        // check the diagonal connection (upper left to bottom right)
        counter = 0;

        for (int i = xPos; i >= 0; i--) {
            if (gameBoard.get(i, i).getTempSymb() == chip.getTempSymb()) {
                connectedChips.add((Chip) gameBoard.get(i, i));
                counter++;
            }
            else break;
        }
        for (int i = xPos + 1; i < width; i++) {
            if (gameBoard.get(i, i).getTempSymb() == chip.getTempSymb()) {
                connectedChips.add((Chip) gameBoard.get(i, i));
                counter++;
            }
            else break;
        }

        if (counter >= 4) {
            game.changeSymbols(connectedChips, '\u2588');
            return true;
        } else connectedChips.clear();

        // check the diagonal connection (bottom left to upper right)
        // this one, in my opinion, is easier to implement with while() instead of for()
        counter = 0;
        int i = xPos;
        int j = yPos;

        while (i < width && j >= 0 &&
                gameBoard.get(i, j).getTempSymb() == chip.getTempSymb()) {
            connectedChips.add((Chip) gameBoard.get(i, j));
            counter++;
            i++;
            j--;
        }

        i = xPos - 1;
        j = yPos + 1;
        while (i >= 0 && j < height &&
                gameBoard.get(i, j).getTempSymb() == chip.getTempSymb()) {
            connectedChips.add((Chip) gameBoard.get(i, j));
            counter++;
            i--;
            j++;
        }

        if (counter >= 4) {
            game.changeSymbols(connectedChips, '\u2588');
            return true;
        }

        return false;
    }
}
