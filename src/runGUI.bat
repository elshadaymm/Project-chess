rmdir /s /q build
mkdir build

javac -d build main\*.java

cd build
java main.ChessGUI

PAUSE