/**
 * The Game class is the main control class for most of the chess game.  It creates the board,
 * sets up the board, and runs the main interface for the player.
 *
 */
    public class Game{
        private int rankSize = 8;//row
        private int fileSize = 8;//col

        private boolean whiteTurn = true;
        private int peace = 0;  //the number of turns since the last capture or pawn advance. incriments at the end of black's turn. Used for the fifty-move rule 
        
        // Who's winning? white if its positive. black if its negative
        //Larger the value, more the game faves white
        private double advantage = 0;

        private Piece[][] board;

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

        public Game(Game game){
            rankSize = game.getRankSize();
            fileSize = game.getFileSize();
            board = new Piece[rankSize][fileSize];
            this.whiteTurn = game.getTurn();
            this.peace = game.getPeace();
            this.advantage = game.getAdvantage();
            for(int i = 0; i < rankSize; i++)
                for(int j = 0; j < fileSize; j++)
                    this.board[i][j] = new Piece(game.getPiece(new Cord(i, j)));
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

        private String toTurn(boolean white) {return white? "white" : "black";}

        private boolean kingAlive(boolean color){
            for(int i = 0; i < rankSize; i++)
                for(int j = 0; j < fileSize; j++)
                    if(board[i][j].getType() == Type.King && board[i][j].getColor() == color)
                        return true;
            return false;
        }

        private void updateAdvantage(){
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

        public boolean win(){
            return !(kingAlive(Constant.WHITE) && kingAlive(Constant.BLACK));
        }

        /**
         * Start of the game interface.  This method moves the pieces around the board
         * and resets the space that the piece moved from to an empty space.
         * @param from The coordinate that the piece starts on
         * @param to The coordinate that the piece is moving to
         */
        public void move(Cord from, Cord to){
            if(getPiece(from).getColor() == Constant.BLACK) peace++;

            if(getPiece(to).getType() != Type.Empty
                || getPiece(from).getType() == Type.Pawn) peace = 0;

            board[to.getFile()][to.getRank()] = getPiece(from);
            board[from.getFile()][from.getRank()] = new Empty(cordColor(from));

            updateAdvantage();
        }

        public void changeTurn(){
            whiteTurn = whiteTurn ? false : true;
        }

        /**
         * Checks a pieces move rules to decide if a move is allowed.
         * @param from Take a coordinate value that the piece starts on
         * @param to Takes a coordinate value that the piece wants to move to
         * @return Invokes the .is_valid() method from the piece, which contains the pieces
         * move rules that are evaluated based on the x,y coordinates that the piece is on and can move to.
         * .is_valid() returns true if the piece is allowed to make that move, false otherwise
         */
        public boolean validMove(Cord from, Cord to){
            if(getPiece(from).getType() == Type.Empty){
                System.out.println("Error: Can't move an Empty Piece.");
                return false;
            }
            if(getPiece(from).getColor() != whiteTurn){
                System.out.println("Error: It's not " + toTurn(!whiteTurn) + "'s turn.");
                return false;
            }
            if(getPiece(to).getType() != Type.Empty && getPiece(from).getColor() == getPiece(to).getColor()){
                System.out.println("Error: Friendly Fire");   //need to comment what "Friendly Fire" is
                return false;
            }
            return getPiece(from).isValid(this, from, to);
        }

        /**
         * Function to determine which piece occupies this space on the board.
         * @param at Takes in a coordinate as a value
         * @return The piece at the coordinate provided
         */
        public Piece getPiece(Cord at){
            return board[at.getFile()][at.getRank()];
        }

        public boolean getTurn() {return whiteTurn;}
        public int getPeace() {return peace;}
        public Piece[][] getBoard() {return board;}
        public int getRankSize() {return rankSize;}
        public int getFileSize() {return fileSize;}
        public double getAdvantage() {return advantage;}

        public String allValidMoves(){
            Cord cord;
            String current;
            String moves = "";

            for(int i = 0; i < rankSize; i++)
                for(int j = 0; j < fileSize; j++){
                    cord = new Cord(i, j);
                    current = getPiece(cord).validMovesToString(this, cord);
                    if(current.length() != 0){
                        moves = moves + current + ", ";
                    }
                }
            if(moves.length() != 0) moves = moves.substring(0, moves.length() - 2);
            moves = "{" + moves + "}";
            return moves;
        }

        /**
         * prints the state of the board using ascii characters to the terminal for the player to reference
         */
        public void printState(){
            printInfo();

            System.out.println();
            System.out.println("All Legal Moves: " + allValidMoves());

            printBoard();
        }

        public void printInfo(){
            System.out.println();
            System.out.println("Fifty-move Rule: " + peace);
            System.out.println("Currently " + toTurn(whiteTurn) + "'s turn.");
            System.out.printf("White's Advantage: %.2f\n", advantage);
        }

        public void printBoard(){
            System.out.println();
            System.out.print(' ');
            for(int j = 0; j < fileSize; j++)
                System.out.print(" " + (char) ('a' + j));
            System.out.println();

            for(int i = rankSize - 1; i >= 0; i--){
                System.out.print(i + 1 + " ");
                for(int j = 0; j < fileSize; j++){
                    System.out.print(board[i][j].toCharacter() + " ");
                }
                System.out.print(i + 1 + " ");
                System.out.println();
            }
            
            System.out.print(' ');
            for(int j = 0; j < fileSize; j++)
                System.out.print(" " + (char) ('a' + j));

            System.out.println();
            System.out.println();
        }
    }
