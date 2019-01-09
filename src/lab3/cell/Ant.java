package lab3.cell;

public class Ant {
	
	protected int x, y;
	protected Direction dir;
	
	public Ant(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void turnLeft() {
		// TODO update the x, y and dir fields according to a left turn 
		// of the ant.
		if (dir == Direction.NORTH) {
			x -= 1;
		    dir = Direction.WEST;
        } else if (dir == Direction.WEST) {
			y -= 1;
		    dir = Direction.SOUTH;
        } else if (dir == Direction.SOUTH) {
			x += 1;
		    dir = Direction.EAST;
        } else {
			y += 1;
		    dir = Direction.NORTH;
        }
	}

	
	public void turnRight() {
		// TODO update the x, y and dir fields according to a right turn 
		// of the ant.
        if (dir == Direction.NORTH) {
			x += 1;
            dir = Direction.EAST;
        } else if (dir == Direction.EAST) {
			y -= 1;
            dir = Direction.SOUTH;
        } else if (dir == Direction.SOUTH) {
			x -= 1;
            dir = Direction.WEST;
        } else {
			y += 1;
            dir = Direction.NORTH;
        }
	}
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
	public Ant copy() {
		return new Ant(x, y, dir);
	}

}
