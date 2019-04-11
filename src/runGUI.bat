rmdir /s /q build
mkdir build

javac -d build main\*.java
javac -d build game\pieces\*.java
javac -d build game\*.java
javac -d build ai\*.java

cd build
java main.ChessGUI

PAUSE