import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GanadorTest {

    private static TicTacToe game;

    @BeforeAll
    public static void setUp(){
        game = new TicTacToe();
    }

    //Por defecto no hay ganador
    @Test
    public void testStartGameWinner(){
        game.resetGame();
        Assertions.assertEquals(TicTacToe.GameState.PLAYING, game.getGameState());
    }

    //Condicion ganadora I - fila ganadora
    @Test
    public void testWinnerPlayerWithRows(){
        game.resetGame();
        game.jugar(1,1); // X
        game.jugar(2,1); // O
        game.jugar(1,2); // X
        game.jugar(2,2); // O
        game.jugar(1,3); // X
        Assertions.assertEquals(TicTacToe.GameState.X_WON, game.getGameState());
    }

    //Condicion ganadora II - columna ganadora
    @Test
    public void testWinnerPlayerWithColumns(){
        game.resetGame();
        game.jugar(1,1); // X
        game.jugar(1,2); // O
        game.jugar(2,1); // X
        game.jugar(2,2); // O
        game.jugar(3,1); // X
        Assertions.assertEquals(TicTacToe.GameState.X_WON, game.getGameState());
    }

    //Condicion ganadora IV - diagonal principal ganadora
    @Test
    public void testWinnerPlayerWithMainDiagonal(){
        game.resetGame();
        game.jugar(1,1); // X
        game.jugar(1,2); // O
        game.jugar(2,2); // X
        game.jugar(2,3); // O
        game.jugar(3,3); // X
        Assertions.assertEquals(TicTacToe.GameState.X_WON, game.getGameState());
    }

    //Condicion ganadora IV - diagonal secundaria ganadora
    @Test
    public void testWinnerPlayerWithSecondaryDiagonal(){
        game.resetGame();
        game.jugar(3,1); // X
        game.jugar(1,2); // O
        game.jugar(2,2); // X
        game.jugar(2,3); // O
        game.jugar(1,3); // X
        Assertions.assertEquals(TicTacToe.GameState.X_WON, game.getGameState());
    }
}