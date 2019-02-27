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

    public Piece(Type type, boolean white){
        isWhite = white;
        this.type = type;
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
        if(game.getPiece(from).getColor() != game.getTurn()) return false;
        if(game.getPiece(to).getType() != Type.Empty && game.getPiece(from).getColor() == game.getPiece(to).getColor()) return false;
        return true;
    }

    public ArrayList<Cord> validMoves(Game game, Cord from){
        return new ArrayList<Cord>();
    }

    public String validMovesToString(Game game, Cord from){
        return "";
    }

    public boolean getColor() {return isWhite;}
    public Type getType() {return type;}

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
