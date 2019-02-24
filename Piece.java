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

    public boolean isValid(Piece[][] board, Cord from, Cord to){
        if(board[to.getY()][to.getX()].getType() == Type.Empty) return true;
        if(board[from.getY()][from.getX()].getColor() != board[to.getY()][to.getX()].getColor()) return true;
        else System.out.println("Error: Friendly Fire");
        return false;
    }

    //To Do
    public void validMoves(Piece[][] board, Cord from){
        return;
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
