# Pregunta 1

## REQUISITO 1 :colocación de piezas

### Prueba: límites del tablero I

En la clase `TicTacToe` se tiene el método `jugar()` de las cuales se realizaran las siguientes pruebas
en la clase `JugarTest` para colocar una pieza fuera del eje x.

Prueba a realizar:
```
@Test
public void testMoveInvalidXPositionThenShowException(){
    exception = assertThrows(RuntimeException.class, () -> {
        game.jugar(5, 2);
    });
    Assertions.assertEquals("Movimieto no valido", exception.getMessage());
}
```

- Se realiza la prueba sin la implementacion del método.

```
public void jugar(int x, int y){
}
```

Falla debido a que no esta implementado.

- Se realiza la prueba sin la implementacion de la excepcion.

```
public void jugar(int x, int y){
    if (x >= 1 && x <= 3) {
        grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
    }
}
```
Falla debido a que se espera una excepcion.

- Se realiza la prueba implementando la excepcion.

```
public void jugar(int x, int y){
    if((x>N || x<1)){
            throw new RuntimeException("Movimieto no valido");
    }
    grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
}
```
La prueba tuvo exito debido a que se muestra la excepcion.

### Prueba: límites del tablero II

En la clase `TicTacToe` se tiene el método `jugar()` de las cuales se realizaran las siguientes pruebas
en la clase `JugarTest` para colocar una pieza fuera del eje y.

Prueba a realizar:
```
@Test
public void testMoveInvalidYPositionThenShowException(){
    exception = assertThrows(RuntimeException.class, () -> {
        game.jugar(2, 5);
    });
    Assertions.assertEquals("Movimieto no valido", exception.getMessage());
}
```
De la implementacion anterior para el eje x solo se tendria que agregar para el caso del eje y, por lo
tanto el método jugar estaria implementando de la siguiente manera:
```
public void jugar(int x, int y){
    if((x>N || x<1) || (y>N || y<1)){
            throw new RuntimeException("Movimieto no valido");
    }
    grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
}
```
La prueba tuvo exito debido a que se muestra la excepcion.

### Prueba - lugar ocupado

En la clase `TicTacToe` se tiene el método `jugar()` de las cuales se realizaran las siguientes pruebas
en la clase `JugarTest` para que solo se puedan colocar en espacios desocupados.

Prueba a realizar:

En la prueba se observa que primero se hace uso del método `jugar()` para asi luego hacer la prueba
del lugar ocupado.
```
@Test
public void testMoveOccupiedPositionThenShowException(){
    game.jugar(2,2);
    exception = assertThrows(RuntimeException.class, () -> {
        game.jugar(2, 2);
    });
    Assertions.assertEquals("Lugar ocupado",exception.getMessage());
}
```
- Se realiza implementando una excepcion si un lugar esta ocupado en el método `jugar()`.
```
public void jugar(int x, int y){
    if((x>N || x<1) || (y>N || y<1)){
        throw new RuntimeException("Movimieto no valido");
    }
    else if(grid[x-1][y-1]!= Box.EMPTY){
        throw new RuntimeException("Lugar ocupado");
    }
    grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
}
```
## REQUISITO 2 :agregar soporte para dos jugadores

### Prueba – X juega primero

En la clase `TicTacToe` se tiene el método constructor en el cual se se hace uso del método 
`resetGame()` en la que se establece el turno en la variable `turn`.

Método resetGame()
```
public void resetGame(){
    for (int i=0; i<N; i++){
        for (int j=0; j<N; j++){
            grid[i][j] = Box.EMPTY;
        }
    }
    turn = 'X';
}
```
Méodo constructor de la clase TicTacToe:
```
public TicTacToe(){
        grid = new Box[N][N];
        resetGame();
}
```

Se realizan la siguiente prueba en la clase `TurnoTest` para X juega primero.

Prueba a realizar:
```
@Test
public void testXPlayerFirstTurn(){
    game.resetGame();
    Assertions.assertEquals('X',game.proximoJugador());
}
```
La prueba pasa debeido a que se inicializa la variable `turn` con X.

### Prueba: O juego justo después de X

Para esta prueba se tiene que refactorizar el metodo `jugar` debido a que se tiene que guardar el turno 
en cada que se coloca una pieza.

Implementando lo mencionado el método jugar estaria de la siguiente manera:
```
public void jugar(int x, int y){
        if((x>N || x<1) || (y>N || y<1)){
            throw new RuntimeException("Movimieto no valido");
        }
        else if(grid[x-1][y-1]!= Box.EMPTY){
            throw new RuntimeException("Lugar ocupado");
        }
        grid[x-1][y-1] = (turn == 'X') ? Box.X: Box.O;
        turn = (turn == 'X'?'O':'X');
    }
```
Se realizan la siguiente prueba en la clase `TurnoTest` para O juega justo despues de X.

Prueba a relizar:
```
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
```
El método `proximoJugador` solo retorna el atributo `turn` que es el turno.
```
public char proximoJugador() {
    return turn;
}
```
La prueba pasa debido a que se juega sucesivamente y termina en el turno de X porque lo que
el turno siguiente es el de O.

### Prueba: X juega justo después de O
Para esta prueba no se realizan nuevas implementaciones debido a que de la prueba 
anterior ya cumple con estas.

