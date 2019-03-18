import javax.swing.Timer;
import java.util.TimerTask;
import java.time.Clock;
import java.awt.*;
import java.awt.event.*;

public class FischerClock{

    private int whiteTime = 120000; //2 minutes, could reference something in Constant?  or be set in GUI?
    private int blackTime = 120000; //2 minutes, could reference something in Constant?  or be set in GUI?
    private int increment = 12000; // in ms, default to 12 seconds
    private boolean currentPlayer = false; //starts as false so that switchTurns starts on white's turn    
    
    public FischerClock(){
    	Timer mechanism = new Timer(1000, tictoc);  //this is the part that takes time off every second
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
    	if(currentPlayer) { whiteTime += increment;
    	} else { blackTime += increment;
    	}
    }
    
    public String whiteTime() { //toString for display in the GUI
    	return (whiteTime / 1000) / 60 + ":" + (whiteTime / 1000) % 60;
    }
    
    public String blackTime() { //toString for display in the GUI
    	return (blackTime / 1000) / 60 + ":" + (blackTime / 1000) % 60;
    }
    
    
    ActionListener tictoc = new ActionListener() {  //this is what the timer runs every second, and this takes 1 second off the time
        public void actionPerformed(ActionEvent evt) {
            if(currentPlayer){whiteTime -= 1000;}
            else blackTime -= 1000;
            if(whiteTime <= 0) {Game.setEnd(2);} //ends the game when white runs out of time
            if(blackTime <= 0) {Game.setEnd(1);} //ends the game when black runs out of time
        }
    };
    
    
    public static void main(String[] args) {
    }
    
    
}