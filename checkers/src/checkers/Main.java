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
						s = in.readLine();
						String pos[] = s.split(" ");
						Piece d = m.getBlackPiece(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
	
						if (d != null) {
								System.out.println("Where to move");
								s = "";
								s = in.readLine();
								pos[0] = "";
								pos[1] = "";
								
								pos = s.split(" ");
								
								d.move(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
								playerMoved = m.movePiece(d);
								m.showMap();
							
							//m.mostrarTableroDebug();
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
		LinkedList<Piece> iaPieces= m.getWhites();
		System.out.println("Player 2 turn.\n");
		
		boolean moved = false;
		while(!moved) {
			int get = (int) (Math.random() * iaPieces.size());
			Piece p = iaPieces.get(get);
			try{
				LinkedList<Movement> moves = MoveChecker.getMovements(p, m.getMap());
				p.setMovement(moves.get(0));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			moved = m.movePiece(p);
			
		}
	}
}
