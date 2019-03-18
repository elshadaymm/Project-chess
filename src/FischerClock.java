import javax.swing.Timer;
import java.util.TimerTask;
import java.time.Clock;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class FischerClock{

    private int whiteTime = 120000; //2 minutes
    private int blackTime = 120000; //2 minutes
    private int increment = 12000; // in ms, default to 12 seconds
    private boolean currentPlayer = true;
    
    public FischerClock(){
    	Timer mechanism = new Timer(1000, tictoc);
    }
    
    public int getWhiteTime() {return whiteTime;}
    public int getBlackTime() {return blackTime;}
    public int getIncrement() {return increment;}
    public boolean getCurrentPlayer() {return currentPlayer;}
    
    public void switchTurns() {
    	if(currentPlayer) { whiteTime += increment;
    	} else { blackTime += increment;
    	currentPlayer = !currentPlayer;	
    	}
    }
    
    
    ActionListener tictoc = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(currentPlayer){whiteTime -= 1000;}
            else blackTime -= 1000;
        }
    };
    
    public static void main(String[] args) {
    	FischerClock clock = new FischerClock();
    	boolean currentPlayer = true;
    }
    
    
}