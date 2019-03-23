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
        if(!super.isValid(game, move)) return false;

        Cord from = move.from();
        Cord to = move.to();
        int dx = move.dx();
        int dy = move.dy();

        //checks for castle
        if(from.file() == 4 && (to.rank() == 0 || to.rank() == 7)){
            if(dx == -2 && getColor()?game.getWhiteQueenCastle():game.getBlackQueenCastle()){
                for(int i = from.file() - 1; i >= 1; i--)
                    if(game.getPiece(new Cord(from.rank(), i)).getType() != Type.Empty)
                        return false;
                return true;
            }
            else if(dx == 2 && getColor()?game.getWhiteKingCastle():game.getBlackKingCastle()){
                for(int i = from.file() + 1; i <= game.getFileSize() - 2; i++)
                    if(game.getPiece(new Cord(from.rank(), i)).getType() != Type.Empty)
                        return false;
                return true;
            }
        }

        if(Math.abs(dx) > 1 || Math.abs(dy) > 1) return false;
        return true;
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
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        Cord test;

        for(int i = 0; i < 2; i++){
            int mod1 = i == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
            for(int j = 0; j < 2; j++){
                int mod2 = j == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
                    
                test = new Cord(from.rank() + mod1, from.file() + mod2);
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }
            test = new Cord(from.rank() + mod1, from.file());
            if(isValid(game, new Move(from, test)))
                moves.add(test);

            test = new Cord(from.rank(), from.file() + mod1);
            if(isValid(game, new Move(from, test)))
                moves.add(test);

            test = new Cord(from.rank(), from.file() + (mod1 * 2));
            if(isValid(game, new Move(from, test)))
                moves.add(test);
        }
        return moves;
    }
    
    @Override
    public char toCharacter(){
        return isWhite? 'K' : 'k';
    }
}