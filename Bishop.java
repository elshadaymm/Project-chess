public class Bishop extends Piece{
    public Bishop(boolean white){
        super(Type.Bishop, white);
    }

    @Override
    public boolean isValid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if(dx == dy) valid = true;
        return valid && super.isValid(board, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'B' : 'b';
    }
}
