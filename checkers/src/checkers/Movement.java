package checkers;

public class Movement {
	
	private int startX;
	private int startY;
	
	private int goX;
	private int goY;
	
	private int score = 0;
	private boolean valid = false;
	private boolean eatMovement = false;
	private Piece piece;
	
	public Movement(Piece p) {
		this.piece = p;
		this.startX = p.getX();
		this.startY = p.getY();
	}
	
	public Movement(Piece p, int goX, int goY) {
		this.piece = p;
		this.goX = goX;
		this.goY = goY;
		this.startX = p.getX();
		this.startY = p.getY();
	}
	
	
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getGoX() {
		return goX;
	}

	public void setGoX(int goX) {
		this.goX = goX;
	}

	public int getGoY() {
		return goY;
	}

	public void setGoY(int goY) {
		this.goY = goY;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isEatMovement() {
		return eatMovement;
	}
	public void setEatMovement(boolean eatMovement) {
		this.eatMovement = eatMovement;
	}
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}
