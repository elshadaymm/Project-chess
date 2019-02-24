public class Main{
    public static void main(String[] args){
        Game game = new Game();
        game.print_state();
        
        Player wb = new Player();

        while(true){
            wb.move(game);
            game.print_state();
        }
    }
}