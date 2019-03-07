import java.util.ArrayList;
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
    private Cord enPassant;
    
    // Who's winning? white if its positive. black if its negative
    //Larger the value, more the game faves white
    private double advantage = 0;

    public Game(int rank, int file){
        rankSize = rank;
        fileSize = file;
        board = new Piece[rankSize][fileSize];
        nullBoard();
        setUpBoard();
    }

    public Game(){
        this(8,8);
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
                        copy = new Empty(cordColor(new Cord(i,j)));
                        break;
                }
                this.board[i][j] = copy;
            }
    }

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
                board[i][j] = new Empty(cordColor(new Cord(i,j)));
    }

    private boolean cordColor(Cord at){
        return (at.getRank() + at.getFile()) % 2 == 0? Constant.BLACK : Constant.WHITE;
    }

    public String toTurn(boolean white) {return white? "white" : "black";}

    //currently unused
    private boolean kingAlive(boolean color){
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++)
                if(board[i][j].getType() == Type.King && board[i][j].getColor() == color)
                    return true;
        return false;
    }

    private void update(){
        switch (inMate()){
            case Constant.CHECK:
                end = whiteTurn? Constant.BLACK_WIN : Constant.WHITE_WIN;
                advantage = whiteTurn? -Constant.THRESHOLD : Constant.THRESHOLD;
                return;
            case Constant.STALE:
                end = Constant.STALEMATE;
                advantage = 0;
                return;
            default:
                break;
        }
        
        if(peace >= 50){
            end = Constant.DRAW_BY_FIFTY_MOVE_RULE;
            return;
        }

        double sum = 0;
        Piece current;
        Cord at;
        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++){
                at = new Cord(i,j);
                current = getPiece(at);
                sum += current.getValue() * (current.getColor()? Constant.POSITIVE: Constant.NEGATIVE);
            }
        advantage = sum;
    }

    /*
    START OF GAME INTERFACE!!!!!!!!!!!!!!!
    */

    //@Jeremy impliment this function
    //!!!!!!!!!!!!
    public String FEN(){
        return "";
    }

    public int end(){
        return end;
    }

    //checks if the move is sucide/puting it self in check
    public boolean sucide(Move move){
        Game tempGame = new Game(this);
        tempGame.simpleMove(move);
        tempGame.changeTurn();
        ArrayList<Move> moves = tempGame.allValidMoves();
        for(Move nextMove : moves){
            if(tempGame.getPiece(nextMove.getTo()).getType() == Type.King)
                return true;
        }
        return false;
    }

    //checks if you are in checkmate
    public boolean checkmate(){
        if(!inCheck()) return false;
        ArrayList<Move> moves = allLegalMoves();
        return moves.size() == 0;
    }

    //checks if you are in stalemate
    public boolean stalemate(){
        if(inCheck()) return false;
        ArrayList<Move> moves = allLegalMoves();
        return moves.size() == 0;
    }

    public boolean inCheck(){
        Game tempGame = new Game(this);
        tempGame.changeTurn();
        ArrayList<Move> moves = tempGame.allValidMoves();
        for(Move nextMove : moves){
            if(tempGame.getPiece(nextMove.getTo()).getType() == Type.King)
                return true;
        }
        return false;
    }

    //0 for not in mate, 1 for checkmate, 2 for stale mate
    public int inMate(){
        ArrayList<Move> moves = allLegalMoves();
        if(moves.size() == 0){
            if(inCheck()) return Constant.CHECK;
            else return Constant.STALE;
        }
        return Constant.NO;
    }

    public void makeMove(Move move){
        if(checkmate()){
            System.out.println("Error: You've lost, the game should have ended, you shouldn't even be getting this message");
            return;
        }

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

        board[to.getFile()][to.getRank()] = getPiece(from);
        board[from.getFile()][from.getRank()] = new Empty(cordColor(from));

        changeTurn();
        update();
    }

    public void simpleMove(Move move){
        Cord from = move.getFrom();
        Cord to = move.getTo();
        board[to.getFile()][to.getRank()] = getPiece(from);
        board[from.getFile()][from.getRank()] = new Empty(cordColor(from));
    }

    public void changeTurn(){
        whiteTurn = whiteTurn ? false : true;
    }

    /**
     * Checks a pieces move rules to decide if a move is allowed.
     * @param from Take a coordinate value that the piece starts on
     * @param to Takes a coordinate value that the piece wants to move to
     * @return Invokes the .is_legal() method from the piece, which contains the pieces
     * move rules that are evaluated based on the x,y coordinates that the piece is on and can move to.
     * .is_legal() returns true if the piece is allowed to make that move, false otherwise
     */
    public boolean legalMove(Move move){
        if(end != Constant.ONGOING){
            System.out.println("Error: Game's Over");
            return false;
        }
        if(getPiece(move.getFrom()).getType() == Type.Empty){
            System.out.println("Error: Can't move an Empty Piece.");
            return false;
        }
        if(getPiece(move.getFrom()).getColor() != whiteTurn){
            System.out.println("Error: It's not " + toTurn(!whiteTurn) + "'s turn.");
            return false;
        }
        if(getPiece(move.getTo()).getType() != Type.Empty && getPiece(move.getFrom()).getColor() == getPiece(move.getTo()).getColor()){
            System.out.println("Error: Friendly Fire");   //example. white cant capture white, white can only capture black
            return false;
        }
        if(sucide(move)){
            System.out.println("Error: Can't put self in check.");
            return false;
        }
        return getPiece(move.getFrom()).isLegal(this, move);
    }

    /**
     * Function to determine which piece occupies this space on the board.
     * @param at Takes in a coordinate as a value
     * @return The piece at the coordinate provided
     */
    public Piece getPiece(Cord at){
        return board[at.getFile()][at.getRank()];
    }

    public boolean getWhiteTurn() {return whiteTurn;}
    public int getPeace() {return peace;}
    public Piece[][] getBoard() {return board;}
    public int getRankSize() {return rankSize;}
    public int getFileSize() {return fileSize;}
    public double getAdvantage() {return advantage;}
    public int getTurn() {return turn;}
    public int getEnd() {return end;}
    public boolean getWhiteKingCastle() {return whiteKingCastle;}
    public boolean getWhiteQueenCastle() {return whiteQueenCastle;}
    public boolean getBlackKingCastle() {return blackKingCastle;}
    public boolean getBlackQueenCastle() {return blackQueenCastle;}
    public Cord getEnPassant() {return enPassant;}

    public ArrayList<Move> allValidMoves(){
        ArrayList<Move> moves = new ArrayList<Move>();
        Cord from;
        ArrayList<Cord> current;

        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++){
                from = new Cord(i, j);
                current = getPiece(from).validMoves(this, from);
                for(Cord to : current)
                    moves.add(new Move(from, to));
            }
        
        return moves;
    }

    public ArrayList<Move> allLegalMoves(){
        ArrayList<Move> moves = new ArrayList<Move>();
        Cord from;
        ArrayList<Cord> current;

        for(int i = 0; i < rankSize; i++)
            for(int j = 0; j < fileSize; j++){
                from = new Cord(i, j);
                current = getPiece(from).legalMoves(this, from);
                for(Cord to : current)
                    moves.add(new Move(from, to));
            }
        
        return moves;
    }

    public String movesToString(final ArrayList<Move> moves){
        String string = "";

        for(Move move : moves){
            string = string + move.toString() + ", ";
        }

        if(string.length() != 0) string = string.substring(0, string.length() - 2);
        string = "{" + string + "}";
        return string;
    }

    public String allValidMovesToString(){
        return movesToString(allValidMoves());
    }

    public String allLegalMovesToString(){
        return movesToString(allLegalMoves());
    }
}