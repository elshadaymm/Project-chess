import java.util.ArrayList;

public class AIAlphaBeta extends Player{
    public static int positions = 0;

    public AIAlphaBeta(Game game) {
        super(game);
        kind = Intelligence.AlphaBeta;
    }

    @Override
    public boolean move(){
        positions = 0;
        Move move = alphaBeta();
        if(move != null) System.out.println("Move made: " + move.toString() + ", Debug: " + move.getValue());
        if(GameHelper.legalMove(game, move))
            makeMove(move);
            System.out.println("AlphaBeta calculated " + positions + " positions at depth " + Constant.DEFAULT_ALPHABETA);
        return true;
    }
    
    public Move alphaBeta(){return alphaBeta(Constant.DEFAULT_ALPHABETA, Integer.MIN_VALUE, Integer.MAX_VALUE);}

    public Move alphaBeta(int depth, double alpha, double beta){
        ArrayList<Move> legalMoves = GameHelper.allLegalMoves(game);

        if(depth < 0) return null;
        if(depth == 0) return new Move(game.getAdvantage());
        if(depth == 1){
            if(game.getWhiteTurn()){
                double value = Integer.MIN_VALUE;
                for(Move move : legalMoves){
                    Game tempGame = new Game(game);
                    tempGame.move(move);
                    move.setValue(tempGame.getAdvantage());
                    value = Math.max(value, move.getValue());
                    alpha = Math.max(alpha, value);
                    positions++;
                    if(alpha >= beta)
                        break;
                }
                return max(legalMoves);
            }else{
                double value = Integer.MAX_VALUE;
                for(Move move : legalMoves){
                    Game tempGame = new Game(game);
                    tempGame.move(move);
                    move.setValue(tempGame.getAdvantage());
                    value = Math.min(value, move.getValue());
                    alpha = Math.min(alpha, value);
                    positions++;
                    if(alpha >= beta)
                        break;
                }
                return min(legalMoves);
            }
        }
        if(game.getWhiteTurn()){
            double value = Integer.MIN_VALUE;
            for(Move move : legalMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                AIAlphaBeta tempAI = new AIAlphaBeta(tempGame);
                move.setValue(tempAI.alphaBeta(depth - 1, alpha, beta).getValue());
                value = Math.max(value, move.getValue());
                alpha = Math.max(alpha, value);
                positions++;
                if(alpha >= beta)
                    break;
            }
            return max(legalMoves);
        }else{
            double value = Integer.MAX_VALUE;
            for(Move move : legalMoves){
                Game tempGame = new Game(game);
                tempGame.move(move);
                AIAlphaBeta tempAI = new AIAlphaBeta(tempGame);
                move.setValue(tempAI.alphaBeta(depth - 1, alpha, beta).getValue());
                value = Math.min(value, move.getValue());
                alpha = Math.min(alpha, value);
                positions++;
                if(alpha >= beta)
                    break;
            }
            return min(legalMoves);
        }
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