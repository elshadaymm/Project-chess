public class Empty extends Piece{
    public Empty(boolean white){
        super(Type.Empty, white);
    }

    @Override
    public char to_char(){
        return is_white? '+' : '-';
    }
}