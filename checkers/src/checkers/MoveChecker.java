package checkers;

import java.util.LinkedList;

public class MoveChecker {
	static boolean checkMovement(Movement movement, Piece[][] map) {
		int x = movement.getStartX();
		int y = movement.getStartY();
		
		int nx = movement.getGoX();
		int ny = movement.getGoY();
		
		int type = movement.getPiece().getType();
		
		switch (type) {
		case 1:
			if (nx < x) {
				if (ny == y-1 || ny == y+1) {
					if (map[nx][ny] != null) {
						if (map[nx][ny].getType() == 1)
							return false;
						if(ny > y){
							if(map[nx-1][ny+1] != null)
								return false;
							movement.setGoY(ny+1);
							
						}
						if(ny < y){
							if(map[nx-1][ny-1] != null)
								return false;
							movement.setGoY(ny-1);	
						}
						movement.setEatMovement(true);
						movement.setGoX(nx-1);					
					}			
					movement.setValid(true);
					return true;
				}
				
				if(ny == y-2) {
					if (map[nx+1][ny-1] != null) {
						if (map[nx+1][ny-1].getType() == 1)
							return false;
						movement.setValid(true);
						movement.setEatMovement(true);
						return true;
					}
				}
					
				if(ny == y+2) {
					if (map[nx+1][ny+1] != null) {
						if (map[nx+1][ny+1].getType() == 1)
							return false;
						movement.setValid(true);
						movement.setEatMovement(true);
						return true;
					}
					
				}
			}
			break;
		case 2:
			
			if ( (nx < x || nx == x) || (nx < 0 || ny < 0 || nx > 7 || ny > 7) ) {
				return false;
			}else if ( ny == (y+1) || ny == (y-1) ) {
				if (map[nx][ny] != null) {
					if (map[nx][ny].getType() == 2)
						return false;
					if(ny > y){
						if(map[nx+1][ny+1] != null)
							return false;
						movement.setGoY(ny+1);
						
					}
					if(ny < y){
						if(map[nx+1][ny-1] != null)
							return false;
						movement.setGoY(ny-1);	
					}
					movement.setEatMovement(true);
					movement.setGoX(nx+1);				
				}
				movement.setValid(true);
				return true;
			}
			if(ny == y-2) {
				if (map[nx+1][ny-1] != null) {
					if (map[nx+1][ny-1].getType() == 2)
						return false;
					movement.setValid(true);
					movement.setEatMovement(true);
					return true;
				}
			}
				
			if(ny == y+2) {
				if (map[nx+1][ny+1] != null) {
					if (map[nx+1][ny+1].getType() == 2)
						return false;
					movement.setValid(true);
					movement.setEatMovement(true);
					return true;
				}
				
			}
			break;

		default:
			break;
		}
			
		return false;
	}
	
	static LinkedList<Movement> getMovements(Piece d, Piece[][] map){
		LinkedList<Movement> list = new LinkedList<Movement>();
		int startX = d.getX();
		int startY = d.getY();
		
		int nextX;
		int nextY;
		
		int max;
		
		if (d.isKing()) {
			max = 4;
			
		} else {
			max = 2;
			
			switch (d.getType()) {
			case 1:
				
				break;

			case 2:
				nextX = startX+1;
				for (int i = 0; i < max; i++) {
					if (i == 0) {
						nextY = startY-1;
						Piece p = new Piece(startX, startY);
						p.move(nextX, nextY);
						
						
						boolean valid = MoveChecker.checkMovement(p.getMovement(), map);
						if (valid) {
							list.add(p.getMovement());
						}
					} else {
						nextY = startY+1;
						Piece p = new Piece(startX, startY);
						p.move(nextX, nextY);
						p.setType(d.getType());
						
						boolean valid = MoveChecker.checkMovement(p.getMovement(), map);
						if (valid) {
							list.add(p.getMovement());
						}					
					}
				}
				
				break;

			default:
				break;
			}
		}
		for(Movement movenent : list)
		System.out.println("Movement: "+movenent.getGoX()+" "+movenent.getGoY());
		return list;
	}
	
	
}
