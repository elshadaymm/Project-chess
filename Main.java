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
