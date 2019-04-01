import java.util.Random;
import java.util.ArrayList;

enum Intelligence{
    Human, Random, MinMax, AlphaBeta
}

public abstract class Player{
    protected Game game;
    protected Intelligence kind = Intelligence.Random;
    public Player(Game game) {
        this.game = game;
    }

    /**
     * Makes a random move.  Used for the random AI to make moves.
     * Decides on the random move by getting the list of legal moves and randomly choosing one.
     * @return always returns true after making the random move.
     */
    public boolean move(){
        Random rand = new Random();
        ArrayList<Move> legalMoves = GameHelper.allLegalMoves(game);

        if(legalMoves.size() != 0)
            makeMove(legalMoves.get(rand.nextInt(legalMoves.size())));
        return true;
    }

    public boolean move(String move){
        return move();
    }

    /**
     * Takes the input of a move the player has made and passes it to the game.
     * @param move a valid move from the Move class
     */
    protected void makeMove(Move move){
        game.makeMove(move);
    }

    /**
     * Checks to make sure that the user input is a valid move.
     */
    protected boolean validInput(String str){
        if(str.length() < 4) 
            return false;

        if(str.charAt(0) < 'a' || str.charAt(0) > 'h'
            || str.charAt(1) < '0' || str.charAt(1) > '8'
            || str.charAt(2) < 'a' || str.charAt(2) > 'h'
            || str.charAt(3) < '0' || str.charAt(3) > '8')
            return false;
        
        return true;
    }

    /**
     * Checks to see if there are any possible moves.  If not, sets the winner of the game
     * by changing the constant to a winner.  Then finds the smallest value piece in the list of
     * available moves.
     */
    protected Move min(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);

        
        Move min = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() < min.getValue())
                min = moves.get(i);
        }
        return min;
    }

    /**
     * Checks to see if there are any possible moves. If not, sets the winner of the
     * game by changing the constant to a winner. Then finds the largest value
     * piece in the list of available moves.
     */
    protected Move max(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);

        
        Move max = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() > max.getValue())
                max = moves.get(i);
        }
        return max;
    }

    /**
     * @return the kind
     */
    public Intelligence getKind() {
        return kind;
    }
}
