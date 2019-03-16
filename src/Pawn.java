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
      if(!super.isValid(game, move)) return false;

      Cord from = move.getFrom();
      Cord to = move.getTo();
      Piece[][] board = game.getBoard();
      int mod = getColor()? Constant.POSITIVE : Constant.NEGATIVE;
      int dx = Math.abs(from.getFile() - to.getFile());
      int dy = mod * (to.getRank() - from.getRank());

      //moveing straight
      if(dx == 0 && board[to.getRank()][to.getFile()].getType() == Type.Empty)
        if(dy == 1)
          return true;
        else if(dy == 2
          && (from.getRank() == 1 || from.getRank() == game.getRankSize() - 2)
          && board[to.getRank() - mod][to.getFile()].getType() == Type.Empty)
          return true;

      //taking diag
      if(dx == 1 && dy == 1)
        if(board[to.getRank()][to.getFile()].getType() != Type.Empty)
          return true;
        else if(game.getEnPassant() != null && to == game.getEnPassant())
          return true;

      return false;
    }

    @Override
    public void updateValue(){
        value = Constant.PAWN_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
      value = Constant.PAWN_VALUE;
      value += validMoves(game, at).size() * Constant.PAWN_SCOPE;
    }

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        int mody = getColor()? Constant.POSITIVE : Constant.NEGATIVE;
        Cord test;

        test = new Cord(from.getRank() + mody, from.getFile());
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.getRank() + (2 * mody), from.getFile());
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.getRank() + mody, from.getFile() + 1);
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.getRank() + mody, from.getFile() - 1);
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        return moves;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }
}
