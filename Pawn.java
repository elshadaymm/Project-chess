public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    @Override
    public boolean is_valid(Piece[][] board, Cord from, Cord to){
      boolean valid = false;
      int mod = get_color()? 1 : -1;
      int dx = abs(from.get_x() - to.get_x());
      int dy = mod * (to.get_y() - from.get_y());
      if(dy == 1 && dx == 0 && board[to.get_y()][to.get_x()].get_type() == Type.Empty) valid = true;
      if(dy == 1 && dx == 1 && board[to.get_y()][to.get_x()].get_type() != Type.Empty && board[to.get_y()][to.get_x()].get_color() != get_color()) valid = true;
      if((from.get_y() == 1) || (from.get_y() == 6))
        if(dy == 2 && dx == 0 && board[to.get_y()][to.get_x()].get_type() == Type.Empty && board[to.get_y() - mod][to.get_x()].get_type() == Type.Empty) valid = true;
      return valid && super.is_valid(board, from, to);
    }

    @Override
    public char to_char(){
        return is_white? 'P' : 'p';
    }
}
