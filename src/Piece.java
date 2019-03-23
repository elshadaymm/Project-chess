import java.util.ArrayList;

/**
 * This is the base class for all of the chess pieces (including empty)
 * It uses enum type to specify the types of pieces.
 */

enum Type{
    King, Queen, Rook,  //these should all be upper case I think (convention)?. <-- idk, google it and chacge it yourself if its not java conventions.
    Bishop, Knight, Pawn,
    Empty;
}

public abstract class Piece{
    protected boolean isWhite;
    private Type type;
    protected double value;

    public Piece(Type type, boolean white){
        isWhite = white;
        this.type = type;
        updateValue();
    }

    public Piece(){
        this(Type.Empty, false);
    }

    public Piece(Piece piece){
        this(piece.getType(), piece.getColor());
    }

    /**
     * Checks that the piece to be moved is the same colour as the player whose turn it is,
     * and that the piece moving is not moving to a square with a same of the same colour.
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @param to coordinate variable of the pieces end position
     *
     */
    public boolean isValid(Game game, Move move){
        if(move.from().rank() < 0//move this check to Move class
            || move.from().file() < 0
            || move.to().rank() < 0
            || move.to().file() < 0
            || move.from().rank() > game.getRankSize() - 1
            || move.from().file() > game.getFileSize() - 1
            || move.to().rank() > game.getRankSize() - 1
            || move.to().file() > game.getFileSize() - 1)
            return false;
        if(game.getPiece(move.from()) == null) 
            return false;
        if(game.getPiece(move.from()).getColor() != game.getWhiteTurn()) 
            return false;
        if(game.getPiece(move.to()).getType() != Type.Empty 
            && game.getPiece(move.from()).getColor() == game.getPiece(move.to()).getColor()) 
            return false;

        return true;
    }

    //returns all legal moves
    public ArrayList<Cord> legalMoves(Game game, Cord from){
        ArrayList<Cord> moves = validMoves(game, from);
        
        int index = 0;
        while(index < moves.size()){
            if(!isLegal(game, new Move(from, moves.get(index))))//shit logic
                moves.remove(index);
            else
                index++;
        }
        return moves;
    }

    //if a move is legal
    public boolean isLegal(Game game, Move move){
        if(move.getPromotion() == '*') return true;//cheat mode
        
        if(game.getPiece(move.from()).getType() == Type.King && move.dy() == 0){//shit logic
            if(move.dx() == 2){
                if(GameHelper.inCheck(game)) return false;
                if(GameHelper.sucide(game, new Move(move.from(), new Cord(move.to().rank(), move.to().file() - 1))))
                    return false;
            }else if(move.dx() == -2){
                if(GameHelper.inCheck(game)) return false;
                if(GameHelper.sucide(game, new Move(move.from(), new Cord(move.to().rank(), move.to().file() + 1))))
                    return false;
            }
        }

        if(GameHelper.sucide(game, move)) return false;
        return isValid(game, move);
    }

    public abstract void updateValue(); //Default value of a piece
    public abstract void updateValue(Game game, Cord at);

    //Returns all valid moves
    public abstract ArrayList<Cord> validMoves(Game game, Cord from);

    public boolean getColor() {return isWhite;}
    public Type getType() {return type;}
    public double getValue() {return value;}

    public String toString(){
        return "" + type + ", " + isWhite;
    }

    public char toCharacter(){
        return  ' ';
    }
}
