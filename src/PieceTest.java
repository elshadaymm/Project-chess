import static org.junit.Assert.*;

import org.junit.Test;

import java.beans.Transient;
import java.util.*;

public class PieceTest{
    @Test
    public void testKing(){
        Game game = new Game();
        game.setBoard("8/8/4k3/8/8/4K3/8/8 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 8; //the king
        assertEquals("King test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("King test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    @Test
    public void testKingSuicide(){
        Game game = new Game();
        game.setBoard("8/8/4k3/R7/7r/4K3/8/8 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 14;// rook
        assertEquals("King suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("King suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    @Test
    public void testQueen(){
        Game game = new Game();
        game.setBoard("kq6/qq6/8/8/8/8/6QQ/6QK b - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 24;//othagnal moves
        numOfLegalMoves += 12 + 5;//diagnal moves
        assertEquals("Queen suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Queen suicide test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    @Test
    public void testRook(){
        Game game = new Game();
        game.setBoard("kr6/rr6/8/8/8/8/6RR/6RK w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 24; //the rooks
        assertEquals("Rook test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Rook test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    @Test
    public void testBasicWhiteCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 14; //rooks
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    @Test
    public void testBasicBlackCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 14; //rooks
        numOfLegalMoves += 5; //rooks
        numOfLegalMoves += 5; //the king
        numOfLegalMoves += 2; //2 castle moves
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    @Test
    public void testInCheckWhiteCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/4r3/8/8/8/8/8/R3K2R w KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 0; //2 castle moves
        assertEquals("White Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    @Test
    public void testInCheckBlackCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/8/8/8/8/8/4R3/R3K2R b KQkq - 0 1");
        
        int numOfLegalMoves = 0;
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 0; //rooks
        numOfLegalMoves += 4; //the king
        numOfLegalMoves += 0; //2 castle moves
        assertEquals("Black Castle Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
    @Test
    public void testWhiteQueenCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
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
    
    @Test
    public void testBlackQueenCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
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
    
    @Test
    public void testWhiteKingCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
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
    
    @Test
    public void testBlackKingCastle(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
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
    
    @Test
    public void testWhiteQueenCastleCheck(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
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
    
    @Test
    public void testBlackQueenCastleCheck(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
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
    
    @Test
    public void testWhiteKingCastleCheck(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R w KQkq - 0 1");
        
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
    
    @Test
    public void testBlackKingCastleCheck(){
        Game game = new Game();
        game.setBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1");
        
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

    @Test
    public void testBishop(){
        Game game = new Game();
        game.setBoard("kb6/bb6/2b5/8/8/5B2/6BB/6BK b - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 9 + 12;
        assertEquals("Bishop test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Bishop test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }

    @Test
    public void testKnight(){
        Game game = new Game();
        game.setBoard("kn6/n7/8/3n4/4N3/8/7N/6NK w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 6; //knights corner
        numOfLegalMoves += 8; //center knight
        numOfLegalMoves += 1; //the king
        assertEquals("Knight test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("knight test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
	@Test
	public void testPawnAdvance(){
        Game game = new Game();
        game.setBoard("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w - - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 16; //pawns
        numOfLegalMoves += 2; //the king
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
	@Test
	public void testWhiteEnPassant(){
        Game game = new Game();
        game.setBoard("4k3/8/8/3pP3/8/8/8/4K3 w - d6 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("White En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.makeMove(new Move(new Cord(4, 4), new Cord(5, 3)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 4; //king
        assertEquals("White En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
	@Test
	public void testBlackEnPassant(){
        Game game = new Game();
        game.setBoard("4k3/8/8/8/3Pp3/8/8/4K3 b - d3 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("Black En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.makeMove(new Move(new Cord(3, 4), new Cord(2, 3)));

        numOfLegalMoves = 0;
        numOfLegalMoves += 4; //king
        assertEquals("Black En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
}
