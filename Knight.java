public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        return true;
    }

    @Override
    public char to_char(){
        return is_white? 'N' : 'n';
    }
}