enum Type{
    King, Queen, Rook,
    Bishop, Knight, Pawn,
    Empty;
}

public class Piece{
    protected boolean is_white;
    private Type type;

    public Piece(Type type, boolean white){
        is_white = white;
        this.type = type;
    }

    public Piece(){
        this(Type.Empty, false);
    }

    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return false;
    }

    public int abs(int x){
        if(x < 0) return -x;
        return x;
    }

    public String to_string(){
        return "" + type + ", " + is_white;
    }
    
    public char to_char(){
        return ' ';
    }
}