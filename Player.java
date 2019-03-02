import java.util.Random;
import java.util.ArrayList;

public class Player{
    protected Game game;
    public Player(Game game) {this.game = game;}

    //Player by default makes a random move by ai
    public void move(){
        Random rand = new Random();
        ArrayList<Move> validMoves = game.allValidMoves();
        makeMove(validMoves.get(rand.nextInt(validMoves.size())));
    }

    protected void makeMove(Cord from, Cord to){
        game.move(from, to);
        game.changeTurn();
    }

    protected void makeMove(Move move){
        game.move(move);
        game.changeTurn();
    }

    protected boolean validInput(String str){
        if(str.length() < 4 || str.length() > 5) return false;

        if(str.charAt(0) < 'a' || str.charAt(0) > 'h'
            || str.charAt(1) < '0' || str.charAt(1) > '8'
            || str.charAt(2) < 'a' || str.charAt(2) > 'h'
            || str.charAt(3) < '0' || str.charAt(3) > '8'){ return false;}
        
        return true;
    }
}
