# Java-Chess
An OOP based chess program written in the java programming language.  Created as a project for CPSC233 at the University of Calgary for the winter 2019 semester.

# How To Run
Compile ChessGui and revelant files. Then run "java ChessGUI Human AIMinMax". first argument is white. second is black. Options are "Human", "AIRandom", "AIMinMax" for now

# Run from .bat
You must first run the complie.bat file in the src folder to compile the code.  Thereafter the human_vs_human.bat or human_vs_minmax.bat or ect file can be used to run the compiled game.

# How To Play
Input your move into the field marked "Input Move:" in standard chess algebraic notation with the square you're moving from and the square you're moving to.  For example, to start the game by moving the left most pawn one space forward for the white player, input "a2a3" where "a2" is the starting position of the pawn and "a3" is the desired position to move the pawn.  Note that the labels for ranks and files are around the outside of the board to assist you to find the coordinates of each space.

# Improvements From v0.1
A GUI has been implemented.  It currently works by input moves manually (not via point and click on the board).  The AI has been improved and now includes an AI that plays random moves, an AI that uses minmax decision making, and a minmax AI that uses alpha and beta prunning.  The game is now able to recognize checkmate and stalemate and ends the game at that position.  The code has been cleaned up, with constants being moved to a Constant class.


v0.2 (Demo 2)
Tut10G01
Elvis Chen
Elshaday Moges
Diwij Dev
Yifeng Pan
Jeremy Stuart
