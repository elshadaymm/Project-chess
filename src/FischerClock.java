import javax.swing.Timer;
import java.awt.event.*;
import java.lang.StringBuilder;

public class FischerClock{
    private static int defaultTime = 120000; // temp instant variable will remove later
    private int whiteTime = defaultTime; //2 minutes, could reference something in Constant?  or be set in GUI?
    private int blackTime = defaultTime; //2 minutes, could reference something in Constant?  or be set in GUI?
    private int increment = 0; // in ms, default to 12 seconds
    private boolean currentPlayer = true;   
    
    public FischerClock(){
    	Timer mechanism = new Timer(1000, tictoc);  //this is the part that takes time off every second
    	mechanism.start();
    }

    public FischerClock(FischerClock other){
        this();
        whiteTime = other.getWhiteTime();
        blackTime = other.getBlackTime();
        increment = other.getIncrement();
        currentPlayer = other.getCurrentPlayer();
    }
    
    public int getWhiteTime() {return whiteTime;}
    public void setWhiteTime(int value) {this.whiteTime = value;}
    public int getBlackTime() {return blackTime;}
    public void setBlackTime(int value) {this.blackTime = value;}
    public int getIncrement() {return increment;}
    public void setIncrement(int value) {this.increment = value;}
    public boolean getCurrentPlayer() {return currentPlayer;}
    
    public void switchTurns() { //this method switches between the players and adds 12 seconds every turn
    	currentPlayer = !currentPlayer;	
    	if(currentPlayer) { whiteTime += increment;}
    	else { blackTime += increment;}
    }
    
    public String whiteTime() { //call this to display time remaining for white
    	String minutes = Integer.toString((whiteTime / 1000) / 60);
    	StringBuilder seconds = new StringBuilder();
    	int currentSeconds = (whiteTime / 1000) % 60;
    	seconds = (currentSeconds < 10) ? seconds.append("0" + currentSeconds) : seconds.append(currentSeconds);
    	return(minutes + ":" + seconds);
    }
    
    public String blackTime() { //call this to display time remaining for black
    	String minutes = Integer.toString((blackTime / 1000) / 60);
    	StringBuilder seconds = new StringBuilder();
    	int currentSeconds = (blackTime / 1000) % 60;
    	seconds = (currentSeconds < 10) ? seconds.append("0" + currentSeconds) : seconds.append(currentSeconds);
    	return(minutes + ":" + seconds);
    }
    
    
    ActionListener tictoc = new ActionListener() {  //this is what the timer runs every second, and this takes 1 second off the time
        public void actionPerformed(ActionEvent evt) {
            if(currentPlayer){
            	whiteTime -= 1000;
            } else {
            	blackTime -= 1000;
            }
        }
    };
    
    
}