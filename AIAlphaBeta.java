import java.util.ArrayList;

public class AIAlphaBeta extends Player{
    public AIAlphaBeta(Game game) {super(game);}

    @Override
    public void move(){
        Move move = alphaBeta();
        if(move != null) System.out.println("Move made: " + move.toString() + ", Debug: " + move.getValue());
        makeMove(move);
    }
    
    public Move alphaBeta(){return alphaBeta(Constant.DEFAULT_ALPHABETA);}

    public Move alphaBeta(int depth){
        return null;
    }

    public Move min(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);
        Move min = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() < min.getValue())
                min = moves.get(i);
        }
        return min;
    }

    public Move max(ArrayList<Move> moves){
        if(moves.size() == 0) return new Move(game.getWhiteTurn()? -Constant.THRESHOLD : Constant.THRESHOLD);
        Move max = moves.get(0);
        for(int i = 1; i < moves.size(); i++){
            if(moves.get(i).getValue() > max.getValue())
                max = moves.get(i);
        }
        return max;
    }
}