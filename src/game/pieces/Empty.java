/**
 * Empty is a class of Piece.  It is used to tell the Game class that there is no piece in a square.
 * When the square is empty it will also tell the board if the square is white or black.
 *
 */
package game.pieces;

import game.*;

import java.util.ArrayList;

public class Empty extends Piece{
    public Empty(boolean white){
        super(Type.Empty, white);
    }
    /**
     * Function that checks if the move is valid always returns false because empty shouldn't move
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @return false shouldn't move
     */

    @Override
    public boolean isValid(Game game, Move move) { return false;}

    /**
     * Function that updates the value of empty to 0
     */
    @Override
    public void updateValue(){value = 0;}

    /**
     * Function that takes in game and where the piece at and sets the value to 0
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param at the coordinate variable of where the piece is currently at
     */

    @Override
    public void updateValue(Game game, Cord at) {value = 0;}

    /**
     * Function to check the moves avaliable, this will always be an empty arrayList
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @return an ArrayList of moves that the piece can make(empty)
     */

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){return new ArrayList<Cord>();}

    @Override
    public char toCharacter(){
        return isWhite? '+' : '-';
    }
}
