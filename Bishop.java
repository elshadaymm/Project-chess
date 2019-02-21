public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return true;
    }

    @Override
    public char to_char(){
        return is_white? 'B' : 'b';
    }
}