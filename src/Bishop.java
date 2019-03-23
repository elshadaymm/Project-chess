import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    public Bishop(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        if(!super.isValid(game, move)) return false;

        Cord from = move.from();
        Cord to = move.to();
        int dx = Math.abs(from.file() - to.file());
        int dy = Math.abs(from.rank() - to.rank());

        if(dx != dy) return false;

        int modX = to.file() - from.file() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        int modY = to.rank() - from.rank() > 0? Constant.POSITIVE : Constant.NEGATIVE;

        //dx == dy
        for(int i = 1; i < dx; i++)
            if(game.getPiece(new Cord(from.rank() + i * modY, from.file() + i * modX)).getType() != Type.Empty)
                return false;
        return true;
    }

    @Override
    public void updateValue(){
        value = Constant.BISHOP_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.BISHOP_VALUE;
        value += validMoves(game, at).size() * Constant.BISHOP_SCOPE;
    }

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        Cord test;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++){
                int modx = i == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
                int mody = j == 0 ? Constant.POSITIVE : Constant.NEGATIVE;

                for(int k = 1; k < game.getRankSize() && k < game.getFileSize(); k++){
                    test = new Cord(from.rank() + (k *modx), from.file() + (k * mody));
                    if(isValid(game, new Move(from, test)))
                        moves.add(test);
                }
            }

        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'B' : 'b';
    }
}
