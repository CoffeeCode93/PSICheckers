package checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		CheckersMap m = new CheckersMap();	
		
		System.out.println("You are Player 1 (black)");

			String s = "";
			m.showMap();
			do {
				
				try {
					boolean playerMoved=false;
					
					while(!playerMoved){
						System.out.println("Select piece using 'row column'. For example: 0 1");
						s = in.readLine().trim();
						String pos[] = s.split(" ");
						Piece d = m.getBlackPiece(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
	
						if (d != null) {
								System.out.println("Where to move");
								s = "";
								s = in.readLine().trim();
								pos[0] = "";
								pos[1] = "";
								
								pos = s.split(" ");
								
								d.move(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
								playerMoved = m.movePiece(d);
								m.showMap();
							
						} else {
							System.out.println("Wrong position. Try again!");
						}
					}
					
					moveIA(m, true);
					m.showMap();
					System.out.println("Exit? [y/*]");
					s = "";
					s = in.readLine();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid movement. Out of bounds result.\n");
				}catch(Exception e) {
					
				}
			} while (!s.equals("y"));
	}

	private final static int IA = 0;
	private final static int HUMAN = 1;

	private static void moveIA(CheckersMap m, boolean ia) {
		LinkedList<Piece> iaPieces;
		
		if (ia) {
			System.out.println("\n**********************************");
			System.out.println("          Player 2 Move!");
			System.out.println("**********************************");
			iaPieces= m.getWhites();

			LinkedList<Piece> validPieces = new LinkedList<Piece>();

			for(int i = 0; i < iaPieces.size(); i++){
				CheckersMap map = new CheckersMap(m);

				System.out.println("\n-> Check movements of the real piece. [" + iaPieces.get(i).getX() + "," + iaPieces.get(i).getY() + "]");
				boolean hasMoves = iaPieces.get(i).checkValidMoves(m.getMap());
			
				if (hasMoves) {
					validPieces.add(iaPieces.get(i));
					minimax(5, IA, map, iaPieces.get(i));
					
					Collections.sort(iaPieces.get(i).getValidMoves());
				}
			}
			Collections.sort(validPieces);

			for (Piece piece : validPieces) {
				System.out.println("Piece on: " + piece.getX() + "-" + piece.getY());
				for (Movement move : piece.getValidMoves()) {
					System.out.println("\t" + move.toString());
				}
			}

			Piece bestPiece = validPieces.getFirst();
			Movement bestMovement = bestPiece.getValidMoves().getFirst();
			bestPiece.setMovement(bestMovement);

			validPieces.getFirst().setMovement(validPieces.getFirst().getValidMoves().getFirst());
			System.out.println("\n[IA Movement] SCORE: " + bestMovement.getScore());
			System.out.println("[IA Movement] FROM: " + bestPiece.getX() + "-"+ bestPiece.getY());
			System.out.println("[IA Movement] EAT: " + bestMovement.isEatMovement());
			System.out.println("[IA Movement] TO: " + bestPiece.getMovement().getGoX() + "-"+ bestPiece.getMovement().getGoY() + "\n");
			m.movePiece(bestPiece);
		}

		return;
	}

	private static void minimax(int depth, int turn, CheckersMap m, Piece piece) {		
		Piece iaPiece = new Piece(piece);
		LinkedList<Movement> movePieces = iaPiece.getValidMoves();

		if(turn == IA){
			for(int i = 0; i < movePieces.size(); i++){
				Movement movement = new Movement(movePieces.get(i));
				iaPiece.setMovement(movement);
				int score = 0;

				//TODO darlle os valores de verdade para o score

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
							
				
				int depthScore = 0;
				if (depth > 1) {
					m.movePiece(iaPiece);
					// Need to move a piece of the player
					
					Piece depthPiece = new Piece(iaPiece);
					System.out.println("-> Check submovements for an iaPiece on: " + movement.getPiece().getX() + "-" + movement.getPiece().getY());
					//m.showMap();

					boolean hasMoves = depthPiece.checkValidMoves(m.getMap());
					if (hasMoves) {
						minimax(depth-1, turn, m, depthPiece);

						for (Movement move : depthPiece.getValidMoves()) {
							depthScore += move.getScore();
						}

						movement.setScore(depthScore);
					}
				}

				movePieces.get(i).setScore(score+depthScore);
			}
		}

		return;
	}
}
