public class Cord{
    private int x, y;
    public Cord(int x, int y){
        set_x(x);
        set_y(y);
    }

    public void set_x(int x){this.x = x;}
    public void set_y(int y){this.y = y;}

    public int get_x(){return x;}
    public int get_y(){return y;}
}