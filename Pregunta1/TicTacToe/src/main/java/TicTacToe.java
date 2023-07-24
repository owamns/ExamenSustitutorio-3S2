import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    public enum Box {O,X, EMPTY}
    private final int N = 3;
    private Box[][] grid;
    public enum GameState {PLAYING, X_WON, O_WON, DRAW}
    private char turn;

    private GameState currentGameState;
    public TicTacToe(){
        grid = new Box[N][N];
        resetGame();
    }

    public Box[][] getGrid() {
        return grid;
    }

    public int getN(){
        return N;
    }

    public void resetGame(){
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
                grid[i][j] = Box.EMPTY;
            }
        }
        turn = 'X';
        currentGameState = GameState.PLAYING;
    }

    public void jugar(int x, int y){
        if((x>N || x<1) || (y>N || y<1)){
            throw new RuntimeException("Movimieto no valido");
        }
        else if(grid[x-1][y-1]!= Box.EMPTY){
            throw new RuntimeException("Lugar ocupado");
        }
        grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
        updateGameState(turn, x, y);
        turn = (turn == 'X'?'O':'X');
    }
    public char proximoJugador() {
        return turn;
    }

    public boolean fullGrids(){
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
                if(grid[i][j]== Box.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esGanador(char turn, int row, int column) {
        Box token = (turn=='X')? Box.X: Box.O;
        return (grid[row-1][0] == token // 3 en fila
                && grid[row-1][1] == token && grid[row-1][2] == token
                || grid[0][column-1] == token // 3 en columna
                && grid[1][column-1] == token && grid[2][column-1] == token
                || row == column // 3 en la diagonal
                && grid[0][0] == token && grid[1][1] == token && grid[2][2] == token
                || row + column == 4 // 3 en la diagonal opuesta
                && grid[0][2] == token && grid[1][1] == token && grid[2][0] == token);
    }

    private void updateGameState(char turn, int row, int column) {
        if (esGanador(turn, row, column)) {
            currentGameState = (turn == 'X') ? GameState.X_WON : GameState.O_WON;
        }
        else if(fullGrids()){
            currentGameState = GameState.DRAW ;
        }
    }

    public GameState getGameState() {
        return currentGameState;
    }

    private List<int[]> generarMovimientos() {
        List<int[]> movimientos = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == Box.EMPTY) {
                    movimientos.add(new int[] { i, j });
                }
            }
        }

        return movimientos;
    }

    public void playIA() {
        int profundidad = 3;
        int[] mejorMovimiento = minimax(profundidad, 'O');

        int fila = mejorMovimiento[0];
        int columna = mejorMovimiento[1];

        jugar(fila + 1, columna + 1);
    }

    private int[] minimax(int profundidad, char jugador) {
        List<int[]> movimientosPosibles = generarMovimientos();

        int mejorPuntaje = (jugador == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int puntajeActual;
        int mejorFila = -1;
        int mejorColumna = -1;

        if (movimientosPosibles.isEmpty() || profundidad == 0 || currentGameState != GameState.PLAYING) {
            mejorPuntaje = evaluateBoard();
        } else {
            for (int[] movimiento : movimientosPosibles) {
                int fila = movimiento[0];
                int columna = movimiento[1];
                grid[fila][columna] = (jugador == 'O') ? Box.O : Box.X;

                if (jugador == 'O') {
                    puntajeActual = minimax(profundidad - 1, 'X')[2];
                    if (puntajeActual > mejorPuntaje) {
                        mejorPuntaje = puntajeActual;
                        mejorFila = fila;
                        mejorColumna = columna;
                    }
                } else {
                    puntajeActual = minimax(profundidad - 1, 'O')[2];
                    if (puntajeActual < mejorPuntaje) {
                        mejorPuntaje = puntajeActual;
                        mejorFila = fila;
                        mejorColumna = columna;
                    }
                }

                grid[fila][columna] = Box.EMPTY;
            }
        }

        return new int[] { mejorFila, mejorColumna, mejorPuntaje };
    }

    private int evaluateBoard() {
        int score = 0;

        // Filas y columnas
        for (int i = 0; i < N; i++) {
            score += evaluateLine(grid[i][0], grid[i][1], grid[i][2]);
            score += evaluateLine(grid[0][i], grid[1][i], grid[2][i]);
        }

        // Diagonales
        score += evaluateLine(grid[0][0], grid[1][1], grid[2][2]);
        score += evaluateLine(grid[0][2], grid[1][1], grid[2][0]);

        return score;
    }

    private int evaluateLine(Box box1, Box box2, Box box3) {
        int score = 0;

        if (box1 == Box.O) {
            if (box2 == Box.O) {
                score = 10;
            } else if (box2 == Box.X || box2 == Box.EMPTY) {
                score = 0;
            }
        } else if (box1 == Box.X) {
            if (box2 == Box.X) {
                score = -10;
            } else if (box2 == Box.O || box2 == Box.EMPTY) {
                score = 0;
            }
        } else if (box1 == Box.EMPTY) {
            if (box2 == Box.O) {
                score = 1;
            } else if (box2 == Box.X) {
                score = -1;
            }
        }

        if (box3 == Box.O) {
            if (score > 0) {
                score *= 10;
            }
        } else if (box3 == Box.X) {
            if (score < 0) {
                score *= 10;
            }
        }

        return score;
    }

}