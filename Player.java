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
            //System.out.println("UCI: " + from.getX() + "" + from.getY() + " " + to.getX() + "" + to.getY());
            System.out.println();
            if(game.validMove(from, to)){
                game.move(from, to);
                game.changeTurn();
                return;
            }else{
                System.out.println("Invalid Move: Enter move of format \"a1a2\": ");
            }
        }
    }
}
