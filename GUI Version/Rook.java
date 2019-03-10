import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    public Rook(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());
        if (dy == 0 && dx != 0){
            int mod = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank(), from.getFile() + i * mod)).getType() != Type.Empty)
                    valid = false;
        }
        else if (dx == 0 && dy != 0){ 
            int mod = to.getRank() - from.getRank() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            valid = true;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getRank() + i * mod, from.getFile())).getType() != Type.Empty)
                    valid = false;
        }
        return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.ROOK_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = Constant.ROOK_VALUE;
        
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
