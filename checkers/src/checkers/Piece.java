package checkers;

public class Piece {
	private int x;
	private int y;
	
	private int type = 0;
	private boolean isKing = false;
	private Movement movement = new Movement(this);
	public boolean isEated = false;
	
	public Piece(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x; this.y = y;
	}
	
	void move(int x, int y) {
		// Check if is a valid position
		// if true
			movement = new Movement(this, x, y);
		return;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isKing() {
		return isKing;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

}