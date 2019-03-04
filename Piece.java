import java.util.ArrayList;

/**
 * This is the base class for all of the chess pieces (including empty)
 * It uses enum type to specify the types of pieces.
 */

enum Type{
    King, Queen, Rook,  //these should all be upper case I think (convention)?
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
    public boolean isValid(Game game, Cord from, Cord to){
        if(game.getPiece(from) == null) return false;
        if(game.getPiece(from).getColor() != game.getWhiteTurn()) 
            return false;
        else if(game.getPiece(to).getType() != Type.Empty 
            && game.getPiece(from).getColor() == game.getPiece(to).getColor()) 
            return false;
        return true;
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
                if(isValid(game, from, new Cord(i, j)))
                    moves.add(new Cord(i, j));
        return moves;
    }

    public boolean getColor() {return isWhite;}
    public Type getType() {return type;}
    public double getValue() {return value;}

    public int abs(int x){
        if(x < 0) return -x;
        return x;
    }

    public String toString(){
        return "" + type + ", " + isWhite;
    }

    public char toCharacter(){
        return  ' ';
    }
}
