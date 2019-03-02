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
      int dx = abs(from.getRank() - to.getRank());
      int dy = mod * (to.getFile() - from.getFile());
      if(dy == 1 && dx == 0 
        && board[to.getFile()][to.getRank()].getType() == Type.Empty) 
        valid = true;
      else if(dy == 1 && dx == 1 
        && board[to.getFile()][to.getRank()].getType() != Type.Empty 
        && board[to.getFile()][to.getRank()].getColor() != getColor()) 
        valid = true;
      else if((from.getFile() == 1) || (from.getFile() == 6))
        if(dy == 2 && dx == 0 
        && board[to.getFile()][to.getRank()].getType() == Type.Empty 
        && board[to.getFile() - mod][to.getRank()].getType() == Type.Empty) 
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
