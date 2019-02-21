public class Queen extends Piece{
    public Queen(boolean white){
        super(Type.Queen, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return false;
    }

    @Override
    public char to_char(){
        return is_white? 'Q' : 'q';
    }
}