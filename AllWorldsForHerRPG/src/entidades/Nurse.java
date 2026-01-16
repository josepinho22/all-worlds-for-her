package entidades;
/**
 * Classe que representa a personagem Enfermeira.
 * Possui um ataque especial único.
 */
public class Nurse extends Hero {

    public Nurse(String name, int maxHealth, int strength, int gold) {
        super(name, maxHealth, strength, gold);
    }

    /**
     * Devolve o texto de introdução da personagem Enfermeira.
     */
    protected String getIntroText() {
        return "És enfermeira. A tua vida sempre foi ajudar os outros.\n" +
                "Agora, a pessoa que amas precisa de ti mais do que nunca.\n" +
                "Entras no labirinto para encontrar a cura.";
    }
    /**
     * Imprime o retrato ASCII da personagem Enfermeira.
     */
    protected void printAsciiPortrait() {
        System.out.println("   .-\"\"\"-.");
        System.out.println("  /  _ _  \\");
        System.out.println(" |  (o o)  |");
        System.out.println(" |   \\_/   |");
        System.out.println("  \\  ___  /");
        System.out.println("   '-___-'");
        System.out.println("    /| |\\");
        System.out.println("   /_| |_\\");
    }
    /**
     * Imprime a fala associada ao ataque especial da Enfermeira.
     */
    protected void printSpecialDialogue() {
        System.out.println("Tu: \"Pulseira verde. Pode aguardar.\"");
    }

    /**
     * Devolve o bónus de dano do ataque especial.
     *
     * @return valor do bónus
     */
    protected int getHeroSpecialBonus() {
        return 6;
    }
}