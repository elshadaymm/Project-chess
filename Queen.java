import java.util.ArrayList;

public class Queen extends Piece{
    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;

    public Queen(boolean white){
        super(Type.Queen, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getRank() - to.getRank());
        int dy = abs(from.getFile() - to.getFile());
        int modX = to.getRank() - from.getRank() > 0? POSITIVE : NEGATIVE;
        int modY = to.getFile() - from.getFile() > 0? POSITIVE : NEGATIVE;
        if(dx == dy){
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modX, from.getFile() + i * modY)).getType() != Type.Empty)
                    return false;
        }
        else if (dy == 0 && dx != 0){
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modX, from.getFile())).getType() != Type.Empty)
                    return false;
        }
        else if (dx == 0 && dy != 0){ 
            valid = true;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getRank(), from.getFile() + i * modY)).getType() != Type.Empty)
                    return false;
        }
        return valid && super.isValid(game, from, to);
    }

    @Override
    public void updateValue(){
        value = 9;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = 9;
        
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'Q' : 'q';
    }
}
