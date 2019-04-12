/**
 * The Piece Class is the parent class of all the game pieces.  This junit tests
 * all of the pieces to make sure their movement rules are working properly.  It does
 * this in the majority of tests by creating a board with pieces in specific positions
 * and then testing the number of moves available to those pieces.  If the number of moves
 * meets the expectation, the test passes.  Please see the junit documentation to see the
 * setup of the board for each test.
 */
package junit;

import game.*;
import game.pieces.*;
import ai.*;

import static org.junit.Assert.*;

import org.junit.Test;

import java.beans.Transient;
import java.util.*;

public class PieceTest{
    private Game game = new Game();
    private Human tester = new Human(game);

    /**
     * Checks the basic movement rules of the King
     */
    @Test
    public void testKing(){
        game.importGame("8/8/4k3/8/8/4K3/8/8 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 8; //the king
        assertEquals("King test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("King test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Makes sure that the King cannot move into check
     */
    @Test
    public void testKingSuicide(){
        game.importGame("8/8/4k3/R7/7r/4K3/8/8 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 14;// rook
        assertEquals("King suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("King suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Creates a board with every piece and places the King in check with only one
     * move that can escape check: a move by the King (there are no moves by other pieces
     * that could block the check).  In this case, the game should only see one possible move,
     * and that is the one that moves the King out of check.
     */
    @Test
    public void testCheckmate(){
        game.importGame("rnb1kbnr/2Nppppp/8/p1p1N3/1p6/3P3P/PPP1PPP1/R1BQKB1R b KQkq - 1 7");

        int numOfLegalMoves = 1;// escape check
        assertEquals("Checkmate test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Tests basic movement rules of the Queen
     */
    @Test
    public void testQueenWhite(){
        game.importGame("kq6/qq6/8/8/8/8/6QQ/6QK w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 12;//othagnal moves
        numOfLegalMoves += 12 + 5;//diagnal moves
        assertEquals("Queen suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Tests basic movement rules of the Queen
     */
    @Test
    public void testQueenBlack(){
        game.importGame("kq6/qq6/8/8/8/8/6QQ/6QK b - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 12;//othagnal moves
        numOfLegalMoves += 12 + 5;//diagnal moves
        assertEquals("Queen suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Tests basic movement rules of the Rook
     */
    @Test
    public void testRook(){
        game.importGame("kr6/rr6/8/8/8/8/6RR/6RK w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 24; //the rooks
        assertEquals("Rook test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Rook test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Basic test of the ability for White to castle
     */
    @Test
    public void testBasicWhiteCastle(){
        game.importGame("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 14; //rooks
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Basic test of the ability for Black to castle
     */
    @Test
    public void testBasicBlackCastle(){
        game.importGame("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 14; //rooks
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the White King cannot castle when it is in check
     */
    @Test
    public void testInCheckWhiteCastle(){
        game.importGame("r3k2r/4r3/8/8/8/8/8/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 0; //2 castle moves
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the Black King cannot castle when it is in check
     */
    @Test
    public void testInCheckBlackCastle(){
        game.importGame("r3k2r/8/8/8/8/8/4R3/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 0; //2 castle moves
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests the number of moves available before and after the White King castles
     * Queenside
     */
    @Test
    public void testWhiteQueenCastle(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(0,4), new Cord(0,2)));
        game.changeTurn();

        numOfLegalMoves = 0;
        numOfLegalMoves += 6 + 7; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests the number of moves available before and after the Black King castles
     * Queenside
     */
    @Test
    public void testBlackQueenCastle(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(7,4), new Cord(7,2)));
        game.changeTurn();  

        numOfLegalMoves = 0;
        numOfLegalMoves += 6 + 7; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests the number of moves available before and after the White King castles
     * QKingside
     */
    @Test
    public void testWhiteKingCastle(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(0,4), new Cord(0,6)));
        game.changeTurn();

        numOfLegalMoves = 0;
        numOfLegalMoves += 8 + 7; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests the number of moves available before and after the Black King castles
     * Kingside
     */
    @Test
    public void testBlackKingCastle(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(7,4), new Cord(7,6)));
        game.changeTurn();  

        numOfLegalMoves = 0;
        numOfLegalMoves += 8 + 7; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the White King cannot castle through a square that is
     * under attack
     */
    @Test
    public void testWhiteQueenCastleCheck(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(0,4), new Cord(0,2)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 1; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the Black King cannot castle through a square that is
     * under attack
     */
    @Test
    public void testBlackQueenCastleCheck(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(7,4), new Cord(7,2)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 1; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the Black King cannot castle through a square that is
     * under attack
     */
    @Test
    public void testWhiteKingCastleCheck(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(0,4), new Cord(0,6)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 1; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure that the Black King cannot castle through a square that is
     * under attack
     */
    @Test
    public void testBlackKingCastleCheck(){
        game.importGame("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.move(new Move(new Cord(7,4), new Cord(7,6)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 3; //the king
        numOfLegalMoves += 1; //2 castle moves
        numOfLegalMoves += 4; //pawns
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * A general test of the ability of the Rook to castle
     */
    @Test
    public void testCastleGeneral(){
        game.importGame("r3k2r/8/6N1/8/8/2n5/8/R3K2R w KQkq - 0 1");

        tester.move("e1d1");//should fail
        tester.move("e1c1");//should fail
        tester.move("e1g1");//should work

        tester.move("e8g8");//should fail
        tester.move("e8f8");//should fail
        tester.move("e8c8");//should work

        assertEquals("Castle General failed", "2kr3r/8/6N1/8/8/2n5/8/R4RK1 w - - 2 2", GameInfo.toFEN(game));
    }

    /**
     * Checks the basic movement rules of the Bishop
     */
    @Test
    public void testBishop(){
        game.importGame("kb6/bb6/2b5/8/8/5B2/6BB/6BK b - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 9 + 12;
        assertEquals("Bishop test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Bishop test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * /**
     * Checks the basic movement rules of the Knight
     */
    @Test
    public void testKnight(){
        game.importGame("kn6/n7/8/3n4/4N3/8/7N/6NK w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 6; //knights corner
        numOfLegalMoves += 8; //center knight
        numOfLegalMoves += 1; //the king
        assertEquals("Knight test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("knight test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests the ability of the pawns to advance two squares on their first move
     */
	@Test
	public void testPawnAdvance(){
        game.importGame("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 16; //pawns
        numOfLegalMoves += 2; //the king
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure White Pawns are abilty to capture en passant
     */
	@Test
	public void testWhiteEnPassant(){
        game.importGame("4k3/8/8/3pP3/8/8/8/4K3 w - d6 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("White En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.makeMove(new Move(new Cord(4, 4), new Cord(5, 3)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 4; //king
        assertEquals("White En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    /**
     * Tests to make sure Black Pawns are abilty to capture en passant
     */
	@Test
	public void testBlackEnPassant(){
        game.importGame("4k3/8/8/8/3Pp3/8/8/4K3 b - d3 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("Black En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.makeMove(new Move(new Cord(3, 4), new Cord(2, 3)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 4; //king
        assertEquals("Black En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Tests the ability of White pawns to promote when they reach the opposite end
     * of the board
     */
    @Test
    public void testWhitePromotion(){
        game.importGame("4k2n/1P4P1/8/8/8/8/1p4p1/4K2N w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 3 * 4; // knight
        numOfLegalMoves += 2; //knight
        numOfLegalMoves += 4; //king
        assertEquals("White promotion test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    /**
     * Tests the ability of Black pawns to promote when they reach the opposite end
     * of the board
     */
    @Test
    public void testBlackPromotion(){
        game.importGame("4k2n/1P4P1/8/8/8/8/1p4p1/4K2N b - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 3 * 4; // knight
        numOfLegalMoves += 2; //knight
        numOfLegalMoves += 4; //king
        assertEquals("Black promotion test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
}