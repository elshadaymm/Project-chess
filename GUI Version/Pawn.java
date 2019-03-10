import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    public Pawn(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
      Cord from = move.getFrom();
      Cord to = move.getTo();
      boolean valid = false;
      Piece[][] board = game.getBoard();
      int mod = getColor()? 1 : -1;
      int dx = Math.abs(from.getFile() - to.getFile());
      int dy = mod * (to.getRank() - from.getRank());
      if(dy == 1 && dx == 0 
        && board[to.getRank()][to.getFile()].getType() == Type.Empty) 
          valid = true;
      else if(dy == 1 && dx == 1 
        && board[to.getRank()][to.getFile()].getType() != Type.Empty 
        && board[to.getRank()][to.getFile()].getColor() != getColor()) 
          valid = true;
      else if((from.getRank() == 1) || (from.getRank() == 6))
        if(dy == 2 && dx == 0 
        && board[to.getRank()][to.getFile()].getType() == Type.Empty 
        && board[to.getRank() - mod][to.getFile()].getType() == Type.Empty) 
          valid = true;
      else if(getColor() == Constant.WHITE && game.getEnPassant() != null   //en passant white
        && dy == 1
        && dx == 1
        && to == game.getEnPassant())
          valid = true;
      else if(getColor() == Constant.BLACK && game.getEnPassant() != null //en passant black
        && dy == 1
        && dx == 1
        && to == game.getEnPassant())
          valid = true;
      return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.PAWN_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = Constant.PAWN_VALUE;
        
        value = worth;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }
}
