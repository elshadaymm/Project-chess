public class Constant{
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;

    public static final double BISHOP_PREFERENCE = 0.1;//Preference of bishop over knight

    //default values of pieces
    public static final double KING_VALUE = 1000;
    public static final double QUEEN_VALUE = 9;
    public static final double ROOK_VALUE = 5;
    public static final double BISHOP_VALUE = 3 + BISHOP_PREFERENCE;
    public static final double KNIGHT_VALUE = 3 - BISHOP_PREFERENCE;
    public static final double PAWN_VALUE = 1;

    //Threathhold fo the game lossing or winning
    public static final double THRESHOLD = KING_VALUE / 2;

    //Values and meaning for the end variable in Game class
    public static final int ONGOING = 0;
    public static final int WHITE_WIN = 1;
    public static final int BLACK_WIN = 2;
    public static final int DRAW_BY_FIFTY_MOVE_RULE = 3;
    public static final int STALEMATE = 4;


    //default depth for minmax ai
    public static final int DEFAULT_MINMAX = 2;
}