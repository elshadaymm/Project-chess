package game;

import java.util.ArrayList;

public class Move{
    private Cord from, to;
    private double value = 0;
    /**
     * setter method that takes in the position of the piece moving and where it is moving to
     * @param from coordinate variable of the pieces starting position
     * @param to coordinate variable of the pieces ending position
     */

    public Move(Cord from, Cord to) {
        this.from = from;
        this.to = to;
    }

    /**
     * setter method that takes in the position of the piece moving and where it is moving to as well as the value of the piece
     * @param from coordinate variable of the pieces starting position
     * @param to coordinate variable of the pieces ending position
     * @param value the value of the piece
     */

    public Move(Cord from, Cord to, double value){
        this(from, to);
        this.value = value;
    }

    /**
     * setter method that takes in value of the piece
     * @param value the value of the piece
     */

    public Move(double value){
        this.value = value;
    }


    public Move(Move other){
        from = other.from();
        to = other.to();
        value = other.getValue();

    }

    /**
     * getter method for the absolute value of delta x
     * @return absolute value of delta x
     */

    public int adx(){return Math.abs(dx());}

    /**
     * getter method for the absolute value of delta y
     * @return absolute value of delta y
     */

    public int ady(){return Math.abs(dy());}

     /**
     * getter method for the value of delta x
     * @return value of delta x
     */

    public int dx(){return to.file() - from.file();}
    
    /**
     * getter method for the value of delta y
     * @return value of delta y
     */
    public int dy(){return to.rank() - from.rank();}

    /**
     * getter method for Cord variable from
     * @return coordinate variable of the pieces starting position
     */

    public Cord from(){return from;}

    /**
     * getter method for Cord variable to
     * @return coordinate variable of the pieces ending position
     */
    public Cord to(){return to;}

    
    /**
     * getter method for the value of the piece
     * @return the value of the piece being moved
     */
    public double getValue() {return value;}

    /**
     * setter method for the value of the piece
     */
    public void setValue(double n) {value = n;}
    
     /**
     * Function that converts from and to variables to UCI and combines them with promotion char
     * @return UCI of from and to variable and char of promotion combined into one String 
     */
    public String toString(){
        return Converter.cordToUCI(from) + Converter.cordToUCI(to) + to.getPromotion();
    }

    /**
     * Function that converts moves from an ArrayList to a String
     * @param moves ArrayList of moves made
     * @return String of all moves made
     */

    public static String movesToString(ArrayList<Move> moves){
        String string = "";

        for(Move move : moves){
            string = string + move.toString() + ", ";
        }

        if(string.length() != 0) string = string.substring(0, string.length() - 2);
        string = "{" + string + "}";
        return string;
    }
}