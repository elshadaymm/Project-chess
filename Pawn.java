public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return true;
    }

    @Override
    public char to_char(){
        return is_white? 'P' : 'p';
    }
}