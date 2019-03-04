import java.util.ArrayList;

public class AIMinMax extends Player{
    public AIMinMax(Game game) {super(game);}

    public void move(){
        makeMove(minMax());
    }

    public Move minMax(){return minMax(Constant.DEFAULT_MINMAX);}

    public Move minMax(final int depth){
        if(depth < 1) return null;
        ArrayList<Move> validMoves = game.allValidMoves();
        
        //was using for debugging, keep for now
        /*
        System.out.println();
        System.out.println("Calculating... Depth: " + depth + ", # of Legal Moves" + validMoves.size());
        game.printState();
        System.out.println(game.movesToString(validMoves));
        */

        if(depth == 1){
            for(Move move : validMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                move.setValue(tempGame.getAdvantage());
            }
            return game.getWhiteTurn()? max(validMoves) : min(validMoves);
        }else{
            for(Move move : validMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                AIMinMax tempAI = new AIMinMax(tempGame);
                move = tempAI.minMax(depth - 1);
            }
            return game.getWhiteTurn()? max(validMoves) : min(validMoves);
        }
    }

    public Move min(final ArrayList<Move> moves){
        Move min;
        if(moves.size() == 0) return null;
        min = moves.get(0);
        for(int i = 1; i < moves.size(); i++)
            if(moves.get(i).getValue() < min.getValue())
                min = moves.get(i);
        return min;
    }

    public Move max(final ArrayList<Move> moves){
        Move max;
        if(moves.size() == 0) return null;
        max= moves.get(0);
        for(int i = 1; i < moves.size(); i++)
            if(moves.get(i).getValue() > max.getValue())
                max = moves.get(i);
        return max;
    }
}