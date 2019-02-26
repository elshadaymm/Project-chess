/**
 * Coord controls the coordinates of the board.  They are created in x,y graph notation.
 * Standard setters (set_x(), set_y()) and getters (get_x() and get_y()) exist
 * as well as a method printCord()to print the coordinates to the terminal.
 */

public class Cord{
    private int x, y;
    public Cord(int x, int y){
        setX(x);
        setY(y);
    }

    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public int getX(){return x;}
    public int getY(){return y;}

    public void printCord(){
        System.out.println("X: " + x + ", Y: " + y);
    }
}
