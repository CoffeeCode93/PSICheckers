package checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Map m = new Map();	
		
		System.out.println("You are Player 1 (black)");

			String s = "";
			do {
				m.showMap();
				
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
					moveIA(m);
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
	
	private static void moveIA(Map m) {
		// TODO Auto-generated method stub
		LinkedList<IAPiece> iaPieces= m.getWhites();
		System.out.println("**********************************");
		System.out.println("Player 2 turn.\n");
		LinkedList<IAPiece> movePieces = new LinkedList<IAPiece>();
		for (IAPiece piece : iaPieces) {
			boolean hasMoves = piece.checkValidMoves(m.getMap());
			
			if (hasMoves) {
				movePieces.add(piece);
			}
				
		}
		System.out.println(movePieces.size());
		
		if (movePieces.size() > 0) {
			IAPiece p = movePieces.get(0);
			p.setMovement(p.getValidMoves().get(0));
			m.movePiece(p);
		}
	}
}
