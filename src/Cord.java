/**
 * Coord controls the coordinates of the board.  They are created in x,y graph notation.
 * Standard setters (set_x(), set_y()) and getters (get_x() and get_y()) exist
 * as well as a method printCord()to print the coordinates to the terminal.
 */

public class Cord{
    private int rank, file;
    private char promotion = ' ';

     /**
     * Function to turn rank and file into Cord
     * @param rank x of the coordinate
     * @param file y of the coordinate
     */

    public Cord(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

    /**
     * Function to turn rank and file into Cord as well holds the char for pawn promotion
     * @param rank x of the coordinate
     * @param file y of the coordinate
     * @param p char for choosing what to turn pawn into during pawn promotion
     */

    public Cord(int rank, int file, char p){
        this(rank, file);
        promotion = p;
    }
    

    public Cord(Cord other){
        this(other.rank(), other.file(), other.getPromotion());
    }

    public Cord(Cord other, char promotion){
        this(other.rank(), other.file());
        this.promotion = promotion;
    }

    /**
     * Getter method the rank of the piece
     * @return rank of the piece
     */
    public int rank(){return rank;}

    /**
     * Getter method the file of the piece
     * @return file of the piece
     */
    public int file(){return file;}

    /**
     * Getter method for the char of promotion of the piece 
     * @return the char of the promotion
     */
    public char getPromotion(){return promotion;}

    /**
     * Setter method for promoting pawn
     * @param p the char that the pawn is promoting to
     */
    public void setPromotion(char p){promotion = p;}

    //public int rank(){return rank;}
    //public int file(){return file;}
    /**
     * Setter method that takes in the rank of a piece
     * @param rank x of the coordinate
     */
    public void setRank(int rank){this.rank = rank;}

    /**
     * Setter method that takes in the File of a piece
     * @param File y of the coordinate
     */

    public void setFile(int file){this.file = file;}

    /**
     * Function that checks if he rank and file of the piece being held equals the rank and file of the other piece
     * @param other other piece
     * @return true if the rank and file of the piece being held equals the rank and file of the other piece
     */

    public boolean equals(Cord other) {
        return this.rank() == other.rank() && this.file() == other.file();
    }
    /**
     * Function to convert cordoorinate of piece to UCI 
     * @return UCI as a string of the corrosponding coordinate
     */

    public String toString(){
        if(rank < 0 || file < 0) return "-";
        return Converter.cordToUCI(this);
    }
}
