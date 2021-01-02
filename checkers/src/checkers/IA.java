package checkers;

import java.util.Collections;
import java.util.LinkedList;

public class IA {
    private static LinkedList<Piece> iaPieces;
    private static LinkedList<Piece> playerPieces;
    private static CheckersMap realMap;
    private static CheckersMap copyMap;

    public static void setupIA(CheckersMap map) {
        copyMap = new CheckersMap(map);
        realMap = map;
        iaPieces = copyMap.getWhites();
        playerPieces = copyMap.getBlacks();
    }

    public static void moveIA(CheckersMap m, boolean ia, int depth) {
        setupIA(m);

        if (ia) {
            System.out.println("\n**********************************");
            System.out.println("          Player 2 Move!");
            System.out.println("**********************************");
        } else {
            System.out.println("\n**********************************");
            System.out.println("          Player 1 Move!");
            System.out.println("**********************************");
        }

        System.out.println("-------- IA MinMax --------");
        LinkedList<Piece> iaValidPieces = getValidPieces(iaPieces, copyMap);

        for (Piece piece : iaValidPieces) {
            minimax(depth, true, m, piece);
            Collections.sort(piece.getValidMoves());
        }
        Collections.sort(iaValidPieces);

        System.out.println("\nShowing the list of pieces of the IA with their moves");
        for (Piece piece : iaValidPieces) {
            System.out.println("Piece on: " + piece.getX() + "-" + piece.getY());
            for (Movement move : piece.getValidMoves()) {
                System.out.println("\t" + move.toString());
            }
        }

        // Get the real piece by ID the copy constructor creates new objects
        Piece bestIAPiece = getRealPiece(iaValidPieces.getFirst());
        Movement bestIAMovement = iaValidPieces.getFirst().getValidMoves().getFirst();
        bestIAPiece.setMovement(bestIAMovement);
        // If the movement eats we need to set also the real piece to eat
        if (bestIAMovement.isEatMovement()) {
            bestIAMovement.setEatedPiece(getRealPiece(bestIAMovement.getEatedPiece()));
        }

        System.out.println("\n[IA Movement] SCORE: " + bestIAMovement.getScore());
        System.out.println("[IA Movement] FROM: " + bestIAPiece.getX() + "-" + bestIAPiece.getY());
        System.out.println(
                "[IA Movement] TO: " + bestIAPiece.getMovement().getGoX() + "-" + bestIAPiece.getMovement().getGoY());
        System.out.println("[IA Movement] EAT: " + bestIAMovement.isEatMovement() + "\n");

        iaValidPieces.getFirst().setMovement(iaValidPieces.getFirst().getValidMoves().getFirst());
        copyMap.movePiece(iaValidPieces.getFirst());
        realMap.movePiece(bestIAPiece);

        return;
    }

    private static LinkedList<Piece> getValidPieces(LinkedList<Piece> pieces, CheckersMap m) {
        LinkedList<Piece> validPieces = new LinkedList<Piece>();

        for (int i = 0; i < pieces.size(); i++) {
            boolean hasMoves = pieces.get(i).checkValidMoves(m.getMap());

            if (hasMoves) {
                validPieces.add(pieces.get(i));
            }
        }

        return validPieces;
    }

    private static Piece getRealPiece(Piece copyPiece) {
        for (Piece p : realMap.getWhites()) {
            if (p.getId() == copyPiece.getId()) {
                return p;
            }
        }

        for (Piece p : realMap.getBlacks()) {
            if (p.getId() == copyPiece.getId()) {
                return p;
            }
        }

        return null;
    }

