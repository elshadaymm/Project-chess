public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    @Override
    public boolean isValid(Piece[][] board, Cord from, Cord to){
      boolean valid = false;
      int mod = getColor()? 1 : -1;
      int dx = abs(from.getX() - to.getX());
      int dy = mod * (to.getY() - from.getY());
      if(dy == 1 && dx == 0 && board[to.getY()][to.getX()].getType() == Type.Empty) valid = true;
      if(dy == 1 && dx == 1 && board[to.getY()][to.getX()].getType() != Type.Empty && board[to.getY()][to.getX()].getColor() != getColor()) valid = true;
      if((from.getY() == 1) || (from.getY() == 6))
        if(dy == 2 && dx == 0 && board[to.getY()][to.getX()].getType() == Type.Empty && board[to.getY() - mod][to.getX()].getType() == Type.Empty) valid = true;
      return valid && super.isValid(board, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }
}
