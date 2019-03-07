public class GameHelper{
    /**
     * prints the state of the board using ascii characters to the terminal for the player to reference
     */
    public static void printState(Game game){
        printInfo(game);

        System.out.println();
        System.out.println("All Legal Moves: " + game.allLegalMovesToString());

        printBoard(game);
    }

    public static void printInfo(Game game){
        System.out.println();
        System.out.println("Turn " + game.getTurn() + ":");
        System.out.println("Fifty-move Rule: " + game.getPeace());
        System.out.println("Currently " + game.toTurn(game.getWhiteTurn()) + "'s turn.");
        System.out.printf("White's Advantage: %.2f\n", game.getAdvantage());
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
}