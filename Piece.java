import java.util.ArrayList;

enum Type{
    King, Queen, Rook,
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
