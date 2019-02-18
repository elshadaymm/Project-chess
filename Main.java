public class Main{
    public static void main(String[] args){
        Game game = new Game();
        game.print_state();

        game.move(new Cord(1, 2), new Cord(3, 2));
        game.print_state();

        game.move(new Cord(6, 3), new Cord(4, 3));
        game.print_state();

        game.move(new Cord(3, 2), new Cord(4, 3));
        game.print_state();
    }
}