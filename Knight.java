public class Knight extends Piece{
    public Knight(boolean white){
        super(Type.Knight, white);
    }

    @Override
    public boolean isValid(Piece[][] board, Cord from, Cord to){
        boolean valid = false;
        int dx = abs(from.getX() - to.getX());
        int dy = abs(from.getY() - to.getY());
        if(dx == 1 && dy == 2) valid = true;
        if(dx == 2 && dy == 1) valid = true;
        return valid && super.isValid(board, from, to);
    }

    @Override
    public char toCharacter(){
        return isWhite? 'N' : 'n';
    }
}
