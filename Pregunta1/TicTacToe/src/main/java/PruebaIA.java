import java.util.Scanner;

public class PruebaIA {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);


        while (ticTacToe.getGameState() == TicTacToe.GameState.PLAYING) {
            char jugadorActual = ticTacToe.proximoJugador();

            if (jugadorActual == 'X') {

                System.out.println("Ingresa la fila (1-3): ");
                int fila = scanner.nextInt();

                System.out.println("Ingresa la columna (1-3): ");
                int columna = scanner.nextInt();

                try {
                    ticTacToe.jugar(fila, columna);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // Turno de la IA
                ticTacToe.playIA();
            }
            imprimirTablero(ticTacToe);
        }

        // Imprime el estado final del tablero y el resultado
        System.out.println("Juego terminado. Resultado: " + ticTacToe.getGameState());
    }

    private static void imprimirTablero(TicTacToe ticTacToe) {
        TicTacToe.Box[][] grid = ticTacToe.getGrid();

        System.out.println("----------------------");
        for (int i = 0; i < ticTacToe.getN(); i++) {
            for (int j = 0; j < ticTacToe.getN(); j++) {
                if (grid[i][j]== TicTacToe.Box.EMPTY){
                    System.out.print("- ");
                }
                else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }
}
