public class Move{
    private Cord from, to;
    public Move(Cord from, Cord to) {
        this.from = from;
        this.to = to;
    }

    public Cord getFrom() {return from;}
    public Cord getTo() {return to;}
}