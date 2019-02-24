import java.util.Scanner;

public class Player{
    public Player() {}

    public void move(Game game){
        Scanner sc = new Scanner(System.in);
        String move;
        Cord from, to;
        System.out.print("Input Move of format \"a1a2\": ");
        while (true) {
            move = sc.next();
            from = new Cord(move.charAt(0) - 'a', Integer.parseInt("" + move.charAt(1)) - 1);
            to = new Cord(move.charAt(2) - 'a', Integer.parseInt("" + move.charAt(3)) - 1);
            System.out.println("UCI: " + from.get_x() + "" + from.get_y() + " " + to.get_x() + "" + to.get_y());
            if(game.valid_move(from, to)){
                game.move(from, to);
                game.change_turn();
                return;
            }else{
                System.out.println("Invalid Move: Enter move of format \"a1a2\": ");
            }
        }
    }
}