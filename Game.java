    public class Game{
        public static int Board_Size = 8;
        public static boolean white = true;
        public static boolean black = false;
        
        private boolean white_turn = true;

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
                    board[i][j] = new Empty(cord_color(new Cord(i,j)));
        }

        private boolean cord_color(Cord at){
            return (at.get_x() + at.get_y()) % 2 == 0? white : black;
        }

        private Piece get_piece(Cord at){
            return board[at.get_y()][at.get_x()];
        }

        //Interface starts here
        public void move(Cord from, Cord to){
            board[to.get_y()][to.get_x()] = board[from.get_y()][from.get_x()];
            board[from.get_y()][from.get_x()] = new Empty(cord_color(from));
        }

        public void change_turn(){
            white_turn = white_turn ? false : true;
        }

        public boolean valid_move(Cord from, Cord to){
            if(get_piece(from).is_valid(board, from, to))
                if(get_piece(from).get_color() == white_turn)
                    return true;
                else
                    System.out.println("It's " + get_turn() + "'s turn.");
            return false;
        }

        public String get_turn() {return get_turn(white_turn);}

        public String get_turn(boolean white) {return white? "white" : "black";}

        public void print_state(){
            System.out.println();
            System.out.println("White's turn: " + white_turn);
            System.out.println("  a b c d e f g h");
            for(int i = Board_Size - 1; i >= 0; i--){
                System.out.print(i + 1 + " ");
                for(int j = 0; j < Board_Size; j++){
                    System.out.print(board[i][j].to_char() + " ");
                }
                System.out.print(i + 1 + " ");
                System.out.println();
            }
            System.out.println("  a b c d e f g h");
            System.out.println();
        }
    }