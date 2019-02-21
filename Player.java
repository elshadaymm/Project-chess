import java.util.Scanner;

public class Player{
    private boolean is_white;
    public Player(boolean white) {is_white = white;}

    public void move(Game game){
        Scanner sc = new Scanner(System.in);
        String move;
        Cord from, to;
        do{
            System.out.print("Input Move of format \"a1a2\": ");
            move = sc.next();
            from = new Cord(Integer.parseInt("" + move.charAt(1)) - 1, move.charAt(0) - 'a');
            to = new Cord(Integer.parseInt("" + move.charAt(3)) - 1, move.charAt(2) - 'a');
        }while (!game.valid_move(is_white, from, to));
        game.move(from, to);
        game.change_turn();
    }
}