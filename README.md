# Chess
Welcome!  Chess is a centuries old two player strategic turn based board game where each player attempts to outwit their opponent using six different types of pieces.  This is a fully functional chess game with complete rules and extra features that will allow you to play against either another human or against a computer.  The features of the game allow you to test your skills by setting up custom board positions and playing against different computer players to learn better tactics and strategy.

*current version: 1.0 (final submission)*

# How To Play Chess
An introduction to the basic setup and rules of chess can be found [here](https://www.chess.com/learn-how-to-play-chess).

# Getting Started
The game's current version uses a GUI to control the game.  In order to play, make sure you've done the following:
  1. Make sure your computer has Java and the JavaFX library installed.
  2. Navigate to the /src folder in the chess program and open the runGUI.bat file.  This will compile the game and execute both the graphical user interface (hereafter GUI) and console based versions of the game currently.
  3. The main menu will load, from which you can select your game mode and begin!
  
# Interface
The program uses a GUI to play the game.  There are two ways to move pieces in the game.  The first is to click and hold on the piece you want to move which will then update the board the show possible moves in green.  You then move the mouse to the space you want to move to and release to make the move.  The second way to move is to manually input your moves in the box lablled "Input Move of Format 'a1h8':" and then clicking the submit button to make the move.  The input format take the starting position of the piece as noted using the labels around the edge of the board.  For example, to move the left most pawn for the white player two spaces you would input "a2a4" and click "Turn."  The game currently enforces the rules of chess, so you cannot make an illegal move.  

### Information Displayed
The state of the game is displayed on the top of the screen, and will display "Ongoing" as long as the game can continue.  Once the game is finished, it will change and tell you why the game is over.  On the right side of the screen you will see the chess clock displaying the time left for each player to play.  When you submit your move extra time will be added and the other's player clock will start counting down until their move is submitted, according to the rules of a Fischer Clock.  A display under the clock also shows information about the game including: which colour is currently playing, game turn, a counter for the fifty-move rule, the current Forsyth-Edwards Notation (hereafter FEN) state for the board, a static timer showing the current time remaining when the turn started, and the maximum number of repitions for the threefold repition move.  There are also a number of buttons under this.

### Buttons
  - The first long input box allows you to load a game from a FEN string manually by pasting the string into the box and clicking "Load From FEN."  
  - "New Game" resets the board and clock to start a new game.  
  - "New Fischer Random" will generate a new board according to the rules of [Fischer Random Chess](https://en.wikipedia.org/wiki/Chess960).
  - "Save Game" will allow the player to save the game as a .chess file anywhere on their computer, and "Load Game" will allow the player to load a game from a .chess file. 
  - "Reset Game" currently bring the player back to the main menu.


# Version History

### Version 0.1 (Demo 1)
  - Text based interface in the console
  - Basic move rules being enforced
  - Checkmate and check not working
  - Cheat mode to make illegal moves (for debugging)
  - Error messages displayed for illegal and invalid moves
  - AutoHotKeys script for debugging
  
### Version 0.2 (Demo 2)
  - First working GUI using JavaFX and text based input
  - Three working AI players (random, minmax, minmax with alpha-beta pruning)
  - Checkmate and Stalemate implemented
  - FEN recorded for each move
  - General architecture improvements (constants moved to constant class, main Game file split in two)
  
### Version 0.3 (Demo 3)
  - Import/export for the game state
  - Save/Load game implemented
  - Comprehensive JUnit tests for the pieces (through the Piece class)
  - All remaining rules implemented (threefold repition, castling, promotion)
  - Live Fischer Clock (Chess Clock)
  - Fischer Random variant implemented
  - Fairy Chess Piece as proof of concept for new pieces
  - AI optimized
  - Architecture and logic improvements
  
### Version 1.0 (Final Submission)
  - Click and drag GUI interface
  - All files commented
  - JUnit test documentation describing every test for the Piece class and individual pieces
  - Minor bug fixes for castling

This is an OOP based chess program written in the java programming language and created as a project for CPSC233 at the University of Calgary for the Winter 2019 semester.


v1.0 (Final Submussion)

### Tut10 G01
  - Elvis Chen
  - Elshaday Moges
  - Diwij Dev
  - Yifeng Pan
  - Jeremy Stuart
