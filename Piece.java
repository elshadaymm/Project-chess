import java.util.ArrayList;

/**
 * This is the base class for all of the chess pieces (including empty)
 * It uses enum type to specify the types of pieces.
 */

enum Type{
    King, Queen, Rook,  //these should all be upper case I think (convention)?. <-- idk, google it and chacge it yourself if its not java conventions.
    Bishop, Knight, Pawn,
    Empty;
}

public class Piece{
    protected boolean isWhite;
    private Type type;
    protected double value;

    public Piece(Type type, boolean white){
        isWhite = white;
        this.type = type;
        updateValue();
    }

    public Piece(){
        this(Type.Empty, false);
    }

    public Piece(Piece piece){
        this(piece.getType(), piece.getColor());
    }

    /**
     * Checks that the piece to be moved is the same colour as the player whose turn it is,
     * and that the piece moving is not moving to a square with a same of the same colour.
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @param to coordinate variable of the pieces end position
     *
     */
    public boolean isValid(Game game, Move move){
        if(game.getPiece(move.getFrom()) == null) 
            return false;
        if(game.getPiece(move.getFrom()).getColor() != game.getWhiteTurn()) 
            return false;
        if(game.getPiece(move.getTo()).getType() != Type.Empty 
            && game.getPiece(move.getFrom()).getColor() == game.getPiece(move.getTo()).getColor()) 
            return false;

        return true;
    }

    public boolean isLegal(Game game, Move move){
        return isValid(game, move) && !GameHelper.sucide(game, move);
    }

    //Default value of a piece
    public void updateValue(){
        value = 0;
    }

    //Default value of a piece
    public void updateValue(Game game, Cord at){
        value = 0;
    }

    public ArrayList<Cord> validMoves(final Game game, final Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        if(game.getPiece(from).getType() == Type.Empty)
            return moves;
        
        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++)
                if(isValid(game, new Move(from, new Cord(i, j))))
                    moves.add(new Cord(i, j));
        return moves;
    }

    public ArrayList<Cord> legalMoves(final Game game, final Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        if(game.getPiece(from).getType() == Type.Empty)
            return moves;
        
        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++)
                if(isLegal(game, new Move(from, new Cord(i, j))))
                    moves.add(new Cord(i, j));
        return moves;
    }

    public boolean getColor() {return isWhite;}
    public Type getType() {return type;}
    public double getValue() {return value;}

    public String toString(){
        return "" + type + ", " + isWhite;
    }

    public char toCharacter(){
        return  ' ';
    }
}
