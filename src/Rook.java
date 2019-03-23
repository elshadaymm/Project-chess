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

        Cord from = move.from();
        Cord to = move.to();
        int adx = move.adx();//absolute value of delta x
        int ady = move.ady();

        if(adx != 0 && ady != 0) return false;

        if (ady == 0){
            int mod = to.file() - from.file() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < adx; i++)
                if(game.getPiece(new Cord(from.rank(), from.file() + i * mod)).getType() != Type.Empty)
                    return false;
        }
        else if (adx == 0){ 
            int mod = move.dy() > 0? Constant.POSITIVE : Constant.NEGATIVE;
            for(int i = 1; i < ady; i++)
                if(game.getPiece(new Cord(from.rank() + i * mod, from.file())).getType() != Type.Empty)
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
                test = new Cord(from.rank() + (k * mod), from.file());
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
                    
                test = new Cord(from.rank(), from.file() + (k * mod));
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