    public static void minimax(int depth, Boolean ia, CheckersMap m, Piece piece) {
        if (depth == 0){
            return;
        }
        if (ia) {
            CheckersMap iaMap = new CheckersMap(m);
            Piece iaPiece = new Piece(piece);
            LinkedList<Movement> movePieces = iaPiece.getValidMoves();

            for (int i = 0; i < movePieces.size(); i++) {
                Movement movement = new Movement(movePieces.get(i));
                iaPiece.setMovement(movement);
                int score = Score(true, movement, iaPiece);
                movement.setScore(score);
                iaMap.movePiece(iaPiece);
                minimax( depth -1, false, iaMap, iaPiece);
                movePieces.get(i).setScore(iaPiece.getMovement().getScore());
            }

        } else {
            //Piece plPiece = new Piece(piece);
            LinkedList<Piece> humanPieces =  getValidPieces(m.getBlacks(),m);
            int[] Scores = new int[humanPieces.size()];
            for(int i =0; i<humanPieces.size(); i++){
                CheckersMap plMap = new CheckersMap(m);
                Piece plPiece = new Piece(humanPieces.get(i));
                LinkedList<Movement> plMoves = plPiece.getValidMoves();
                for (int j =0; j<plMoves.size(); j++){
                    CheckersMap pl2Map = new CheckersMap(plMap);
                    Piece piece2move = new Piece(plPiece);
                    piece2move.setMovement(plMoves.get(j));
                    plMoves.get(j).setScore(Score(false, plMoves.get(j), piece2move));
                    pl2Map.movePiece(piece2move);
                }
                Collections.sort(plMoves);
                Collections.reverse(plMoves);
                Scores[i]=plMoves.getFirst().getScore();
            }
            System.out.println("PEZA TRAE SCORE: " + piece.getMovement().getScore());
            piece.getMovement().setScore(piece.getMovement().getScore() + getMinValue(Scores));
            System.out.println("SCORE MAIS BAIXO DE HUMANO: " + getMinValue(Scores));
            System.out.println("SCORE FINAL: " + piece.getMovement().getScore());

        }
        return;
    }

    public static int getMinValue(int[] numbers){
        int minValue = numbers[0];
        for(int i=1;i<numbers.length;i++){
          if(numbers[i] < minValue){
            minValue = numbers[i];
          }
        }
        return minValue;
      }

    public static int Score(Boolean max, Movement movement, Piece piece) {
        int score = 0;

        if (max) {
            // Puntuación laterales (non poden comer)
            if (movement.getGoY() == 0 || movement.getGoY() == 7) {
                score += 4;
            }
            if (movement.getGoY() == 1 || movement.getGoY() == 6) {
                score += 3;
            }
            if (movement.getGoY() == 2 || movement.getGoY() == 5) {
                score += 2;
            }
            if (movement.getGoY() == 3 || movement.getGoY() == 4) {
                score += 1;
            }
            // Puntuación coronar
            if (movement.getGoX() == 7) {
                // si xa e dama como o lateral
                if (piece.isKing()) {
                    score += 4;
                } else {
                    score += 6;
                }
            }
            if (movement.isEatMovement()) {
                score += 10;
            }
            System.out.println("DEVOLVE SCORE IA => " + score);
            return score;

        } else {
            // Puntuación laterales (non poden comer)
            if (movement.getGoY() == 0 || movement.getGoY() == 7) {
                score -= 4;
            }
            if (movement.getGoY() == 1 || movement.getGoY() == 6) {
                score -= 3;
            }
            if (movement.getGoY() == 2 || movement.getGoY() == 5) {
                score -= 2;
            }
            if (movement.getGoY() == 3 || movement.getGoY() == 4) {
                score -= 1;
            }
            // Puntuación coronar
            if (movement.getGoX() == 7) {
                // si xa e dama como o lateral
                if (piece.isKing()) {
                    score -= 4;
                } else {
                    score -= 6;
                }
            }
            if (movement.isEatMovement()) {
                score -= 10;
            }
            System.out.println("DEVOLVE SCORE HUMANO => " + score +" da peza " + piece.toString()+" con movemento: " + movement.toString());
            return score;
        }
    }
}
