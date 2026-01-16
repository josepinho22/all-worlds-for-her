package jogo;
/**
 * Classe principal do programa.
 * Contém o método main, ponto de entrada da aplicação.
 */
public class Main {
    /**
     * Método principal que inicia a execução do jogo.
     *
     * @param args argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}