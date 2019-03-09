import java.util.ArrayList;

public class GameHelper{
    private static String turnToString(boolean white){return white? "white" : "black";}

    //checks if a king of a color is alive in a game
    public static boolean kingAlive(Game game, boolean color){
        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++)
                if(game.getBoard()[i][j].getType() == Type.King && game.getBoard()[i][j].getColor() == color)
                    return true;
        return false;
    }

    public static boolean cordColor(Cord at){
        return (at.getRank() + at.getFile()) % 2 == 0? Constant.BLACK : Constant.WHITE;
    }

    //0 for not in mate, 1 for checkmate, 2 for stale mate
    public static int inMate(Game game){
        ArrayList<Move> moves = allLegalMoves(game);
        if(moves.size() == 0){
            if(inCheck(game)) return Constant.CHECK;
            else return Constant.STALE;
        }
        return Constant.NO;
    }

    //checks if the move is sucide/puting it self in check
    public static boolean sucide(Game game, Move move){
        Game tempGame = new Game(game);
        tempGame.simpleMove(move);
        tempGame.changeTurn();
        ArrayList<Move> moves = GameHelper.allValidMoves(tempGame);
        for(Move nextMove : moves){
            if(tempGame.getPiece(nextMove.getTo()).getType() == Type.King)
                return true;
        }
        return false;
    }

    //cehcks if the game is in check
    public static boolean inCheck(Game game){
        Game tempGame = new Game(game);
        tempGame.changeTurn();
        ArrayList<Move> moves = allValidMoves(tempGame);
        for(Move nextMove : moves){
            if(tempGame.getPiece(nextMove.getTo()).getType() == Type.King)
                return true;
        }
        return false;
    }

    /**
     * Checks a pieces move rules to decide if a move is allowed.
     * @param from Take a coordinate value that the piece starts on
     * @param to Takes a coordinate value that the piece wants to move to
     * @return Invokes the .is_legal() method from the piece, which contains the pieces
     * move rules that are evaluated based on the x,y coordinates that the piece is on and can move to.
     * .is_legal() returns true if the piece is allowed to make that move, false otherwise
     */
    public static boolean legalMove(Game game, Move move){
        if(game.getEnd() != Constant.ONGOING){
            System.out.println("Error: Game's Over");
            return false;
        }
        if(game.getPiece(move.getFrom()).getType() == Type.Empty){
            System.out.println("Error: Can't move an Empty Piece.");
            return false;
        }
        if(game.getPiece(move.getFrom()).getColor() != game.getWhiteTurn()){
            System.out.println("Error: It's not " + turnToString(!game.getWhiteTurn()) + "'s turn.");
            return false;
        }
        if(game.getPiece(move.getTo()).getType() != Type.Empty && game.getPiece(move.getFrom()).getColor() == game.getPiece(move.getTo()).getColor()){
            System.out.println("Error: Friendly Fire");   //example. white cant capture white, white can only capture black
            return false;
        }
        if(sucide(game, move)){
            System.out.println("Error: Can't put self in check.");
            return false;
        }
        return game.getPiece(move.getFrom()).isLegal(game, move);
    }

    /**
     * prints the state of the board using ascii characters to the terminal for the player to reference
     */
    public static void printState(Game game){
        printInfo(game);

        System.out.println();
        System.out.println("All Legal Moves: " + Move.movesToString(allLegalMoves(game)));

        System.out.println();
        System.out.print("Current FEN State: ");
        printFEN(game);
        
        printBoard(game);
    }

    public static void printInfo(Game game){
        System.out.println();
        System.out.println("Turn " + game.getTurn() + ":");
        System.out.println("Fifty-move Rule: " + game.getPeace());
        System.out.println("Currently " + turnToString(game.getWhiteTurn()) + "'s turn.");
        System.out.printf("White's Advantage: %.2f\n", game.getAdvantage());
    }

    public static void printBoard(Game game){
        System.out.println();
        System.out.print(' ');
        for(int j = 0; j < game.getFileSize(); j++)
            System.out.print(" " + (char) ('a' + j));
        System.out.println();

        for(int i = game.getRankSize() - 1; i >= 0; i--){
            System.out.print(i + 1 + " ");
            for(int j = 0; j < game.getFileSize(); j++){
                System.out.print(game.getBoard()[i][j].toCharacter() + " ");
            }
            System.out.print(i + 1 + " ");
            System.out.println();
        }

        System.out.print(' ');
        for(int j = 0; j < game.getFileSize(); j++)
            System.out.print(" " + (char) ('a' + j));

        System.out.println();
        System.out.println();
    }

    public static ArrayList<Move> allValidMoves(Game game){
        ArrayList<Move> moves = new ArrayList<Move>();
        Cord from;
        ArrayList<Cord> current;

        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++){
                from = new Cord(i, j);
                current = game.getPiece(from).validMoves(game, from);
                for(Cord to : current)
                    moves.add(new Move(from, to));
            }

        return moves;
    }

    public static ArrayList<Move> allLegalMoves(Game game){
        ArrayList<Move> moves = new ArrayList<Move>();
        Cord from;
        ArrayList<Cord> current;

        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++){
                from = new Cord(i, j);
                current = game.getPiece(from).legalMoves(game, from);
                for(Cord to : current)
                    moves.add(new Move(from, to));
            }

        return moves;
    }

    public static void printFEN(Game game){
        StringBuilder gameState = new StringBuilder();
        for(int i = (game.getRankSize() - 1); i > -1; i--){
          StringBuilder rankState = new StringBuilder();
          rankState.setLength(0);
          int counter = 0;
          for(int j = 0; j < (game.getFileSize()); j++){
            char symbol = game.getBoard()[i][j].toCharacter();

            if ((symbol != '-') && (symbol != '+'))
              rankState.append(symbol);
            else {
              counter += 1;
              if(j == 7){
                rankState.append(counter);
                counter = 0;
              } else if ((game.getBoard()[i][j + 1].toCharacter() != '-') && (game.getBoard()[i][j + 1].toCharacter() != '+')) {
                rankState.append(counter);
                counter = 0;}
              }
            }

            gameState.append(rankState);
            gameState.append('/');
              }
        
        if(game.getWhiteTurn()){gameState.append(" w ");}else{gameState.append(" b ");}
        if(game.getWhiteKingCastle()){gameState.append("K");}
        if(game.getWhiteQueenCastle()){gameState.append("Q");}
        if(game.getBlackKingCastle()){gameState.append("k");}
        if(game.getBlackQueenCastle()){gameState.append("q");}
        gameState.append(" - "); //en passant goes here, needs to get finished first
        gameState.append(game.getPeace());
        gameState.append(" ");
        gameState.append(game.getTurn());
        
        System.out.println(gameState);
      }
}
