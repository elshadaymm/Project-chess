/**
 * The Game class is the main control class for most of the chess game.  It creates the board,
 * sets up the board, and runs the main interface for the player.
 *
 */
public class Game{
    private int rankSize = Constant.DEFAULT_RANK_SIZE;//the row length.
    private int fileSize = Constant.DEFAULT_FILE_SIZE;//the column length.

    private Piece[][] board;//Where all the pieces are stored.

    private int turn = 1;
    private boolean whiteTurn = true;
    private int peace = 0;  //The number of turns since the last capture or pawn advance. increments at the end of black's turn. Used for the fifty-move rule 

    private int end = Constant.ONGOING;//Gets to know how the game is won.

    //@Jeremy @Elvis
    //used for castling, currently unused. also used for FEN.
    private boolean whiteKingCastle = true;
    private boolean whiteQueenCastle = true;
    private boolean blackKingCastle = true;
    private boolean blackQueenCastle = true;

    //@Jeremy @Elvis
    //shows the piece that can be en passented in the current turn
    //used for en passent, currently unused. also used for FEN
    private Cord enPassant = null;
    
    // Who's winning? white if it's positive(+), black if it's negative(-).
    //Larger the value, more the game favors the white.
    private double advantage = 0;

    // Creates the 2D array for the board and its pieces.
    public Game(int rank, int file){
        rankSize = rank;
        fileSize = file;
        board = new Piece[fileSize][rankSize];
        nullBoard();
        setUpBoard();
    }
    //Constructor
    public Game(){
        this(Constant.DEFAULT_RANK_SIZE, Constant.DEFAULT_FILE_SIZE);
    }

    //copies a game
    public Game(final Game game){
        rankSize = game.getRankSize();
        fileSize = game.getFileSize();

        board = new Piece[rankSize][fileSize];

        turn = game.getTurn();
        whiteTurn = game.getWhiteTurn();
        peace = game.getPeace();
        
        end = game.getEnd();

        whiteKingCastle = game.getWhiteKingCastle();
        whiteQueenCastle = game.getWhiteQueenCastle();
        blackKingCastle = game.getBlackKingCastle();
        blackQueenCastle = game.getBlackQueenCastle();

        enPassant = game.getEnPassant();
        
        advantage = game.getAdvantage();
        setBoard(game.getBoard());
    }

