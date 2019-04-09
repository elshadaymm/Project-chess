import javax.swing.Timer;
import java.awt.event.*;
import java.lang.StringBuilder;

/**
 * Creates and runs the clock for the chess game.  Consists of instance variables that control the time available for
 * each player and the amount of time added after each move (called the "increment").  Uses a timer mechanism to remove
 * a second from the player whose current turn it is.  All time is measured in milliseconds.
 */

public class FischerClock{
    private static int defaultTime = 120000; // default time for each player, in milliseconds.
    private int whiteTime = defaultTime;
    private int blackTime = defaultTime; 
    private int increment = 5000; // in milliseconds, default to 12 seconds
    private boolean currentPlayer = true;   
    
    public FischerClock(){
    }

    public FischerClock(FischerClock other){ //copy constructor
        this();
        whiteTime = other.getWhiteTime();
        blackTime = other.getBlackTime();
        increment = other.getIncrement();
        currentPlayer = other.getCurrentPlayer();
    }

    /**
     * Starts the clock by creating a timer object called "mechanism" that runs every seconds (1000 milliseconds)
     * and calls the "tictoc" method to remove time from the instance variable containing the time left for the
     * current player
     */
    public void startClock(){
        Timer mechanism = new Timer(1000, tictoc);
        mechanism.start();
    }
    
    //setters and getters
    public int getWhiteTime() {return whiteTime;}
    public void setWhiteTime(int value) {this.whiteTime = value;}
    public int getBlackTime() {return blackTime;}
    public void setBlackTime(int value) {this.blackTime = value;}
    public int getIncrement() {return increment;}
    public void setIncrement(int value) {this.increment = value;}
    public boolean getCurrentPlayer() {return currentPlayer;}
    
    /**
     * Switches between the players and adds the increment to the player whose turn just finished.  This method
     * is called at the end of each player's turn.  
     */
    public void switchTurns() {
    	currentPlayer = !currentPlayer;	
    	if(!currentPlayer) { whiteTime += increment;}
    	else { blackTime += increment;}
    }
    
    /**
     * toString method to return the time for the white player as a regular digital time display
     * @return a string displaying time in the format "2:45"
     */
    public String whiteTime() {
    	String minutes = Integer.toString((whiteTime / 1000) / 60);
    	StringBuilder seconds = new StringBuilder();
    	int currentSeconds = (whiteTime / 1000) % 60;
    	seconds = (currentSeconds < 10) ? seconds.append("0" + currentSeconds) : seconds.append(currentSeconds);
    	return(minutes + ":" + seconds);
    }
    
    /**
     * toString method to return the time for the black player as a regular digital
     * time display
     * @return a string displaying time in the format "2:45"
     */
    public String blackTime() { //call this to display time remaining for black
    	String minutes = Integer.toString((blackTime / 1000) / 60);
    	StringBuilder seconds = new StringBuilder();
    	int currentSeconds = (blackTime / 1000) % 60;
    	seconds = (currentSeconds < 10) ? seconds.append("0" + currentSeconds) : seconds.append(currentSeconds);
    	return(minutes + ":" + seconds);
    }
    
    /**
     * This is what the timer ("mechanism") runs every second.  This ActionListener checks to see who the current player is and
     * removes a second (1000 milliseconds) from that player's time variable every time it is called
     */
    ActionListener tictoc = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(currentPlayer){
            	whiteTime -= 1000;
            } else {
            	blackTime -= 1000;
            }
        }
    };
    
    
}