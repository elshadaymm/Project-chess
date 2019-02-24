public class Rook extends Piece{
    public Rook(boolean white){
        super(Type.Rook, white);
    }

    @Override
    public boolean isValid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if (dy == 0 && dx != 0) valid = true;
        else if (dx == 0 && dy != 0) valid = true;
        return valid && super.isValid(board, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'R' : 'r';
    }
}