    //copies the board as well as the respective pieces.
    private void setBoard(final Piece[][] board){
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++){
                Piece original = board[i][j];
                Piece copy;
                switch (original.getType()){
                    case King:
                        copy = new King(original);
                        break;
                    case Queen:
                        copy = new Queen(original);
                        break;
                    case Rook:
                        copy = new Rook(original);
                        break;
                    case Bishop:
                        copy = new Bishop(original);
                        break;
                    case Knight:
                        copy = new Knight(original);
                        break;
                    case Pawn:
                        copy = new Pawn(original);
                        break;
                    default:
                        copy = new Empty(GameHelper.cordColor(new Cord(i,j)));
                        break;
                }
                this.board[i][j] = copy;
            }
    }

    //Sets up the default board with all the pieces on its position.
    private void setUpBoard(){
        boolean side = Constant.WHITE;
        board[0][0] = new Rook(side);
        board[0][1] = new Knight(side);
        board[0][2] = new Bishop(side);
        board[0][3] = new Queen(side);
        board[0][4] = new King(side);
        board[0][5] = new Bishop(side);
        board[0][6] = new Knight(side);
        board[0][7] = new Rook(side);
        for(int i = 0; i < fileSize; i++)
            board[1][i] = new Pawn(side);

        side = Constant.BLACK;
        board[7][0] = new Rook(side);
        board[7][1] = new Knight(side);
        board[7][2] = new Bishop(side);
        board[7][3] = new Queen(side);
        board[7][4] = new King(side);
        board[7][5] = new Bishop(side);
        board[7][6] = new Knight(side);
        board[7][7] = new Rook(side);
        for(int i = 0; i < fileSize; i++)
            board[6][i] = new Pawn(side);
    }

    /**
     * Creates the spaces on the board according to their color.
     * The bottom left corner is 0,0 and the top right is 7,7
    */
    private void nullBoard(){
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++)
                board[i][j] = new Empty(GameHelper.cordColor(new Cord(i,j)));
    }
    
    // Checks and updates whether either side has won. 
    public boolean updateEnd(){
        switch (GameHelper.inMate(this)){
            case Constant.CHECK:
                end = whiteTurn? Constant.BLACK_WIN : Constant.WHITE_WIN;
                advantage = whiteTurn? -Constant.THRESHOLD : Constant.THRESHOLD;
                return true;
            case Constant.STALE:
                end = Constant.STALEMATE;
                advantage = 0;
                return true;
            default:
                break;
        }
        if(!GameHelper.kingAlive(this, Constant.WHITE)){
            end = Constant.BLACK_WIN;
            advantage = -Constant.THRESHOLD;
            return true;
        }
        if(!GameHelper.kingAlive(this, Constant.BLACK)){
            end = Constant.WHITE_WIN;
            advantage = Constant.THRESHOLD;
            return true;
        }
        
        if(peace >= 50){
            end = Constant.DRAW_BY_FIFTY_MOVE_RULE;
            advantage = 0;
            return true;
        }

        return false;
    }

    // Updates and returns the Advantage. 
    public void updateAdvantage(){
        double sum = 0;
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++){
                Cord at = new Cord(i,j);
                Piece current = getPiece(at);
                current.updateValue(this, at);
                sum += current.getValue() * (current.getColor()? Constant.POSITIVE: Constant.NEGATIVE);
            }
        advantage = sum;
    }

    public void update(){
        if(!updateEnd())
            updateAdvantage();
    }

    /*
    START OF GAME INTERFACE!!!!!!!!!!!!!!!
    */

    //Making a move.
    public void makeMove(Move move){
        move(move);
    }

    /**
     * Start of the game interface.  This method moves the pieces around the board
     * and resets the space that the piece moved from to an empty space(in this case with an empty piece).
     * @param from The coordinate that the piece starts on
     * @param to The coordinate that the piece is moving to
     */
    public void move(Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
         
        if(getPiece(from).getColor() == Constant.BLACK){
            turn++;
            peace++;
        }
        
        //This checks for a piece that can be taken. If yes, then it sets peace to 0 again.
        if(getPiece(to).getType() != Type.Empty
            || getPiece(from).getType() == Type.Pawn) peace = 0;
        
        //updates the en passant variable with the coordinate of the square behind the pawn that moved two spaces.
        if(from.getRank() == 1 && to.getRank() == 3 && getPiece(from).getType() == Type.Pawn) {
        	Cord temp = new Cord(2, from.getFile());
        	enPassant = temp;
        } else if(from.getRank() == 6 && to.getRank() == 4 && getPiece(from).getType() == Type.Pawn) {
        	Cord temp = new Cord(5, from.getFile());
        	enPassant = temp;
        } else {enPassant = null;}

        board[to.getRank()][to.getFile()] = getPiece(from);
        board[from.getRank()][from.getFile()] = new Empty(GameHelper.cordColor(from));

        changeTurn();
        update();
    }

    // Lets you make a simple move.
    public void simpleMove(Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        board[to.getRank()][to.getFile()] = getPiece(from);
        board[from.getRank()][from.getFile()] = new Empty(GameHelper.cordColor(from));
    }
    
    // Helps in changing the turns.
    public void changeTurn(){
        whiteTurn = whiteTurn ? false : true;
    }

    /**
     * Function to determine which piece occupies this space on the board.
     * @param at Takes in a coordinate as a value
     * @return The piece at the coordinate provided
     */
    public Piece getPiece(Cord at){return board[at.getRank()][at.getFile()];}
    public Piece getPiece(int rank, int file){return board[rank][file];}

    /**
     * All the Getters and Setters.
     */
    public int getRankSize() {return rankSize;}
    public int getFileSize() {return fileSize;}

    public Piece[][] getBoard() {return board;}

    public int getTurn() {return turn;}
    public boolean getWhiteTurn() {return whiteTurn;}
    public int getPeace() {return peace;}
    
    public int getEnd() {return end;}

    public boolean getWhiteKingCastle() {return whiteKingCastle;}
    public void setWhiteKingCastle(boolean value) {whiteKingCastle = value;}
    public boolean getWhiteQueenCastle() {return whiteQueenCastle;}
    public void setWhiteQueenCastle(boolean value) {whiteQueenCastle = value;}
    public boolean getBlackKingCastle() {return blackKingCastle;}
    public void setBlackKingCastle(boolean value) {blackKingCastle = value;}
    public boolean getBlackQueenCastle() {return blackQueenCastle;}
    public void setBlackQueenCastle(boolean value) {blackQueenCastle = value;}

    public Cord getEnPassant() {return enPassant;}

    public double getAdvantage() {return advantage;}
}