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
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        if(game.getPiece(from).getType() == Type.Empty)
            return moves;
        
        for(int i = 0; i < game.getBoardSize(); i++)
            for(int j = 0; j < game.getBoardSize(); j++)
                if(isValid(game, from, new Cord(i, j)))
                    moves.add(new Cord(i, j));
        return moves;
    }

    @Override
    public String validMovesToString(Game game, Cord from){
        ArrayList<Cord> moves = validMoves(game, from);
        String movesToString = "";
        String fromString = Converter.CordToUCI(from);
        for(Cord cord : moves)
            movesToString = movesToString + fromString + Converter.CordToUCI(cord) + ", ";
        if(movesToString.length() != 0) movesToString = movesToString.substring(0, movesToString.length() - 2);
        return movesToString;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }
}
