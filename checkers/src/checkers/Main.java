package checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Map m = new Map();	
		boolean fin = false;
		System.out.println("You are Player 1 (black)");

			String s = "";
			do {
				//m.showMap();
				
				try {
				/* 	boolean playerMoved=false;
					
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
					} */
					

					moveIA(m, false);
					System.out.println();
					m.showMap();
					moveIA(m, true);
					System.out.println();
					m.showMap();
					System.out.println("Exit? [y/*]");
					s = "";
					s = in.readLine();
					if (s.equals("y")){
						fin = true;
					}

					if (m.getBlacks().size() == 0 && m.getWhites().size() != 0) {
						System.out.println("Player 2 Wins! Whites Wins!");
						fin = true;
					} else if (m.getBlacks().size() != 0 && m.getWhites().size() == 0) {
						System.out.println("Player 1 Wins! Blacks Wins!");
						fin = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid movement. Out of bounds result.\n");
				}catch(Exception e) {
					
				}
			} while (!s.equals("y") && !fin);
	}
	
	private static void moveIA(Map m, boolean ia) {

		LinkedList<Piece> iaPieces = null;
		LinkedList<Piece> movePieces = new LinkedList<Piece>();

		Movement newMovement = null;
		Piece p = null;

		if (ia) {
			System.out.println("\n**********************************");
			System.out.println("          Player 2 Move!");
			System.out.println("**********************************\n");
			iaPieces= m.getWhites();
		} else {
			System.out.println("\n**********************************");
			System.out.println("          Player 1 Move!");
			System.out.println("**********************************\n");
			iaPieces= m.getBlacks();
		}

		for (Piece piece : iaPieces) {
			boolean hasMoves = piece.checkValidMoves(m.getMap());
			
			if (hasMoves) {
				movePieces.add(piece);

				for (Movement movement : piece.getValidMoves()) {
					if (movement.isEatMovement()) {
						newMovement = movement;
						p = piece;
						p.setMovement(newMovement);
					}
				}
			}
				
		}


		if (movePieces.size() > 0) {
			
			if (p == null) {
				Random rand = new Random();
				int upperbound = movePieces.size();
				int random = rand.nextInt(upperbound);
				
				p = movePieces.get(random);
				
							
				Collections.sort(p.getValidMoves());
				
				upperbound = p.getValidMoves().size();
				random = rand.nextInt(upperbound);
				p.setMovement(p.getValidMoves().get(random));
			}
			m.movePiece(p);
		}
	}
}
