public class King extends Piece{
    public King(boolean white){
        super(Type.King, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.get_x() - to.get_x());
        int dy = abs(from.get_y() - to.get_y());
        if(dx == 1 && dy <= 1) valid = true;
        if(dy == 1 && dx == 0) valid = true;
        return valid && super.is_valid(board, from, to);
    }

    @Override
    public char to_char(){
        return is_white? 'K' : 'k';
    }
}