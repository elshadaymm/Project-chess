public class Queen extends Piece{
    public Queen(boolean white){
        super(Type.Queen, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.get_x() - to.get_x());
        int dy = abs(from.get_y() - to.get_y());
        if(dx == dy) valid = true;
        else if(from.get_x() == to.get_x() && from.get_y() != to.get_y()) valid = true;
        else if(from.get_y() == to.get_y() && from.get_x() != to.get_x()) valid = true;
        return valid && super.is_valid(board, from, to);
    }

    @Override
    public char to_char(){
        return is_white? 'Q' : 'q';
    }
}