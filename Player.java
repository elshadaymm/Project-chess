import java.util.Scanner;

public class Player{
    public Player() {}

    public void move(Game game){
        Scanner sc = new Scanner(System.in);
        String move;
        Cord from, to;
        System.out.println("All Legal Moves: " + game.allValidMoves());
        System.out.print("Input Move of Format \"a1a2\": ");
        while (true) {
            move = sc.next();
            move = Converter.UCIToCord(move);
            from = new Cord(Integer.parseInt("" + move.charAt(1)), Integer.parseInt("" + move.charAt(0)));
            to = new Cord(Integer.parseInt("" + move.charAt(3)), Integer.parseInt("" + move.charAt(2)));

            System.out.println();
            if(game.validMove(from, to)){
                game.move(from, to);
                game.changeTurn();
                return;
            }else{
                System.out.println("All Legal Moves: " + game.allValidMoves());
                System.out.println("Invalid Move: Enter move of format \"a1a2\": ");
            }
        }
    }
}
