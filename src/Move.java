import java.util.ArrayList;

public class Move{
    private Cord from, to;
    private char promotion = '-';
    private double value = 0;

    public Move(Cord from, Cord to) {
        this.from = from;
        this.to = to;
    }

    public Move(Cord from, Cord to, double value){
        this(from, to);
        this.value = value;
    }

    public Move(Cord from, Cord to, char promotion){
        this(from, to);
        this.promotion = promotion;
    }

    public Move(double value){
        this.value = value;
    }

    public int dx(){return to.file() - from.file();}
    public int dy(){return to.rank() - from.rank();}

    public Cord from(){return from;}
    public Cord to(){return to;}

    //public Cord getFrom() {return from;}
    //public Cord getTo() {return to;}
    public char getPromotion() {return promotion;}
    public double getValue() {return value;}

    public void setValue(double n) {value = n;}
    
    public String toString(){
        return Converter.cordToUCI(from) + Converter.cordToUCI(to);
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