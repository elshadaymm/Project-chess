package ai;

import game.*;

import java.util.Scanner;

public class Human extends Player{
    public Human(Game game) {
        super(game);
        kind = Intelligence.Human;
    }

    /**
     * Function that takes in a move from input and checks if it is a legal move then moves the piece if it is.
     * @return true if conditions are met for the move
     */
    @Override
    public boolean move(){//throw errors instead of printing to console
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
            
            sc.close();

            move = move + " ";
            move = Converter.UCIToCord(move) + move.charAt(4);
            from = new Cord(Integer.parseInt("" + move.charAt(0)), Integer.parseInt("" + move.charAt(1)));
            to = new Cord(Integer.parseInt("" + move.charAt(2)), Integer.parseInt("" + move.charAt(3)), move.charAt(4));
            
            if(GameHelper.legalMove(game, new Move(from, to))){
                makeMove(new Move(from, to));
                return true;
            }else{
                System.out.print("Invalid Move: Enter new move: ");
            }
        }
    }
    /**
     * Function that checks if the input is valid and if it is a legal move
     * @return true if input is valid and legal.
     */
    @Override
    public boolean move(String move){
        if(!validInput(move)){
            System.out.println("invalid input");
            return false;
        }
        Cord from, to;
        move = move + " ";
        move = Converter.UCIToCord(move) + move.charAt(4);
        from = new Cord(Integer.parseInt("" + move.charAt(0)), Integer.parseInt("" + move.charAt(1)));
        to = new Cord(Integer.parseInt("" + move.charAt(2)), Integer.parseInt("" + move.charAt(3)), move.charAt(4));
        
        if(GameHelper.legalMove(game, new Move(from, to))){
            makeMove(new Move(from, to));
            return true;
        }
        return false;
    }
}