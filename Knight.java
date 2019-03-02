import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if(dx == 1 && dy == 2) valid = true;
        if(dx == 2 && dy == 1) valid = true;
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
        return isWhite? 'N' : 'n';
    }
}
