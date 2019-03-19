import java.util.ArrayList;

public class Move{
    private Cord from, to;
    private double value = 0;
    private boolean castle = false;

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

    public void setValue(double n) {value = n;}
    public double getValue() {return value;}

    public Cord getFrom() {return from;}
    public Cord getTo() {return to;}

    public boolean getCastle(){return castle;}
    public void setCastle(boolean ahhhhhhhhhhhh){castle = ahhhhhhhhhhhh;}
    
    public String toString(){
        return Converter.CordToUCI(from) + Converter.CordToUCI(to);
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