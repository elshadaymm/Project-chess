import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    public Knight(Piece piece){
        this(piece.getColor());
    }

    /**
     * Function to check if the move being made is following the rules of the corresponding piece(Knight)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param adx absolute value of delta x
     * @param ady absolute value of delta y
     * @return If move is valid
     */

    @Override
    public boolean isValid(Game game, Move move){
        if(!super.isValid(game, move)) return false;

        int adx = move.adx();
        int ady = move.ady();
        //Checks if knight moves one square horizontally and two squares vertically
        if(adx == 1 && ady == 2) return true;
        //Checks if knight moves two squares horizontally and one square vertically
        if(adx == 2 && ady == 1) return true;
        return false;
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

    /**
     * Function to check the moves avaliable to the corrosponding piece(Knight)
     * @param game game variable that stores the piece positions, accessed with game class getPiece()
     * @param from coordinate variable of the pieces starting position
     * @param getRankSize gets the row of the corrosponding piece
     * @param getFileSize gets the column of the corrosponding piece
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
                test = new Cord(from.rank() + modx, from.file() + (2 * mody));
                if(isValid(game, new Move(from, test)))
                    moves.add(test);

                test = new Cord(from.rank() + (2 * modx), from.file() + mody);
                if(isValid(game, new Move(from, test)))
                    moves.add(test);
            }

        return moves;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'N' : 'n';
    }
}
