    public class Game{
        public static int Board_Size = 8;
        public static boolean white = true;
        public static boolean black = false;

        private Piece[][] board = new Piece[Board_Size][Board_Size];
        public Game(){
            null_board();
            setup_board();
        }

        private void setup_board(){
            boolean side = white;
            board[0][0] = new Rook(side);
            board[0][1] = new Knight(side);
            board[0][2] = new Bishop(side);
            board[0][3] = new Queen(side);
            board[0][4] = new King(side);
            board[0][5] = new Bishop(side);
            board[0][6] = new Knight(side);
            board[0][7] = new Rook(side);
            for(int i = 0; i < Board_Size; i++)
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
            for(int i = 0; i < Board_Size; i++)
                board[6][i] = new Pawn(side);
        }

        private void null_board(){
            for(int i = 0; i < Board_Size; i++)
                for(int j = 0; j < Board_Size; j++)
                    board[i][j] = new Empty();
        }

        //Interface starts here
        public void move(Cord from, Cord to){
            board[to.get_x()][to.get_y()] = board[from.get_x()][from.get_y()];
            board[from.get_x()][from.get_y()] = new Empty();
        }

        public void print_state(){
            System.out.println();
            for(int i = Board_Size - 1; i >= 0; i--){
                System.out.println("---------------");
                for(int j = 0; j < Board_Size; j++){
                    System.out.print(board[i][j].to_char() + "|");
                }
                System.out.println();
            }
            System.out.println();
        }
    }