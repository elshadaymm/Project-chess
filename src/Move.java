import java.util.ArrayList;

public class Move{
    private Cord from, to;
    private double value = 0;

    public Move(Cord from, Cord to) {
        this.from = from;
        this.to = to;
    }

    public Move(Cord from, Cord to, double value){
        this(from, to);
        this.value = value;
    }

    public Move(double value){
        this.value = value;
    }

    public Move(Move other){
        from = other.from();
        to = other.to();
        value = other.getValue();
    }

    public int adx(){return Math.abs(dx());}//absolute value of delta x
    public int ady(){return Math.abs(dy());}//absolute value of delta y

    public int dx(){return to.file() - from.file();}
    public int dy(){return to.rank() - from.rank();}

    public Cord from(){return from;}
    public Cord to(){return to;}

    public double getValue() {return value;}

    public void setValue(double n) {value = n;}
    
    public String toString(){
        return Converter.cordToUCI(from) + Converter.cordToUCI(to) + to.getPromotion();
    }

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