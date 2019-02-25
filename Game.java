    public class Game{
        public static int boardSize = 8;
        public static boolean white = true;
        public static boolean black = false;

        private boolean whiteTurn = true;
        private int peace = 0;

        private Piece[][] board = new Piece[boardSize][boardSize];
        public Game(){
            nullBoard();
            setUpBoard();
        }

        private void setUpBoard(){
            boolean side = white;
            board[0][0] = new Rook(side);
            board[0][1] = new Knight(side);
            board[0][2] = new Bishop(side);
            board[0][3] = new Queen(side);
            board[0][4] = new King(side);
            board[0][5] = new Bishop(side);
            board[0][6] = new Knight(side);
            board[0][7] = new Rook(side);
            for(int i = 0; i < boardSize; i++)
                board[1][i] = new Pawn(side);



            side = black;
            board[7][0] = new Rook(side);
            board[7][1] = new Knight(side);
            board[7][2] = new Bishop(side);
            board[7][3] = new Queen(side);
            board[7][4] = new King(side);
            board[7][5] = new Bishop(side);
            board[7][6] = new Knight(side);
            board[7][7] = new Rook(side);
            for(int i = 0; i < boardSize; i++)
                board[6][i] = new Pawn(side);
        }

        private void nullBoard(){
            for(int i = 0; i < boardSize; i++)
                for(int j = 0; j < boardSize; j++)
                    board[i][j] = new Empty(cordColor(new Cord(i,j)));
        }

        private boolean cordColor(Cord at){
            return (at.getX() + at.getY()) % 2 == 0? white : black;
        }
        
        private String toTurn(boolean white) {return white? "white" : "black";}

        //Interface starts here

        public Piece getPiece(Cord at){
            return board[at.getY()][at.getX()];
        }

        public void move(Cord from, Cord to){
            if(getPiece(from).getColor() == black) peace++;

            if(getPiece(to).getType() != Type.Empty
                || getPiece(from).getType() == Type.Pawn) peace = 0;

            board[to.getY()][to.getX()] = getPiece(from);
            board[from.getY()][from.getX()] = new Empty(cordColor(from));
        }

        public void changeTurn(){
            whiteTurn = whiteTurn ? false : true;
        }

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
                System.out.println("Error: Friendly Fire");
                return false;
            }
            return getPiece(from).isValid(this, from, to);
        }

        public boolean getTurn() {return whiteTurn;}

        public Piece[][] getBoard() {return board;}

        public int getBoardSize() {return boardSize;}

        public String allValidMoves(){
            Cord cord;
            String current;
            String moves = "";

            for(int i = 0; i < boardSize; i++)
                for(int j = 0; j < boardSize; j++){
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

        public void printState(){
            System.out.println();
            System.out.println("Fifty-move Rule: " + peace);
            System.out.println("Currently " + toTurn(whiteTurn) + "'s turn.");
            System.out.println();
            System.out.println("  a b c d e f g h");
            for(int i = boardSize - 1; i >= 0; i--){
                System.out.print(i + 1 + " ");
                for(int j = 0; j < boardSize; j++){
                    System.out.print(board[i][j].toCharacter() + " ");
                }
                System.out.print(i + 1 + " ");
                System.out.println();
            }
            System.out.println("  a b c d e f g h");
            System.out.println();
        }
    }
