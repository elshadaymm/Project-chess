import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    public Knight(Piece piece){
        this(piece.getColor());
    }

    @Override
    public boolean isValid(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getRank() - to.getRank());
        int dy = Math.abs(from.getFile() - to.getFile());
        if(dx == 1 && dy == 2) valid = true;
        if(dx == 2 && dy == 1) valid = true;
        return valid && super.isValid(game, move);
    }

    @Override
    public void updateValue(){
        value = Constant.KNIGHT_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        double worth = Constant.KNIGHT_VALUE;
        value = worth;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'N' : 'n';
    }
}
