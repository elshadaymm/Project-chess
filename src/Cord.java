/**
 * Coord controls the coordinates of the board.  They are created in x,y graph notation.
 * Standard setters (set_x(), set_y()) and getters (get_x() and get_y()) exist
 * as well as a method printCord()to print the coordinates to the terminal.
 */

public class Cord{
    private int rank, file;
    private char promotion = ' ';

    public Cord(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

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

    public int rank(){return rank;}
    public int file(){return file;}

    public char getPromotion(){return promotion;}
    public void setPromotion(char p){promotion = p;}

    //public int rank(){return rank;}
    //public int file(){return file;}
    
    public void setRank(int rank){this.rank = rank;}
    public void setFile(int file){this.file = file;}

    public boolean equals(Cord other) {
        return this.rank() == other.rank() && this.file() == other.file();
    }

    public String toString(){
        if(rank < 0 || file < 0) return "-";
        return Converter.cordToUCI(this);
    }
}
