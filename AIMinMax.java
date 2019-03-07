import java.util.Random;
import java.util.ArrayList;

public class AIMinMax extends Player{
    public AIMinMax(Game game) {super(game);}

    public void move(){
        Move move = minMax();
        if(move != null) System.out.println("Move made: " + move.toString() + ", Debug: " + move.getValue());
        makeMove(move);
    }

    public Move minMax(){return minMax(Constant.DEFAULT_MINMAX);}

    public Move minMax(final int depth){
        if(depth < 1) return null;
        ArrayList<Move> legalMoves = GameHelper.allLegalMoves(game);
        
        if(depth == 1){
            for(Move move : legalMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                move.setValue(tempGame.getAdvantage());
            }
        }else{
            for(Move move : legalMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                AIMinMax tempAI = new AIMinMax(tempGame);
                move.setValue(tempAI.minMax(depth - 1).getValue());
            }
        }
        return game.getWhiteTurn()? max(legalMoves) : min(legalMoves);
    }

    public Move min(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);
        Random rand = new Random();
        ArrayList<Move> allMin = minMoves(moves);
        return allMin.get(rand.nextInt(allMin.size()));
    }

    public Move max(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);
        Random rand = new Random();
        ArrayList<Move> allMax = maxMoves(moves);
        return allMax.get(rand.nextInt(allMax.size()));
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
}