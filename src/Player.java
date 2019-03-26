import java.util.Random;
import java.util.ArrayList;

enum Intelligence{
    Human, Random, MinMax, AlphaBeta
}

public class Player{
    protected Game game;
    protected Intelligence kind = Intelligence.Random;
    public Player(Game game) {
        this.game = game;
    }

    //Player by default makes a random move by ai
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

    protected void makeMove(Move move){
        game.makeMove(move);
    }

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

    //used for ai
    protected Move min(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);

        
        Move min = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() < min.getValue())
                min = moves.get(i);
        }
        return min;
        
        /*
        Random rand = new Random();
        ArrayList<Move> allMin = minMoves(moves);
        return allMin.get(rand.nextInt(allMin.size()));*/
    }

    //used for ai
    protected Move max(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);

        
        Move max = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() > max.getValue())
                max = moves.get(i);
        }
        return max;
        
        /*
        Random rand = new Random();
        ArrayList<Move> allMax = maxMoves(moves);
        return allMax.get(rand.nextInt(allMax.size()));*/
    }

    private ArrayList<Move> minMoves(ArrayList<Move> moves){
        ArrayList<Move> minMoves = new ArrayList<Move>();
        Move min;
        if(moves.size() == 0) return null;
        min = moves.get(0);
        minMoves.add(min);
        for(int i = 1; i < moves.size(); i++){
            Move current = moves.get(i);
            if(current.getValue() == min.getValue())
                minMoves.add(current);
            else if(current.getValue() < min.getValue()){
                min = current;
                minMoves.clear();
                minMoves.add(min);
            }
        }
        return minMoves;
    }

    private ArrayList<Move> maxMoves(ArrayList<Move> moves){
        ArrayList<Move> maxMoves = new ArrayList<Move>();
        Move max;
        if(moves.size() == 0) return null;
        max = moves.get(0);
        maxMoves.add(max);
        for(int i = 1; i < moves.size(); i++){
            Move current = moves.get(i);
            if(current.getValue() == max.getValue())
                maxMoves.add(current);
            else if(current.getValue() > max.getValue()){
                max = current;
                maxMoves.clear();
                maxMoves.add(max);
            }
        }
        return maxMoves;
    }

    /**
     * @return the kind
     */
    public Intelligence getKind() {
        return kind;
    }
}
