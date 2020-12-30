package checkers;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Mapa m = new Mapa();	
		
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
					

					// moveIA(m, false);
					// System.out.println();
					//m.showMap();
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
	private static int prof;
	private static int score;
	// private static int scoreTotal[];


	private static void moveIA(Mapa m, boolean ia) {
		//Mapa a = new Mapa(m);
		LinkedList<Piece> iaPieces;
		
		if (ia) {
			System.out.println("\n**********************************");
			System.out.println("          Player 2 Move!");
			System.out.println("**********************************\n");
			iaPieces= m.getWhites();
			//int[] scoreTotal = new int[iaPieces.size()];
			HashMap<Piece, Map.Entry<Movement, Integer>> scoreTotal = new HashMap<>();
		//else {
		// 	System.out.println("\n**********************************");
		// 	System.out.println("          Player 1 Move!");
		// 	System.out.println("**********************************\n");
		// 	iaPieces= m.getBlacks();
		// }
			System.out.println("Temos " + iaPieces.size());
			for(int i = 0; i < iaPieces.size(); i++){
				Mapa map = new Mapa(m);
				//map = a;
				boolean hasMoves = iaPieces.get(i).checkValidMoves(m.getMap());
			
				if (hasMoves) {
					Map.Entry<Movement, Integer> move = minimax(5, IA, map, iaPieces.get(i));
					if (move != null){
						System.out.println(move.getKey()+" => " +move.getValue());
						scoreTotal.put(iaPieces.get(i), move);
					}
				}
				
			}
			Map.Entry<Piece, Map.Entry<Movement, Integer>> maxEntry = null;

			for (Map.Entry<Piece, Map.Entry<Movement, Integer>> entry : scoreTotal.entrySet())
			{
				if (maxEntry == null || entry.getValue().getValue().compareTo(maxEntry.getValue().getValue()) > 0)
				{
					maxEntry = entry;
				}
			}
			maxEntry.getKey().setMovement(maxEntry.getValue().getKey());
			m.movePiece(maxEntry.getKey());
			//Aqui teño a peza a mover pero non o movemento
			//System.out.println("Index of best score is: "+getIndexOfLargest(scoreTotal));
		} 
		return;

		// LinkedList<Piece> movePieces = new LinkedList<Piece>();
		// for (Piece piece : iaPieces) {
		// 	boolean hasMoves = piece.checkValidMoves(m.getMap());
			
		// 	if (hasMoves) {
		// 		movePieces.add(piece);
		// 	}
				
		// }
		
		// if (movePieces.size() > 0) {
		// 	Piece p = movePieces.get(0);
		// 	Collections.sort(p.getValidMoves());
		// 	p.setMovement(p.getValidMoves().get(0));
		// 	m.movePiece(p);
		//}
	}

	private static Map.Entry<Movement, Integer> minimax(int depth, int turn, Mapa m, Piece iaPiece){
		System.out.println(iaPiece.getValidMoves());
		LinkedList<Movement> movePieces = iaPiece.getValidMoves();
		HashMap<Movement,Integer> movementScored= new HashMap<>();
		if(turn == IA){
			for(int i = 0; i < movePieces.size(); i++){
				score = 0;
				Movement movement = movePieces.get(i);
				iaPiece.setMovement(movement);
				m.movePiece(iaPiece);
				//TODO darlle os valores de verdade para o score
				if (movement.isEatMovement()){
					score += 10;
				} else {
					score += 1;
				}
				movementScored.put(movement, score);
			}

			Map.Entry<Movement, Integer> maxEntry = null;
			for (Map.Entry<Movement, Integer> entry : movementScored.entrySet())
			{
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
				{
					maxEntry = entry;
				}
			}
			return maxEntry;
		}
		System.out.println("ERROR");
		return null;
	}
}
