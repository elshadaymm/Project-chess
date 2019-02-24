public class Queen extends Piece{
    public Queen(boolean white){
        super(Type.Queen, white);
    }

    @Override
    public boolean isValid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if(dx == dy) valid = true;
        else if(from.getX() == to.getX() && from.getY() != to.getY()) valid = true;
        else if(from.getY() == to.getY() && from.getX() != to.getX()) valid = true;
        return valid && super.isValid(board, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'Q' : 'q';
    }
}
