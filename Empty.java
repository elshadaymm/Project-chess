public class Empty extends Piece{
    public Empty(boolean white){
        super(Type.Empty, white);
    }

    @Override
    public char toCharacter(){
        return isWhite? '+' : '-';
    }
}
