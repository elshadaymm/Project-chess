package game.pieces;

import game.*;

import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(boolean white){
        super(Type.Pawn, white);
    }

    public Pawn(Piece piece){
        this(piece.getColor());
    }
     /**
     * Function to check the moves avaliable to the corrosponding piece(Pawn)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @param to coordinate variable of the pieces end position
     * @param dy value of delta y
     * @param adx absolute value of delta x
     * @param getEnpassant gets the enpassant position
     * @param getType gets the type of the piece on the square
     * @return an ArrayList of moves that the piece can make
     */
    @Override
    public boolean isValid(Game game, Move move){
      if(!super.isValid(game, move)) return false;

      Cord from = move.from();
      Cord to = move.to();
      Piece[][] board = game.getBoard();
      int mod = getColor()? Constant.POSITIVE : Constant.NEGATIVE;
      int adx = move.adx();//absoult value of delta x
      int dy = mod * move.dy();

      //moveing straight
      if(adx == 0 && board[to.rank()][to.file()].getType() == Type.Empty)
        if(dy == 1)
          return true;
        else if(dy == 2
          && (from.rank() == 1 || from.rank() == game.getRankSize() - 2)
          && board[to.rank() - mod][to.file()].getType() == Type.Empty)
          return true;

      //taking diag
      if(adx == 1 && dy == 1)
        if(game.getPiece(to).getType() != Type.Empty
          || to.equals(game.getEnPassant()))
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
      value += Constant.PAWN_PROGRESS * (isWhite? at.rank(): 7 - at.rank());
    }

    /**
     * Function to check the moves avaliable to the corrosponding piece(Pawn)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @return an ArrayList of moves that the piece can make
     */
    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        int mody = getColor()? Constant.POSITIVE : Constant.NEGATIVE;
        Cord test;
        
        test = new Cord(from.rank() + mody, from.file());
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.rank() + (2 * mody), from.file());
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.rank() + mody, from.file() + 1);
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        test = new Cord(from.rank() + mody, from.file() - 1);
        if(isValid(game, new Move(from, test)))
          moves.add(test);

        updatePromotion(game, moves);

        return moves;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'P' : 'p';
    }

    private void updatePromotion(Game game, ArrayList<Cord> moves){
      ArrayList<Cord> promotionMoves = new ArrayList<Cord>();
      int index = 0;
      while(index < moves.size()){
        if(moves.get(index).rank() == 0 || moves.get(index).rank() == game.getRankSize() - 1){
          promotionMoves.add(moves.get(index));
          moves.remove(index);
        }
        else
          index++;
      }
      
      for(Cord move : promotionMoves){
        moves.add(new Cord(move, isWhite?'Q':'q'));
        moves.add(new Cord(move, isWhite?'R':'r'));
        moves.add(new Cord(move, isWhite?'B':'b'));
        moves.add(new Cord(move, isWhite?'N':'n'));
      }
    }
}
