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
        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());
        if(dx == dy){
            valid = true;
            int modX = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            int modY = to.getRank() - from.getRank() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modY, from.getFile() + i * modX)).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.BISHOP_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = Constant.BISHOP_VALUE;
        
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'B' : 'b';
    }
}
