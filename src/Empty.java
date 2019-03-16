/**
 * Empty is a class of Piece.  It is used to tell the Game class that there is no piece in a square.
 * When the square is empty it will also tell the board if the square is white or black.
 *
 */
import java.util.ArrayList;

public class Empty extends Piece{
    public Empty(boolean white){
        super(Type.Empty, white);
    }

    @Override
    public boolean isValid(Game game, Move move) { return false;}

    @Override
    public void updateValue(){value = 0;}

    @Override
    public void updateValue(Game game, Cord at) {value = 0;}

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){return new ArrayList<Cord>();}

    @Override
    public char toCharacter(){
        return isWhite? '+' : '-';
    }
}
