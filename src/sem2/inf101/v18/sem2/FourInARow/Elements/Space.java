package sem2.inf101.v18.sem2.FourInARow.Elements;

/**
 * Empty space element for the game board.
 *
 */
public class Space implements IGameElement {
    private String color;
    private char tempSymb;
    private char origSymb;
    private int xPos = -1;
    private int yPos = -1;

    @Override
    public void setColor(String s) {
        color = s;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getColorCode(String mod) {
        return color + "_" + mod;
    }

    @Override
    public String getColorCode() {
        return color;
    }

    @Override
    public void setSymb(char symb) {
        this.tempSymb = symb;
    }

    @Override
    public char getTempSymb() {
        return tempSymb;
    }

    @Override
    public char getOrigSymb() {
        return origSymb;
    }

    @Override
    public void resetSymb() {
        tempSymb = origSymb;
    }

    @Override
    public int getXPos() {
        return xPos;
    }

    @Override
    public int getYPos() {
        return yPos;
    }

    @Override
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}
