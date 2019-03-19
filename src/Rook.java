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
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());

        if(dx != 0 && dy != 0) return false;

        if (dy == 0){
            int mod = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < dx; i++)
                if(game.getPiece(new Cord(from.getRank(), from.getFile() + i * mod)).getType() != Type.Empty)
                    return false;
        }
        else if (dx == 0){ 
            int mod = to.getRank() - from.getRank() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < dy; i++)
                if(game.getPiece(new Cord(from.getRank() + i * mod, from.getFile())).getType() != Type.Empty)
                    return false;
        }
        return true;
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
        Cord test;

        for(int i = 0; i < 2; i++){
            int mod = i == 0 ? Constant.POSITIVE : Constant.NEGATIVE;

            for(int k = 1; k < game.getRankSize() || k < game.getFileSize(); k++){
                test = new Cord(from.getRank() + (k * mod), from.getFile());
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
                    
                test = new Cord(from.getRank(), from.getFile() + (k * mod));
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }
        }

        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
