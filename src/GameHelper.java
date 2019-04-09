import java.util.ArrayList;
import java.util.Random;

public class GameHelper{
    /**
     * Function that creates an arrayList holding all legal moves
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @return ArrayList of all legal moves of the current board
     */
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

    //todo improve logic with allvalidomoves. remove dupe code
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
    
    //if Constant.WHITE return "white" otherwise
    public static String turnToString(boolean white){return white? "white" : "black";}

    //checks if a king of a color is alive in a game
    public static boolean kingAlive(Game game, boolean color){
        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++)
                if(game.getBoard()[i][j].getType() == Type.King && game.getBoard()[i][j].getColor() == color)
                    return true;
        return false;
    }

    //the color of the cordanet. iif its a blac or whitet squer
    public static boolean cordColor(Cord at){
        return (at.rank() + at.file()) % 2 == 0? Constant.BLACK : Constant.WHITE;
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
            if(tempGame.getPiece(nextMove.to()).getType() == Type.King)
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
            if(tempGame.getPiece(nextMove.to()).getType() == Type.King)
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
    public static boolean legalMove(Game game, Move move){//!!!!!!!!!!!!someone make isLegal() throw errors for the human plaer class to catch. betther then this way
        if(game.getEnd() != Constant.ONGOING){
            System.out.println("Error: Game's Over");
            return false;
        }
        if(game.getPiece(move.from()).getType() == Type.Empty){
            System.out.println("Error: Can't move an Empty Piece.");
            return false;
        }
        if(game.getPiece(move.from()).getColor() != game.getWhiteTurn()){
            System.out.println("Error: It's not " + turnToString(!game.getWhiteTurn()) + "'s turn.");
            return false;
        }
        if(game.getPiece(move.to()).getType() != Type.Empty && game.getPiece(move.from()).getColor() == game.getPiece(move.to()).getColor()){
            System.out.println("Error: Friendly Fire");   //example. white cant capture white, white can only capture black
            return false;
        }
        if(sucide(game, move)){
            System.out.println("Error: Can't put self in check.");
            return false;
        }
        return game.getPiece(move.from()).isLegal(game, move);
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
        String current = GameInfo.FENBoard(game);
        int repetition = 0;
        for(String position: history)
            if(position.equals(current))
                repetition++;
        return repetition;
    }

    //if a piece is to the right of a king, used for casteling
    public static boolean leftOfKing(Game game, Cord at){
        for(int i = at.file() + 1; i < game.getFileSize(); i++)
            if(game.getPiece(new Cord(at.rank(), i)).getType() == Type.King)
                return true;
        return false;
    }

    //if a piece is to the right of a king, used for casteling
    public static boolean rightOfKing(Game game, Cord at){
        for(int i = at.file() - 1; i >=0; i--)
            if(game.getPiece(new Cord(at.rank(), i)).getType() == Type.King)
                return true;
        return false;
    }
    
    public static boolean FENFormat(String FEN){
        if(FEN.length() < 10) return false;
        return true;
    }

    //Todo
    public static String newFischerRandom(){
        String original = "RNBQKANR";

        ArrayList<Character> characters = new ArrayList<Character>();
        for(char c:original.toCharArray()){
            characters.add(c);
        }
        String shuffle = "";
        while(characters.size()!=0){
            int randPicker = (int)(Math.random() * characters.size());
            shuffle = shuffle + characters.remove(randPicker);
        }

        return shuffle.toLowerCase() + "/pppppppp/8/8/8/8/PPPPPPPP/" + shuffle + " w - - 0 1";
    }
}