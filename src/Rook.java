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
        if(!super.isValid(game, move)) return false;

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
        return valid;
    }

    @Override
    public void updateValue(){
        value = Constant.ROOK_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.ROOK_VALUE;
        value += validMoves(game, at).size() * Constant.ROOK_SCOPE;
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
        return isWhite? 'R' : 'r';
    }
}
