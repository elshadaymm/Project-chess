import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    public Knight(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        if(!super.isValid(game, move)) return false;

        int dx = Math.abs(move.dx());
        int dy = Math.abs(move.dy());
        if(dx == 1 && dy == 2) return true;
        if(dx == 2 && dy == 1) return true;
        return false;
    }

    @Override
    public void updateValue(){
        value = Constant.KNIGHT_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.KNIGHT_VALUE;
        value += validMoves(game, at).size() * Constant.KNIGHT_SCOPE;
    }

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        Cord test;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++){
                int modx = i == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
                int mody = j == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
                test = new Cord(from.rank() + modx, from.file() + (2 * mody));
                if(isValid(game, new Move(from, test)))
                    moves.add(test);

                test = new Cord(from.rank() + (2 * modx), from.file() + mody);
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }

        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'N' : 'n';
    }
}
