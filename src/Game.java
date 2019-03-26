/**
 * The Game class is the main control class for most of the chess game.  It creates the board,
 * sets up the board, and runs the main interface for the player.
 *
 */
import java.util.ArrayList;

public class Game{
    private int rankSize = Constant.DEFAULT_RANK_SIZE;//row
    private int fileSize = Constant.DEFAULT_FILE_SIZE;//col

    //in order of FEN
    private Piece[][] board;//the chess board. 2d array of piecese
    private boolean whiteTurn = true;
    private boolean whiteKingCastle = true;
    private boolean whiteQueenCastle = true;
    private boolean blackKingCastle = true;
    private boolean blackQueenCastle = true;
    private Cord enPassant = new Cord(-1, -1);
    private int peace = 0;  //the number of turns since the last capture or pawn advance. incriments at the end of black's turn. Used for the fifty-move rule 
    private int turn = 1; //the turn number, if its whites turn or not

    //extra info for the game
    private FischerClock clock = new FischerClock();//thes clock for the game
    private ArrayList<String> history = new ArrayList<String>();
    
    // Who's winning? white if its positive. black if its negative
    //Larger the value, more the game faves white
    private double advantage = 0;
    private int end = Constant.ONGOING; //if the game has ended. check the Constant class for detales

    public Game(int rank, int file){
        rankSize = rank;
        fileSize = file;
        board = new Piece[fileSize][rankSize];
        reset();
    }

    public Game(){this(Constant.DEFAULT_RANK_SIZE, Constant.DEFAULT_FILE_SIZE);}

    public Game(Game other){
        rankSize = other.getRankSize();
        fileSize = other.getFileSize();

        board = new Piece[rankSize][fileSize];
        setBoard(other.getBoard());
        whiteTurn = other.getWhiteTurn();
        whiteKingCastle = other.getWhiteKingCastle();
        whiteQueenCastle = other.getWhiteQueenCastle();
        blackKingCastle = other.getBlackKingCastle();
        blackQueenCastle = other.getBlackQueenCastle();
        enPassant = other.getEnPassant();
        peace = other.getPeace();
        turn = other.getTurn();

        advantage = other.getAdvantage();        
        end = other.getEnd();

        history = new ArrayList<String>();
        history.addAll(other.getHistory());
        history.add(GameInfo.FENBoard(this));

        clock = new FischerClock(other.getClock());
    }

