import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if(dx == dy){
            valid = true;
            int modX = to.getX() - from.getX() > 0? 1 : -1;
            int modY = to.getY() - from.getY() > 0? 1 : -1;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getX() + i * modX, from.getY() + i * modY)).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, from, to);
    }

    @Override
    public void updateValue(){
        value = 3;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = 3;
        
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'B' : 'b';
    }
}
