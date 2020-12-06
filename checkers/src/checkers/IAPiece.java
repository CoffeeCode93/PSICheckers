package checkers;

import java.util.LinkedList;

public class IAPiece extends Piece{
	LinkedList<Movement> validMoves;

	public IAPiece(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void setValidMoves(LinkedList<Movement> validMoves) {
		this.validMoves = validMoves;
	}
	
	public LinkedList<Movement> getValidMoves() {
		return validMoves;
	}
	
	public boolean checkValidMoves(Piece[][] map) {
		boolean hasMoves = false;
		validMoves = MoveChecker.getMovements(this, map);
		
		if (validMoves.size() > 0) {
			hasMoves = true;
		}
		
		return hasMoves;
	}

}
