import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    public Bishop(Piece piece){
        this(piece.getColor());
    }

    /**
     * Function to check if the move being made is following the rules of the corresponding piece(Bishop)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @param dx value of delta x
     * @param dy value of delta y
     * @param adx absolute value of delta x
     * @param ady absolute value of delta y
     * @return If the move is valid
     */
    @Override
    public boolean isValid(Game game, Move move){
        if(!super.isValid(game, move)) return false;

        Cord from = move.from();
        int adx = move.adx();
        int ady = move.ady();

        if(adx != ady) return false;

        int modX = move.dx() > 0? Constant.POSITIVE : Constant.NEGATIVE;
        int modY = move.dy() > 0? Constant.POSITIVE : Constant.NEGATIVE;

        //Checks for Diagonal Movement if none returns false
        for(int i = 1; i < adx; i++)//dx == dy
            if(game.getPiece(new Cord(from.rank() + i * modY, from.file() + i * modX)).getType() != Type.Empty)
                return false;
        return true;
    }

    @Override
    public void updateValue(){
        value = Constant.BISHOP_VALUE;
    }

    @Override
    public void updateValue(Game game, Cord at){
        value = Constant.BISHOP_VALUE;
        value += validMoves(game, at).size() * Constant.BISHOP_SCOPE;
    }

    /**
     * Function to check the moves avaliable to the corrosponding piece(Bishop)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @return an ArrayList of moves that the piece can make
     */
    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        Cord test;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++){
                int modx = i == 0 ? Constant.POSITIVE : Constant.NEGATIVE;
                int mody = j == 0 ? Constant.POSITIVE : Constant.NEGATIVE;

                for(int k = 1; k < game.getRankSize() && k < game.getFileSize(); k++){
                    test = new Cord(from.rank() + (k *modx), from.file() + (k * mody));
                    if(isValid(game, new Move(from, test)))
                        moves.add(test);
                }
            }

        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'B' : 'b';
    }
}
