import java.util.ArrayList;

public class King extends Piece{
    public King(boolean white){
        super(Type.King, white);
    }

    public King(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());
        if(dx == 1 && dy <= 1) valid = true;
        if(dy == 1 && dx == 0) valid = true;
        return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.KING_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.KING_VALUE;
        value += validMoves(game, at).size() * Constant.KING_SCOPE;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'K' : 'k';
    }
}
