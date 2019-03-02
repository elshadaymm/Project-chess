import java.util.ArrayList;

public class King extends Piece{
    public King(boolean white){
        super(Type.King, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getRank() - to.getRank());
        int dy = abs(from.getFile() - to.getFile());
        if(dx == 1 && dy <= 1) valid = true;
        if(dy == 1 && dx == 0) valid = true;
        return valid && super.isValid(game, from, to);
    }

    @Override
    public void updateValue(){
        value = 1100;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = 1100;
        
        value = worth;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'K' : 'k';
    }
}
