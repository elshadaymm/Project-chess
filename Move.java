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

    public void setValue(double n) {value = n;}
    public double getValue() {return value;}

    public Cord getFrom() {return from;}
    public Cord getTo() {return to;}
    
    public String toString(){
        return Converter.CordToUCI(from) + Converter.CordToUCI(to);
    }
}