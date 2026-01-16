package jogo;
/**
 * Classe utilitária para efeitos visuais na consola.
 * Contém métodos estáticos para pausas, títulos, menus e animações de texto.
 */
public final class ConsoleFX {
    /**
     * Construtor privado para impedir a criação de instâncias desta classe utilitária.
     */
    private ConsoleFX() {}
    /**
     * Suspende a execução do programa durante um determinado tempo.
     *
     * @param ms tempo de pausa em milissegundos
     */
    public static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    /**
     * Imprime texto na consola com efeito de máquina de escrever.
     *
     * @param text texto a imprimir
     * @param delayPerCharMs atraso em milissegundos entre cada carácter
     */
    public static void typewriter(String text, long delayPerCharMs) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            pause(delayPerCharMs);
        }
        System.out.println();
    }
    /**
     * Mostra um título formatado na consola.
     *
     * @param text texto do título
     */
    public static void title(String text) {
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println("        " + text.toUpperCase());
        System.out.println("════════════════════════════════════════");
        System.out.println();
    }
    /**
     * Mostra um separador de secção na consola.
     *
     * @param text texto da secção
     */
    public static void section(String text) {
        System.out.println();
        System.out.println("────────── " + text + " ──────────");
    }
    /**
     * Mostra uma opção numerada de menu.
     *
     * @param number número da opção
     * @param text descrição da opção
     */
    public static void option(int number, String text) {
        System.out.println("  " + number + ") " + text);
    }
    /**
     * Mostra um pedido de entrada ao utilizador.
     *
     * @param text texto do pedido
     */
    public static void prompt(String text) {
        System.out.print("👉 " + text + " ");
    }

}