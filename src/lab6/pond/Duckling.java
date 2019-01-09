package lab6.pond;

import javafx.scene.paint.Color;

public class Duckling extends Duck {

    private IPondObject moren;

    public Duckling(double x, double y, IPondObject moren) {
        super(x, y);
        size = 0.4;
        headColor = Color.DARKORANGE;
        bodyColor = Color.ORANGE;
        this.moren = moren;
    }

    /*protected Color getPrimaryColor() {
        return Color.YELLOWGREEN;
    }*/

    public void step(Pond pond) {
        if (pos.distanceTo(moren.getPosition()) >= getWidth() / 2 + moren.getWidth() / 2) {
            dir = dir.turnTowards(pos.directionTo(moren.getPosition()), 100);
            pos = pos.move(dir, 5);
        }
        setHealth(getHealth() * 0.999);

        // return ducks when they reach the end of the screen
        if (pos.getX() > PondDemo.getInstance().getScreen().getWidth())
            pos = pos.move(-PondDemo.getInstance().getScreen().getWidth(), 0);
        if (pos.getY() > PondDemo.getInstance().getScreen().getHeight())
            pos = pos.move(0, -PondDemo.getInstance().getScreen().getHeight());
        /*if (pos.getX() < 0)
            pos = pos.move(PondDemo.getInstance().getScreen().getWidth(), 0);
        if (pos.getY() < 0)
            pos = pos.move(0, PondDemo.getInstance().getScreen().getHeight());*/
    }
}
