public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
      boolean valid = false;
      int dx = abs(from.get_x() - to.get_x());
      int dy = abs(from.get_y() - to.get_y());
      if(dy == 1 && dx == 0) valid = true;
      if((from.get_y() == 1) || (from.get_y() == 6))
        if(dy == 2 && dx == 0) valid = true;
      return valid && super.is_valid(board, from, to);
    }

    @Override
    public char to_char(){
        return is_white? 'P' : 'p';
    }
}
