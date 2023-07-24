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
}