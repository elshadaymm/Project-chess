public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return true;
    }

    @Override
    public char to_char(){
        return is_white? 'R' : 'r';
    }
}