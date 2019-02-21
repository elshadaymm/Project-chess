import java.util.Scanner;

public class Player{
    private boolean is_white;
    public Player(boolean white) {is_white = white;}

    public void move(Game game){
        Scanner sc = new Scanner(System.in);
        String pos1, pos2;
        Cord from, to;
        do{
            System.out.println("Input Move");
            pos1 = sc.next();
            pos2 = sc.next();
            from = new Cord(Integer.parseInt("" + pos1.charAt(1)) - 1, pos1.charAt(0) - 'a');
            to = new Cord(Integer.parseInt("" + pos2.charAt(1)) - 1, pos2.charAt(0) - 'a');
        }while (!game.valid_move(is_white, from, to));
        game.move(from, to);
        game.change_turn();
    }
}