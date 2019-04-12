package game;

public class Constant{
    public static final int DEFAULT_RANK_SIZE = 8;
    public static final int DEFAULT_FILE_SIZE = 8;
    
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;

    //Values and meaning for the end variable in Game class
    public static final int ONGOING = 0;
    public static final int WHITE_WIN = 1;
    public static final int BLACK_WIN = 2;
    public static final int DRAW_BY_FIFTY_MOVE_RULE = 3;
    public static final int STALEMATE = 4;
    public static final int WHITE_TIMEOUT = 5;
    public static final int BLACK_TIMEOUT = 6;
    public static final int DRAW_BY_THREEFOLD_REPETITION = 7;

    //Used for inMate fuction in Game class
    public static final int NO = 0;
    public static final int CHECK = 1;
    public static final int STALE = 2;

    public static final double BISHOP_PREFERENCE = 0.1;//Preference of bishop over knight

    //default values of pieces
    public static final double KING_VALUE = 1000;
    public static final double QUEEN_VALUE = 9;
    public static final double PRINCESS_VALUE = 7;
    public static final double ROOK_VALUE = 5;
    public static final double BISHOP_VALUE = 3 + BISHOP_PREFERENCE;
    public static final double KNIGHT_VALUE = 3 - BISHOP_PREFERENCE;
    public static final double PAWN_VALUE = 1;

    //Scope is the pieces posiable movement squares / the squares it controls
    private static final double DEFAULT_SCOPE = 0.05;
    private static final double KING_MAX_SCOPE = 8;
    private static final double KNIGHT_MAX_SCOPE = 8;
    private static final double PAWN_MAX_SCOPE = 4;

    public static final double KING_SCOPE = 0.1 / KING_MAX_SCOPE;
    public static final double QUEEN_SCOPE = DEFAULT_SCOPE / 5;
    public static final double ROOK_SCOPE = DEFAULT_SCOPE;
    public static final double BISHOP_SCOPE = DEFAULT_SCOPE * 2;
    public static final double KNIGHT_SCOPE = 2 / KNIGHT_MAX_SCOPE;
    public static final double PAWN_SCOPE = 0.2 / PAWN_MAX_SCOPE;

    //safety of the king
    public static final double KING_NOT_SAFE = -0.5;
    public static final double KING_SAFE = 0.5;

    //pawn increse in value as they get to the end
    public static final double PAWN_PROGRESS = 0.2 / 6;

    //Threathhold fo the game lossing or winning
    public static final double THRESHOLD = KING_VALUE / 2;

    //default depth for ai
    public static final int DEFAULT_MINMAX = 2;
    public static final int DEFAULT_ALPHABETA = 2;
    
    // default time for each player, in milliseconds.
    public static int DEFAULT_TIME = 10 * 60 * 1000;
}