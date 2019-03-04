public class Constant{
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;

    public static final double BISHOP_PREFERENCE = 0.1;//Preference of bishop over knight

    //default values of pieces
    public static final double KING_VALUE = 1100;
    public static final double QUEEN_VALUE = 9;
    public static final double ROOK_VALUE = 5;
    public static final double BISHOP_VALUE = 3 + BISHOP_PREFERENCE;
    public static final double KNIGHT_VALUE = 3 - BISHOP_PREFERENCE;
    public static final double PAWN_VALUE = 1;


    //default depth for minmax ai
    public static final int DEFAULT_MINMAX = 2;
}