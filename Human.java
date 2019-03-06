import java.util.Scanner;

public class Human extends Player{
    public Human(Game game) {super(game);}

    //makes a move from input
    @Override
    public void move(){
        Scanner sc = new Scanner(System.in);
        String move;
        Cord from, to;
        System.out.print("Input Move of Format \"a1h8\": ");
        while (true) {
            do{
                move = sc.next();
                System.out.println();
                if(!validInput(move))
                    System.out.print("Invalid Fromat: Enter move of format \"a1h8\": ");
            }while(!validInput(move));
            move = move + " ";
            move = Converter.UCIToCord(move) + move.charAt(4);
            from = new Cord(Integer.parseInt("" + move.charAt(1)), Integer.parseInt("" + move.charAt(0)));
            to = new Cord(Integer.parseInt("" + move.charAt(3)), Integer.parseInt("" + move.charAt(2)));
            
            if(move.length() == 5 && move.charAt(4) == '*'){
                makeMove(from, to);
                return;
            }else if(game.validMove(new Move(from, to))){
                makeMove(from, to);
                return;
            }else{
                System.out.print("Invalid Move: Enter new move: ");
            }
        }
    }
}