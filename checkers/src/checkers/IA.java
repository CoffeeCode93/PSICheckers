package checkers;

import java.util.Collections;
import java.util.LinkedList;

public class IA {
    private final static int IA = 0;
	private final static int HUMAN = 1;

    public static void moveIA(CheckersMap m, boolean ia, int depth) {
        LinkedList<Piece> iaPieces;
		
		if (ia) {
			System.out.println("\n**********************************");
			System.out.println("          Player 2 Move!");
			System.out.println("**********************************");
			iaPieces = m.getWhites();
		} else {
			iaPieces = m.getBlacks();
        }

        LinkedList<Piece> validPieces = new LinkedList<Piece>();

        for(int i = 0; i < iaPieces.size(); i++){
            CheckersMap map = new CheckersMap(m);

            System.out.println("\n-> Check movements of the real piece. [" + iaPieces.get(i).getX() + "," + iaPieces.get(i).getY() + "]");
            boolean hasMoves = iaPieces.get(i).checkValidMoves(m.getMap());
            
            if (hasMoves) {
                validPieces.add(iaPieces.get(i));
                minimax(depth, ia, map, iaPieces.get(i));
                
                Collections.sort(iaPieces.get(i).getValidMoves());
            }
        }
        Collections.sort(validPieces);

        System.out.println("\nShowing the list of pieces with their moves");
        for (Piece piece : validPieces) {
            System.out.println("Piece on: " + piece.getX() + "-" + piece.getY());
            for (Movement move : piece.getValidMoves()) {
                System.out.println("\t" + move.toString());
            }
        }

        Piece bestPiece = validPieces.getFirst();
        Movement bestMovement = bestPiece.getValidMoves().getFirst();
        bestPiece.setMovement(bestMovement);

        System.out.println("\n[IA Movement] SCORE: " + bestMovement.getScore());
        System.out.println("[IA Movement] FROM: " + bestPiece.getX() + "-"+ bestPiece.getY());
        System.out.println("[IA Movement] TO: " + bestPiece.getMovement().getGoX() + "-"+ bestPiece.getMovement().getGoY());
        System.out.println("[IA Movement] EAT: " + bestMovement.isEatMovement() + "\n");
        m.movePiece(bestPiece);

		return;
	}
    
	public static void minimax(int depth, Boolean ia, CheckersMap m, Piece piece) {		
		Piece iaPiece = new Piece(piece);
		LinkedList<Movement> movePieces = iaPiece.getValidMoves();

        for(int i = 0; i < movePieces.size(); i++){
            Movement movement = new Movement(movePieces.get(i));
            iaPiece.setMovement(movement);
            int score = 0;

            //Puntuación laterales (non poden comer)
            if (movement.getGoY() == 0 || movement.getGoY() == 7){
                score += 4;
            }
            if (movement.getGoY() == 1 || movement.getGoY() == 6){
                score += 3;
            }
            if (movement.getGoY() == 2 || movement.getGoY() == 5){
                score += 2;
            }
            if (movement.getGoY() == 3 || movement.getGoY() == 4){
                score += 1;
            }
            //Puntuación coronar
            if (movement.getGoX() == 7) {
                //si xa e dama como o lateral
                if(iaPiece.isKing()){
                    score += 4;
                } else {
                    score += 6;
                }
            }
            if (movement.isEatMovement()){
                score += 10;
            }
            
            movement.setScore(score);

            int depthScore = 0;
            if (depth > 1) {
                m.movePiece(iaPiece);
                // Need to move a piece of the player
                
                Piece depthPiece = new Piece(iaPiece);
                System.out.println("--> Check submovements for an iaPiece on: " + movement.getPiece().getX() + "-" + movement.getPiece().getY());

                boolean hasMoves = depthPiece.checkValidMoves(m.getMap());
                if (hasMoves) {
                    minimax(depth-1, ia, m, depthPiece);

                    for (Movement move : depthPiece.getValidMoves()) {
                        depthScore += move.getScore();
                    }
                }
            }

            movePieces.get(i).setScore(score+depthScore);
        }

		return;
	}
}
