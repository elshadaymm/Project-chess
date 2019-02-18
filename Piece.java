enum Type{
    King, Queen, Rook,
    Bishop, Knight, Pawn,
    Empty;
}

public class Piece{
    private boolean is_white;
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
        char piece;
        switch (type){
            case King:
                piece = 'k';
                break;
            case Queen:
                piece = 'q';
                break;
            case Rook:
                piece = 'r';
                break;
            case Bishop:
                piece = 'b';
                break;
            case Knight:
                piece = 'n';
                break;
            case Pawn:
                piece = 'p';
                break;
            default:
                piece = ' ';
                break;
        }

        if(is_white) piece += 'A' - 'a';

        return piece;
    }
}