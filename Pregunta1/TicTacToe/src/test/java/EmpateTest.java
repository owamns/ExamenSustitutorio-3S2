import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EmpateTest {

    private static TicTacToe game;

    @BeforeAll
    public static void setUp(){
        game = new TicTacToe();
    }

    //Condicion de empate
    @Test
    public void testDrawGame(){
        game.resetGame();
        game.jugar(1,1); //X
        game.jugar(1,2); //O
        game.jugar(2,2); //X
        game.jugar(1,3); //O
        game.jugar(2,3); //X
        game.jugar(2,1); //O
        game.jugar(3,1); //X
        game.jugar(3,3); //O
        game.jugar(3,2); //X
        Assertions.assertEquals(TicTacToe.GameState.DRAW, game.getGameState());
    }

}