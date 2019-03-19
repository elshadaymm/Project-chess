import java.util.ArrayList;

public class GameHelper{
    public static String turnToString(boolean white){return white? "white" : "black";}

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
    public static boolean legalMove(Game game, Move move){//disabled for casteling
        /*
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
        }*/
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

        //printHistory(game);
        //System.out.println();
        
        printBoard(game);
    }

    public static void printInfo(Game game){
        System.out.println();
        System.out.println("Turn " + game.getTurn() + ":");
        System.out.println("Fifty-move Rule: " + game.getPeace());
        System.out.println("Currently " + turnToString(game.getWhiteTurn()) + "'s turn.");
        System.out.println("FEN: " + toFEN(game));
        System.out.println("Repetition: " + repetition(game));
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

    public static void printHistory(Game game){
        for(int i = 0; i < game.getHistory().size(); i++)
            System.out.println(game.getHistory().get(i));
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

    public static String toFEN(Game game){
        return FENBoard(game) + FENInfo(game);
    }

    public static String FENBoard(Game game){
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
            if(i != 0)gameState.append('/');
        }
        return gameState.toString();
    }

    public static String FENInfo(Game game){
        StringBuilder gameState = new StringBuilder();
        gameState.append(game.getWhiteTurn()? " w " : " b " );

        if(game.getWhiteKingCastle()){gameState.append("K");}
        if(game.getWhiteQueenCastle()){gameState.append("Q");}
        if(game.getBlackKingCastle()){gameState.append("k");}
        if(game.getBlackQueenCastle()){gameState.append("q");}

        if(!game.getWhiteKingCastle() 
            && !game.getWhiteQueenCastle() 
            && !game.getBlackKingCastle() 
            && !game.getBlackQueenCastle()) {gameState.append("-");}

        gameState.append(" " + game.getEnPassant().toString() + " ");
            
        gameState.append(game.getPeace());
        gameState.append(" ");
        gameState.append(game.getTurn());

        return gameState.toString();
    }

    /**
     * checks if any board state has occured three times (Threefold Repetition)
     * records boardstate as FEN and stores it into an arraylist
     * iterates through the arraylist and checks if there are three identiacal board states
     * @param game
     * @return true if a board state has occured more then three times, else false
     */
    public static boolean threefoldRepetition(Game game){return repetition(game) >= 3;}
    public static int repetition(Game game){
        ArrayList<String> history = game.getHistory();
        String current = FENBoard(game);
        int repetition = 0;
        for(String position: history)
            if(position.equals(current))
                repetition++;
        return repetition;
    }

    //if a piece is to the right of a king, used for casteling
    public static boolean leftOfKing(Game game, Cord at){
        for(int i = at.getFile() + 1; i < game.getFileSize(); i++)
            if(game.getPiece(new Cord(at.getRank(), i)).getType() == Type.King)
                return true;
        return false;
    }

    //if a piece is to the right of a king, used for casteling
    public static boolean rightOfKing(Game game, Cord at){
        for(int i = at.getFile() - 1; i >=0; i--)
            if(game.getPiece(new Cord(at.getRank(), i)).getType() == Type.King)
                return true;
        return false;
    }
}