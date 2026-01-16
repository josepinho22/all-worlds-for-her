package entidades;
/**
 * Classe que representa a personagem Professora.
 * Possui um ataque especial baseado em confusão intelectual.
 */
public class Teacher extends Hero {
    /**
     * Cria uma nova personagem Professora.
     *
     * @param name nome da personagem
     * @param maxHealth vida máxima
     * @param strength força base
     * @param gold ouro inicial
     */
    public Teacher(String name, int maxHealth, int strength, int gold) {
        super(name, maxHealth, strength, gold);
    }
    /**
     * Devolve o texto de introdução da personagem Professora.
     *
     * @return texto de introdução
     */
    @Override
    protected String getIntroText() {
        return "És professora. Vives de lógica, paciência e explicações.\n" +
                "Mas este labirinto não segue regras normais.\n" +
                "Mesmo assim, entras: porque o amor não se discute, faz-se.";
    }
    /**
     * Imprime o retrato ASCII da personagem Professora.
     */
    @Override
    protected void printAsciiPortrait() {
        System.out.println("    _____");
        System.out.println("   / ___ \\");
        System.out.println("  | |o o| |");
        System.out.println("  | | ^ | |");
        System.out.println("   \\_____/");
        System.out.println("    _| |_");
        System.out.println("   /_| |_\\");
    }
    /**
     * Imprime a fala associada ao ataque especial da Professora.
     */
    @Override
    protected void printSpecialDialogue() {
        System.out.println("Tu: \"Álgebra linear. Determinantes. Matrizes.\"");
    }
    /**
     * Devolve o bónus de dano do ataque especial da Professora.
     *
     * @return valor do bónus
     */
    @Override
    protected int getHeroSpecialBonus() {
        return 7;
    }
}