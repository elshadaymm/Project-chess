/**
 * this class returns info about the game
 * never modifies the game
 * all methods either print or return a string
 */

public class GameInfo{
    /**
     * prints the state of the board using ascii characters to the terminal for the player to reference
     */
    public static void printState(Game game){
        printInfo(game);

        System.out.println();
        System.out.println("All Legal Moves: " + Move.movesToString(GameHelper.allLegalMoves(game)));

        System.out.println();

        //printHistory(game);
        //System.out.println();
        
        printBoard(game);
    }

    public static void printInfo(Game game){
        System.out.println();
        System.out.println("Turn " + game.getTurn() + ":");
        System.out.println("Fifty-move Rule: " + game.getPeace());
        System.out.println("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn.");
        System.out.println("FEN: " + toFEN(game));
        System.out.println("Repetition: " + GameHelper.repetition(game));
    }

    public static void printBoard(Game game){
        System.out.println();
        System.out.print(' ');
        for(int j = 0; j < game.getFileSize(); j++)
            System.out.print(" " + (char) ('a' + j));
        System.out.println();

        for(int i = game.getRankSize() - 1; i >= 0; i--){
            System.out.print(i + 1 + " ");
            for(int j = 0; j < game.getFileSize(); j++){
                System.out.print(game.getBoard()[i][j].toCharacter() + " ");
            }
            System.out.print(i + 1 + " ");
            System.out.println();
        }

        System.out.print(' ');
        for(int j = 0; j < game.getFileSize(); j++)
            System.out.print(" " + (char) ('a' + j));

        System.out.println();
        System.out.println();
    }

    public static void printHistory(Game game){
        for(int i = 0; i < game.getHistory().size(); i++)
            System.out.println(game.getHistory().get(i));
    }
    public static String toFEN(Game game){
        return FENBoard(game) + FENInfo(game);
    }

    public static String FENBoard(Game game){
        StringBuilder gameState = new StringBuilder();
        for(int i = (game.getRankSize() - 1); i > -1; i--){
            StringBuilder rankState = new StringBuilder();
            rankState.setLength(0);
            int counter = 0;
            for(int j = 0; j < (game.getFileSize()); j++){
                char symbol = game.getBoard()[i][j].toCharacter();

                if ((symbol != '-') && (symbol != '+'))
                    rankState.append(symbol);
                else {
                    counter += 1;
                    if(j == 7){
                    rankState.append(counter);
                    counter = 0;
                    } else if ((game.getBoard()[i][j + 1].toCharacter() != '-') && (game.getBoard()[i][j + 1].toCharacter() != '+')) {
                    rankState.append(counter);
                    counter = 0;}
                }
            }

            gameState.append(rankState);
            if(i != 0)gameState.append('/');
        }
        return gameState.toString();
    }

    public static String FENInfo(Game game){
        StringBuilder gameState = new StringBuilder();
        gameState.append(game.getWhiteTurn()? " w " : " b " );

        if(game.getWhiteKingCastle()){gameState.append("K");}
        if(game.getWhiteQueenCastle()){gameState.append("Q");}
        if(game.getBlackKingCastle()){gameState.append("k");}
        if(game.getBlackQueenCastle()){gameState.append("q");}

        if(!game.getWhiteKingCastle() 
            && !game.getWhiteQueenCastle() 
            && !game.getBlackKingCastle() 
            && !game.getBlackQueenCastle()) {gameState.append("-");}

        gameState.append(" " + game.getEnPassant().toString() + " ");
            
        gameState.append(game.getPeace());
        gameState.append(" ");
        gameState.append(game.getTurn());

        return gameState.toString();
    }
}