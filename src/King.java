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
        int dx = to.getFile() - from.getFile();
        int dy = to.getRank() - from.getRank();

        //checks for castle
        if(from.getFile() == 4 && (to.getRank() == 0 || to.getRank() == 7)){
            if(dx == -2 && getColor()?game.getWhiteQueenCastle():game.getBlackQueenCastle()){
                for(int i = from.getFile() - 1; i > 0; i--)
                    if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty)
                        return false;
                return true;
            }
            else if(dx == 2 && getColor()?game.getWhiteKingCastle():game.getBlackKingCastle()){
                for(int i = from.getFile() + 1; i < game.getFileSize() - 1; i++)
                    if(game.getPiece(new Cord(from.getRank(), i)).getType() != Type.Empty)
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
                    
                test = new Cord(from.getRank() + mod1, from.getFile() + mod2);
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }
            test = new Cord(from.getRank() + mod1, from.getFile());
            if(isValid(game, new Move(from, test)))
                moves.add(test);

            test = new Cord(from.getRank(), from.getFile() + mod1);
            if(isValid(game, new Move(from, test)))
                moves.add(test);

            test = new Cord(from.getRank(), from.getFile() + (mod1 * 2));
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
