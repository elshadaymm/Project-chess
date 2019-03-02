import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getRank() - to.getRank());
        int dy = abs(from.getFile() - to.getFile());
        if(dx == dy){
            valid = true;
            int modX = to.getRank() - from.getRank() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            int modY = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modX, from.getFile() + i * modY)).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, from, to);
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
