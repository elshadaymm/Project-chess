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
        if(!super.isValid(game, move)) return false;

        Cord from = move.getFrom();
        Cord to = move.getTo();
        boolean valid = false;
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());
        if(dx == 1 && dy == 2) valid = true;
        if(dx == 2 && dy == 1) valid = true;
        return valid;
    }

    @Override
    public void updateValue(){
        value = Constant.KNIGHT_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.KNIGHT_VALUE;
        value += validMoves(game, at).size() * Constant.KNIGHT_SCOPE;
    }

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        if(game.getPiece(from).getType() == Type.Empty)
            return moves;
        
        for(int i = 0; i < game.getRankSize(); i++)
            for(int j = 0; j < game.getFileSize(); j++)
                if(isValid(game, new Move(from, new Cord(i, j))))
                    moves.add(new Cord(i, j));
        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'N' : 'n';
    }
}
