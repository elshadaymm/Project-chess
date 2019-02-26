/**
 * Main class to run the game.  Creates a new game, prints the game state, creates a new player
 * then alternates moves and prints the game state while there is no winner.
 *
 */
public class Main{
    public static void main(String[] args){
        Game game = new Game();
        game.printState();

        Player wb = new Player();

        while(true){
            wb.move(game);
            game.printState();
        }
    }
}
