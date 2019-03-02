/**
 * Coord controls the coordinates of the board.  They are created in x,y graph notation.
 * Standard setters (set_x(), set_y()) and getters (get_x() and get_y()) exist
 * as well as a method printCord()to print the coordinates to the terminal.
 */

public class Cord{
    private int rank, file;
    public Cord(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

    public void setRank(int rank){this.rank = rank;}
    public void setFile(int file){this.file = file;}

    public int getRank(){return rank;}
    public int getFile(){return file;}

    public String toString(){
        return Converter.CordToUCI(this);
    }
}
