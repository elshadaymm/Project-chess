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
