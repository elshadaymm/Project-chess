import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if (dy == 0 && dx != 0){
            int mod = to.getX() - from.getX() > 0? 1 : -1;
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getX() + i * mod, from.getY())).getType() != Type.Empty)
                    valid = false;
        }
        else if (dx == 0 && dy != 0){ 
            int mod = to.getY() - from.getY() > 0? 1 : -1;
            valid = true;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getX(), from.getY() + i * mod)).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
