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

        Cord from = move.getFrom();
        Cord to = move.getTo();
        int dx = Math.abs(from.getFile() - to.getFile());
        int dy = Math.abs(from.getRank() - to.getRank());

        //checks for castle
        if(to.getRank() == 0 || to.getRank() == 7){
            if(to.getFile() == 6 || to.getFile() == 2){
                if(getColor()?game.getWhiteQueenCastle():game.getBlackQueenCastle()){
                    for(int i = from.getFile() - 1; i > 0; i--)
                        if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty)
                            return false;
                    return true;
                }
                else if(getColor()?game.getWhiteKingCastle():game.getBlackKingCastle()){
                    for(int i = from.getFile() + 1; i < game.getFileSize(); i++)
                        if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty)
                            return false;
                    return true;
                }
                else return false;
            }
        }

        if(dx > 1 || dy > 1) return false;
        return true;
    }

    private boolean castleLineOfSightWithLegal(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        int mod = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        for(int i = from.getFile() + mod; mod > 0? (i <= 6):(i >= 2); i += mod)
            if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty
                || GameHelper.sucide(game, new Move(from, new Cord(from.getRank(), i))))
                return false;
        return true;
    }

    private boolean castleLineOfSight(Game game, Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        int mod = to.getFile() - from.getFile() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        for(int i = from.getFile() + mod; mod > 0? (i <= 6):(i >= 2); i += mod)
            if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty)
                return false;
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
                    
                test = new Cord(from.getRank() + mod1, from.getFile() + mod2);
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }
            test = new Cord(from.getRank() + mod1, from.getFile());
            if(isValid(game, new Move(from, test)))
                moves.add(test);

            for(int k = 1; k < game.getFileSize(); k++){
                test = new Cord(from.getRank(), from.getFile() + (k * mod1));
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }
        }
        return moves;
    }


    
    @Override
    public char toCharacter(){
        return isWhite? 'K' : 'k';
    }
}
