import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
      boolean valid = false;
      Piece[][] board = game.getBoard();
      int mod = getColor()? 1 : -1;
      int dx = abs(from.getX() - to.getX());
      int dy = mod * (to.getY() - from.getY());
      if(dy == 1 && dx == 0 
        && board[to.getY()][to.getX()].getType() == Type.Empty) 
        valid = true;
      else if(dy == 1 && dx == 1 
        && board[to.getY()][to.getX()].getType() != Type.Empty 
        && board[to.getY()][to.getX()].getColor() != getColor()) 
        valid = true;
      else if((from.getY() == 1) || (from.getY() == 6))
        if(dy == 2 && dx == 0 
        && board[to.getY()][to.getX()].getType() == Type.Empty 
        && board[to.getY() - mod][to.getX()].getType() == Type.Empty) 
        valid = true;
      return valid && super.isValid(game, from, to);
    }

    @Override
    public void updateValue(){
        value = 1;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = 1;
        
        value = worth;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }
}
