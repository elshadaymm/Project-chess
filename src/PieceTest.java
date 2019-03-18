import static org.junit.Assert.*;

import org.junit.Test;

import java.beans.Transient;
import java.util.*;

public class PieceTest
{
    public static final String CLASSNAME = "CreditHistory";
    
	@Test
	public void testPawnAdvance()
	{
        Game game = new Game();
        game.setBoard("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 16; //pawns
        numOfLegalMoves += 2; //the king
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());

        game.changeTurn();
        assertEquals("Pawn Advance test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
	@Test
	public void testWhiteEnPassant()
	{
        Game game = new Game();
        game.setBoard("4k3/8/8/3pP3/8/8/8/4K3 w - d6 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("White En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
	@Test
	public void testBlackEnPassant()
	{
        Game game = new Game();
        game.setBoard("4k3/8/8/8/3Pp3/8/8/4K3 b - d3 0 1");

        int numOfLegalMoves = 0;
        numOfLegalMoves += 2; //pawn
        numOfLegalMoves += 5; //the king
        assertEquals("Black En Passant test Failed", numOfLegalMoves, GameHelper.allLegalMoves(game).size());
    }
    
}