    public void reset(){importGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");}

    //Sets the board using an FEN string
    public void importGame(String FEN){
        if(!GameHelper.FENFormat(FEN)) return;

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

        setInfo(info);

        clock = new FischerClock();

        history = new ArrayList<String>();
        history.add(GameInfo.FENBoard(this));

        update();
    }

    //copies a board
    public void setBoard(Piece[][] board){
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
                    case Princess:
                        copy = new Princess(original);
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

    public void update(){
        updateEnd();
        if(end == Constant.ONGOING) updateAdvantage();
    }

    public void makeMove(Move move){
    	if(clock.getWhiteTime() <= 0) {
            end = Constant.WHITE_TIMEOUT;
            advantage = -Constant.THRESHOLD;
            return;
        }
        if(clock.getBlackTime() <= 0) {
            end = Constant.BLACK_TIMEOUT;
            advantage = Constant.THRESHOLD;
            return;
        }
        move(move);
        clock.switchTurns();
    }

    /**
     * Start of the game interface.  This method moves the pieces around the board
     * and resets the space that the piece moved from to an empty space.
     * @param from The coordinate that the piece starts on
     * @param to The coordinate that the piece is moving to
     */
    public void move(Move move){
        Cord from = move.from();
        Cord to = move.to();
        if(getPiece(from).getColor() == Constant.BLACK){
            turn++;
        }

        peace++;
        if(getPiece(to).getType() != Type.Empty
            || getPiece(from).getType() == Type.Pawn) peace = 0;

        updateEnpassant(move);

        if(!updateCastle(move)){
            board[to.rank()][to.file()] = getPiece(from);
            board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
        }

        if(getPiece(to).getType() == Type.Pawn)
            updatePromotion(move);

        history.add(GameInfo.FENBoard(this));
        changeTurn();
        update();
    }

    //makes a simple move without updating anything
    public void simpleMove(Move move){
        Cord from = move.from();
        Cord to = move.to();
        board[to.rank()][to.file()] = getPiece(from);
        board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
    }

    //alternates turn
    public void changeTurn(){whiteTurn = whiteTurn ? false : true;}

    /**
     * Function to determine which piece occupies this space on the board.
     * @param at Takes in a coordinate as a value
     * @return The piece at the coordinate provided
     */
    public Piece getPiece(Cord at){return board[at.rank()][at.file()];}
    public Piece getPiece(int rank, int file){return board[rank][file];}

    public int getRankSize() {return rankSize;}
    public int getFileSize() {return fileSize;}

    //in order of FEN
    public Piece[][] getBoard() {return board;}
    public boolean getWhiteTurn() {return whiteTurn;}
    public boolean getWhiteKingCastle() {return whiteKingCastle;}
    public boolean getWhiteQueenCastle() {return whiteQueenCastle;}
    public boolean getBlackKingCastle() {return blackKingCastle;}
    public boolean getBlackQueenCastle() {return blackQueenCastle;}
    public Cord getEnPassant() {return enPassant;}
    public int getPeace() {return peace;}
    public int getTurn() {return turn;}

    public FischerClock getClock() {return clock;}
    public ArrayList<String> getHistory(){return history;}

    public double getAdvantage() {return advantage;}
    public int getEnd() {return end;}
    
    public void setWhiteTurn(boolean value){whiteTurn = value;}
    public void setWhiteKingCastle(boolean value) {whiteKingCastle = value;}
    public void setWhiteQueenCastle(boolean value) {whiteQueenCastle = value;}
    public void setBlackKingCastle(boolean value) {blackKingCastle = value;}
    public void setBlackQueenCastle(boolean value) {blackQueenCastle = value;}


    //implimed helper methods starts here!!
    private void setInfo(String info){
        String turn, castle, enPassant, halfMove, fullMove; 
        turn = info.substring(1, 2);//w or b
        info = info.substring(3);
        
        int cut = info.indexOf(" ");
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
            this.enPassant = Converter.stringToCord(enPassant);

        peace = Integer.parseInt(halfMove);
        this.turn = Integer.parseInt(fullMove);
    }

    //used by setBoard(String FEN)
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

    //used by setBoard(String FEN)
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
                case 'A': case 'a':
                    copy = new Princess(color);
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

    private boolean updateEnd(){
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

        if(GameHelper.threefoldRepetition(this)){
            end = Constant.DRAW_BY_THREEFOLD_REPETITION;
            advantage = 0;
            return true;
        }

        return false;
    }

    private void updateAdvantage(){
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

    private boolean updateCastle(Move move){
        Cord from = move.from();
        int dx = move.dx();
        boolean temp = false;//lazy logic will replace later
        boolean temp2 = getPiece(from).getColor();//again shit logic
        if(getPiece(from).getType() == Type.King){
            if(getPiece(from).getColor() == Constant.WHITE){
                if(dx == 2 && whiteKingCastle){
                    board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
                    board[0][7] = new Empty(GameHelper.cordColor(from));
                    board[0][6] = new King(Constant.WHITE);
                    board[0][5] = new Rook(Constant.WHITE);
                    temp = true;
                }else if(dx == -2 && whiteQueenCastle){
                    board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
                    board[0][0] = new Empty(GameHelper.cordColor(from));
                    board[0][2] = new King(Constant.WHITE);
                    board[0][3] = new Rook(Constant.WHITE);
                    temp = true;
                }
            }else{
                if(dx == 2 && blackKingCastle){
                    board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
                    board[7][7] = new Empty(GameHelper.cordColor(from));
                    board[7][6] = new King(Constant.BLACK);
                    board[7][5] = new Rook(Constant.BLACK);
                    temp = true;
                }else if(dx == -2 && blackQueenCastle){
                    board[from.rank()][from.file()] = new Empty(GameHelper.cordColor(from));
                    board[7][0] = new Empty(GameHelper.cordColor(from));
                    board[7][2] = new King(Constant.BLACK);
                    board[7][3] = new Rook(Constant.BLACK);
                    temp = true;
                }
            }
            if(temp2 == Constant.WHITE){
                whiteKingCastle = false;
                whiteQueenCastle = false;
            }else{
                blackKingCastle = false;
                blackQueenCastle = false;
            }
        }
    
        if(getPiece(from).getType() == Type.Rook){
            if(getPiece(from).getColor() == Constant.WHITE){
                if(whiteKingCastle)
                    whiteKingCastle = !GameHelper.rightOfKing(this, from);
                if(whiteQueenCastle)
                    whiteQueenCastle = !GameHelper.leftOfKing(this, from);
            }else{
                if(blackKingCastle)
                    blackKingCastle = !GameHelper.rightOfKing(this, from);
                if(blackQueenCastle)
                    blackQueenCastle = !GameHelper.leftOfKing(this, from);
            }
        }
        return temp;
    }

    private void updateEnpassant(Move move){
        Cord from = move.from();
        Cord to = move.to();
        //removes the enpassanted piece
        if(to.equals(this.enPassant))
            if(whiteTurn) board[to.rank() - 1][to.file()] = new Empty(GameHelper.cordColor(from));
            else board[to.rank() + 1][to.file()] = new Empty(GameHelper.cordColor(from));

        //updates the en passant variable to the coordinate of the square behind the pawn that has moved two squares from its starting position 
        enPassant = new Cord(-1, -1);
        if(getPiece(from).getType() == Type.Pawn){
            if(from.rank() == 1 && to.rank() == 3)
                enPassant = new Cord(2, from.file());
            else if(from.rank() == 6 && to.rank() == 4)
                enPassant = new Cord(5, from.file());
        }
    }

    private void updatePromotion(Move move){
        Cord to = move.to();
        boolean color = getPiece(to).getColor();
        if(to.rank() == 0 || to.rank() == rankSize - 1){
            Piece pro;
            switch (to.getPromotion()){
                case 'Q': case 'q': 
                    pro = new Queen(color);
                    break;
                case 'R': case 'r': 
                    pro = new Rook(color);
                    break;
                case 'B': case 'b': 
                    pro = new Bishop(color);
                    break;
                case 'N': case 'n': 
                    pro = new Knight(color);
                    break;
                default:
                    pro = new Queen(color);
                    break;
            }
            board[to.rank()][to.file()] = pro;
        }
    }
}