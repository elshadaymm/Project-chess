public class Main{
    public static void main(String[] args){
        Game game = new Game();
        game.print_state();
        
        Player white = new Player(true);
        Player black = new Player(false);

        while(true){
            white.move(game);
            game.print_state();
            black.move(game);
            game.print_state();
        }
    }
}