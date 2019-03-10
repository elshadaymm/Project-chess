/**
 * Empty is a class of Piece.  It is used to tell the Game class that there is no piece in a square.
 * When the square is empty it will also tell the board if the square is white or black.
 *
 */

public class Empty extends Piece{
    public Empty(boolean white){
        super(Type.Empty, white);
    }

    @Override
    public char toCharacter(){
        return isWhite? '+' : '-';
    }
}
