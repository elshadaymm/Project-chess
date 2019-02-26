import java.util.Scanner;

public class Player{
    public Player() {}

    public void move(Game game){
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
                game.move(from, to);
                game.changeTurn();
                return;
            }else if(game.validMove(from, to)){
                game.move(from, to);
                game.changeTurn();
                return;
            }else{
                System.out.print("Invalid Move: Enter new move: ");
            }
        }
    }

    private boolean validInput(String str){
        if(str.length() < 4 || str.length() > 5) return false;

        if(str.charAt(0) < 'a' || str.charAt(0) > 'h'
            || str.charAt(1) < '0' || str.charAt(1) > '8'
            || str.charAt(2) < 'a' || str.charAt(2) > 'h'
            || str.charAt(3) < '0' || str.charAt(3) > '8'){ return false;}
        
        return true;
    }
}
