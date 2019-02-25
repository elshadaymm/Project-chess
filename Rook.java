import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    @Override
    public boolean isValid(Game game, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if (dy == 0 && dx != 0) valid = true;
        else if (dx == 0 && dy != 0) valid = true;
        return valid && super.isValid(game, from, to);
    }

    @Override
    public ArrayList<Cord> validMoves(Game game, Cord from){
        ArrayList<Cord> moves = new ArrayList<Cord>();
        if(game.getPiece(from).getType() == Type.Empty)
            return moves;
        
        for(int i = 0; i < game.getBoardSize(); i++)
            for(int j = 0; j < game.getBoardSize(); j++)
                if(isValid(game, from, new Cord(i, j)))
                    moves.add(new Cord(i, j));
        return moves;
    }

    @Override
    public String validMovesToString(Game game, Cord from){
        ArrayList<Cord> moves = validMoves(game, from);
        String movesToString = "";
        String fromString = Converter.CordToUCI(from);
        for(Cord cord : moves)
            movesToString = movesToString + fromString + Converter.CordToUCI(cord) + ", ";
        if(movesToString.length() != 0) movesToString = movesToString.substring(0, movesToString.length() - 2);
        return movesToString;
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
