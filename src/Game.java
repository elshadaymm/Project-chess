/**
 * The Game class is the main control class for most of the chess game.  It creates the board,
 * sets up the board, and runs the main interface for the player.
 *
 */
public class Game{
    private int rankSize = Constant.DEFAULT_RANK_SIZE;//row
    private int fileSize = Constant.DEFAULT_FILE_SIZE;//col

    private Piece[][] board;

    private int turn = 1;
    private boolean whiteTurn = true;
    private int peace = 0;  //the number of turns since the last capture or pawn advance. incriments at the end of black's turn. Used for the fifty-move rule 

    private int end = Constant.ONGOING;

    //@Jeremy @Elvis
    //used for castleing, currently unused. also used for FEN
    private boolean whiteKingCastle = true;
    private boolean whiteQueenCastle = true;
    private boolean blackKingCastle = true;
    private boolean blackQueenCastle = true;

    //@Jeremy @Elvis
    //shows the piece that can be enpassented this turn
    //used for en passent, currently unused. also used for FEN
    private Cord enPassant = new Cord(-1, -1);
    
    // Who's winning? white if its positive. black if its negative
    //Larger the value, more the game faves white
    private double advantage = 0;

    private FischerClock chessClock;

    public Game(int rank, int file){
        rankSize = rank;
        fileSize = file;
        board = new Piece[fileSize][rankSize];
        nullBoard();
        setUpBoard();
    }

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

    private boolean FENFormat(String FEN){
        if(FEN.length() < 10 + rankSize - 1) return false;
        return true;
    }

    public void reset(){setBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");}

    public void setBoard(String FEN){
        if(!FENFormat(FEN)) return;

        while(FEN.charAt(0) == ' ')
            FEN = FEN.substring(1);

        int cut; 
        cut = FEN.indexOf(" ");
        String board = FEN.substring(0, cut);
        String info = FEN.substring(cut, FEN.length());
        for(int i = 0; i < rankSize - 1; i++){
            cut = board.indexOf("/");
            String rank = board.substring(0, cut);
            board = board.substring(cut + 1, board.length());
            setRank(rank, rankSize - 1 - i);
        }
        setRank(board, 0);

        String turn, castle, enPassant, halfMove, fullMove; 
        turn = info.substring(1, 2);//w or b
        info = info.substring(3);
        
        cut = info.indexOf(" ");
        castle = info.substring(0, cut);//KQkq or - or ect
        info = info.substring(cut + 1);

        cut = info.indexOf(" ");
        enPassant = info.substring(0, cut);
        info = info.substring(cut + 1);

        cut = info.indexOf(" ");
        halfMove = info.substring(0, cut);
        info = info.substring(cut + 1);

        fullMove = info;


        whiteTurn = turn.charAt(0) == 'w';
        setCastle(castle);

        if(enPassant.charAt(0) == '-')
            this.enPassant = new Cord(-1, -1);
        else
            this.enPassant = Converter.StringToCord(enPassant);

        peace = Integer.parseInt(halfMove);
        this.turn = Integer.parseInt(fullMove);

        update();
    }

    private void setCastle(String castle){
        whiteKingCastle = false;
        blackKingCastle = false;
        whiteQueenCastle = false;
        blackQueenCastle = false;
        for(int i = 0; i < castle.length(); i++){
            switch (castle.charAt(i)){
                case 'K':
                    whiteKingCastle = true;
                case 'k':
                    blackKingCastle = true;
                case 'Q':
                    whiteQueenCastle = true;
                case 'q':
                    blackQueenCastle = true;
                default:
                    break;
            }
        }
    }

    //used for setBord
    private void setRank(String rank, int atRank){
        int fileIndex = 0;
        for(int i = 0; i < rank.length(); i++){
            Piece copy;
            boolean color = 'A' <= rank.charAt(i) && rank.charAt(i) <= 'Z' ? Constant.WHITE : Constant.BLACK;
            switch (rank.charAt(i)){
                case 'K': case 'k':
                    copy = new King(color);
                    break;
                case 'Q': case 'q':
                    copy = new Queen(color);
                    break;
                case 'R': case 'r':
                    copy = new Rook(color);
                    break;
                case 'B': case 'b':
                    copy = new Bishop(color);
                    break;
                case 'N': case 'n':
                    copy = new Knight(color);
                    break;
                case 'P': case 'p':
                    copy = new Pawn(color);
                    break;
                default:
                    int num = Integer.parseInt("" + rank.charAt(i));
                    for(int j = 0; j < num - 1; j++){
                        copy = new Empty(GameHelper.cordColor(new Cord(atRank, fileIndex)));
                        board[atRank][fileIndex] = copy;
                        fileIndex++;
                    }
                    copy = new Empty(GameHelper.cordColor(new Cord(atRank, fileIndex)));
                    break;
            }
            board[atRank][fileIndex] = copy;
            fileIndex++;
        }
    }

    //copies a board
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

    //sets up the default board for chess
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
     * Creates the spaces on the board, the bottom left corner is 0,0 and the top right is 7,7
    */
    private void nullBoard(){
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++)
                board[i][j] = new Empty(GameHelper.cordColor(new Cord(i,j)));
    }

    public boolean updateEnd(){
        end = Constant.ONGOING;
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

    //To do
    public void makeMove(Move move){
        move(move);
    }

    /**
     * Start of the game interface.  This method moves the pieces around the board
     * and resets the space that the piece moved from to an empty space.
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

        if(getPiece(to).getType() != Type.Empty
            || getPiece(from).getType() == Type.Pawn) peace = 0;


        //removes the enpassanted piece
        if(to.equals(this.enPassant))
            if(whiteTurn) board[to.getRank() - 1][to.getFile()] = new Empty(GameHelper.cordColor(from));
            else board[to.getRank() + 1][to.getFile()] = new Empty(GameHelper.cordColor(from));

        //updates the en passant variable to the coordinate of the square behind the pawn that has moved two squares from its starting position 
        enPassant = new Cord(-1, -1);
        if(getPiece(from).getType() == Type.Pawn){
            if(from.getRank() == 1 && to.getRank() == 3)
                enPassant = new Cord(2, from.getFile());
            else if(from.getRank() == 6 && to.getRank() == 4)
                enPassant = new Cord(5, from.getFile());
        }

        board[to.getRank()][to.getFile()] = getPiece(from);
        board[from.getRank()][from.getFile()] = new Empty(GameHelper.cordColor(from));


        changeTurn();
        FischerClock.switchTurns();
        update();
    }

    public void simpleMove(Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        board[to.getRank()][to.getFile()] = getPiece(from);
        board[from.getRank()][from.getFile()] = new Empty(GameHelper.cordColor(from));
    }

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

    public int getRankSize() {return rankSize;}
    public int getFileSize() {return fileSize;}

    public Piece[][] getBoard() {return board;}

    public int getTurn() {return turn;}
    public boolean getWhiteTurn() {return whiteTurn;}
    public int getPeace() {return peace;}
    
    public int getEnd() {return end;}
    public int setEnd(int value) {this.end = value;}

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