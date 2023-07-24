import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TurnoTest {

    private static TicTacToe game;

    @BeforeAll
    public static void setUp(){
        game = new TicTacToe();
    }

    //Primer turno, jugador X
    @Test
    public void testXPlayerFirstTurn(){
        game.resetGame();
        Assertions.assertEquals('X',game.proximoJugador());
    }

    //Turno de O despues de X
    @Test
    public void testOPlayerTurnAfterXPlayer(){
        game.resetGame();
        for (int i=0; i<game.getN(); i++){
            for (int j=0; j<game.getN(); j++){
                game.jugar(i+1,j+1);
            }
        }
        Assertions.assertEquals('O',game.proximoJugador());
    }

    //Turno de X despues de O
    @Test
    public void testXPlayerTurnAfterOPlayer(){
        game.resetGame();
        game.jugar(1,1);
        game.jugar(1,2);
        Assertions.assertEquals('X',game.proximoJugador());
    }

}