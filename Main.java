/**
 * Main class to run the game.  Creates a new game, prints the game state, creates a new player
 * then alternates moves and prints the game state while there is no winner.
 *
 */
public class Main{
    public static void main(String[] args){
        int row = 8;//Change row and col to change the board size
        int col = 8;//As long as both values are >= 8

        Game game = new Game(row, col);
        game.printState();

        Player white, black;

        if(args.length == 2){
            switch (args[0]){
                case "Human": 
                    white = new Human(game);
                    break;
                case "Engine": 
                    white = new Engine(game);
                    break;
                default:
                    white = new AIRandom(game);
                    break;
            }
            switch (args[1]){
                case "Human": 
                    black = new Human(game);
                    break;
                case "Engine": 
                    black = new Engine(game);
                    break;
                default:
                    black = new AIRandom(game);
                    break;
            }
        }else{
            white = new Human(game);
            black = new Human(game);
        }

        while(!game.win()){
            white.move();
            game.printState();
            if(!game.win()){
                black.move();
                game.printState();
            }
        }

        System.out.println("Game Won");
    }
}
