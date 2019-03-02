import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getRank() - to.getRank());
        int dy = abs(from.getFile() - to.getFile());
        if (dy == 0 && dx != 0){
            int mod = to.getRank() - from.getRank() > 0? 1 : -1;
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * mod, from.getFile())).getType() != Type.Empty)
                    valid = false;
        }
        else if (dx == 0 && dy != 0){ 
            int mod = to.getFile() - from.getFile() > 0? 1 : -1;
            valid = true;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getRank(), from.getFile() + i * mod)).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, from, to);
    }

    @Override
    public void updateValue(){
        value = 5;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = 5;
        
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
