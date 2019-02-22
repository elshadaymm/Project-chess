public class Queen extends Piece{
    public Queen(boolean white){
        super(Type.Queen, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        int dx = abs(from.get_x() - to.get_x());
        int dy = abs(from.get_y() - to.get_y());
        if(dx == dy) return true;
        if(from.get_x() == to.get_x() && from.get_y() != to.get_y() || from.get_y() == to.get_y() && from.get_x() != to.get_x()) return true;
        return false;
    }

    @Override
    public char to_char(){
        return is_white? 'Q' : 'q';
    }
}