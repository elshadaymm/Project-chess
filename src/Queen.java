import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(boolean white){
        super(Type.Queen, white);
    }

    public Queen(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());
        int modX = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        int modY = to.getRank() - from.getRank() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        if(dx == dy){
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modY, from.getFile() + i * modX)).getType() != Type.Empty)
                    return false;
        }
        else if (dy == 0 && dx != 0){
            valid = true;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank(), from.getFile() + i * modX)).getType() != Type.Empty)
                    return false;
        }
        else if (dx == 0 && dy != 0){ 
            valid = true;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getRank() + i * modY, from.getFile())).getType() != Type.Empty)
                    return false;
        }
        return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.QUEEN_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.QUEEN_VALUE;
        value += validMoves(game, at).size() * Constant.QUEEN_SCOPE;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'Q' : 'q';
    }
}