Para comprobar lo mencionado se realiza la siguiente prueba en la clase `TurnoTest`:
```
@Test
public void testXPlayerTurnAfterOPlayer(){
    game.resetGame();
    game.jugar(1,1);
    game.jugar(1,2);
    Assertions.assertEquals('X',game.proximoJugador());
}
```
La prueba pasa por lo que se puede desechar esta prueba.

## REQUISITO 3 :agregar condiciones ganadoras

### Prueba: por defecto no hay ganador

Para esta prueba se agrega una nueva variable `currentGameState` de tipo `GameState` que determina 
el estado el juego (jugando, x gano, y gano).
```
public enum GameState {PLAYING, X_WON, O_WON}
```
Por lo que se refactoriza el método `resetGame` para inicializar el estado del juego.
```
public void resetGame(){
    for (int i=0; i<N; i++){
        for (int j=0; j<N; j++){
            grid[i][j] = Box.EMPTY;
        }
    }
    turn = 'X';
    currentGameState = GameState.PLAYING;
}
```

Prueba a realizar en la clase `GanadorTest`:
```
@Test
public void testStartGameWinner(){
    game.resetGame();
    Assertions.assertEquals(TicTacToe.GameState.PLAYING, game.getGameState());
}
```
Esta prueba pasa debeido a que se espera que no halla ganadores al iniciar el juego. 

### Prueba – condición ganadora I

Para esta prueba se tiene que refactorizar el método jugar para determinar el ganador, por lo que 
se crean 2 métodos en la clase TicTacToe que son `updateGameState` y `esGanador`.

updateGameState, método para establecer el estado del juego:
```
private void updateGameState(char turn, int row, int column) {
    if (esGanador(turn, row, column)) {
        currentGameState = (turn == 'X') ? GameState.X_WON : GameState.O_WON;
    }
    else if(fullGrids()){
        currentGameState = GameState.DRAW ;
    }
}
```

esGanador, método para establecer el ganador si se cumplen las condiciones de ganador:
```
public boolean esGanador(char turn, int row, int column) {
    Box token = (turn=='X')? Box.X: Box.O;
    return (grid[row-1][0] == token // 3 en fila
            && grid[row-1][1] == token && grid[row-1][2] == token);
}
```
Prueba a realizar en la clase `GanadorTest`:
```
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
```
Esta prueba pasa debido a que se espera un ganador cuando toda una linea horizontal esta ocupada por
una misma piensa, en este caso X.

### Prueba – condición ganadora II
Para esta prueba de refactoriza el metodo `esGanador`, llenando una linea vertical.
```
public boolean esGanador(char turn, int row, int column) {
    Box token = (turn=='X')? Box.X: Box.O;
    return (grid[row-1][0] == token // 3 en fila
            && grid[row-1][1] == token && grid[row-1][2] == token
            || grid[0][column-1] == token // 3 en columna
            && grid[1][column-1] == token && grid[2][column-1] == token);
}
```
Prueba a realizar en la clase `GanadorTest`:
```
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
```
La prueba pasa debido a que se espera un ganador cuando toda una linea vertical esta ocupada por
una misma piensa, en este caso X.

### Prueba – condición ganadora III

Para esta prueba de refactoriza el metodo `esGanador`, llenando la diagonal principal.
```
public boolean esGanador(char turn, int row, int column) {
    Box token = (turn=='X')? Box.X: Box.O;
    return (grid[row-1][0] == token // 3 en fila
            && grid[row-1][1] == token && grid[row-1][2] == token
            || grid[0][column-1] == token // 3 en columna
            && grid[1][column-1] == token && grid[2][column-1] == token
            || row == column // 3 en la diagonal
            && grid[0][0] == token && grid[1][1] == token && grid[2][2] == token);
}
```
Prueba a realizar en la clase `GanadorTest`:
```
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
```
La prueba pasa debido a que se espera un ganador cuando toda la diagonal principal esta ocupada por
una misma piensa, en este caso X.

### Prueba – condición ganadora IV

Para esta prueba de refactoriza el metodo `esGanador`, llenando la diagonal secundaria.
```
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
```
Prueba a realizar en la clase `GanadorTest`:
```
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
```
La prueba pasa debido a que se espera un ganador cuando toda la diagonal secundaria esta ocupada por
una misma piensa, en este caso X.

## Requisito 4: condiciones de empate
Para este requisito se agrega un tipo de estado para la variable `currentGameState` de tipo `GameState`
que es `DRAW` que indica el empate.

Por lo que GameState estaria de la siguiente manera:
```
public enum GameState {PLAYING, X_WON, O_WON, DRAW}
```

Tambien se agrega el método `fullGrids` para verificar que todo el tablero este lleno y se uso en el método
`updateGameState` para determinar el empate.
```
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
```
updateGameState se refactoriza de la siguiente manera:
```
private void updateGameState(char turn, int row, int column) {
    if (esGanador(turn, row, column)) {
        currentGameState = (turn == 'X') ? GameState.X_WON : GameState.O_WON;
    }
    else if(fullGrids()){
        currentGameState = GameState.DRAW ;
    }
}
```
Prueba a realizar en `EmpateTest`:
```
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
```
La prueba pasa debido a que se espera un empate luego de llenar el tablero.




