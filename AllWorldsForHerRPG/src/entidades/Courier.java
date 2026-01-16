package entidades;
/**
 * Classe que representa a personagem Estafeta.
 * Possui um ataque especial baseado em lançamentos.
 */
public class Courier extends Hero {
    /**
     * Cria uma nova personagem Estafeta.
     *
     * @param name nome da personagem
     * @param maxHealth vida máxima
     * @param strength força base
     * @param gold ouro inicial
     */
    public Courier(String name, int maxHealth, int strength, int gold) {
        super(name, maxHealth, strength, gold);
    }

    /**
     * Devolve o texto de introdução da personagem Estafeta.
     *
     * @return texto de introdução
     */
    protected String getIntroText() {
        return "És estafeta. Conheces as ruas, a pressa e o perigo.\n" +
                "Mas nada te preparou para isto.\n" +
                "Entras no labirinto porque a única entrega que importa agora é a cura.";
    }
    /**
     * Imprime o retrato ASCII da personagem Estafeta.
     */
    protected void printAsciiPortrait() {
        System.out.println("   ______");
        System.out.println("  / ____/\\");
        System.out.println(" /_/___ / /");
        System.out.println(" \\_____/ /");
        System.out.println("  /  _  \\/");
        System.out.println(" |  (_)  |");
        System.out.println(" |   _   |");
        System.out.println("  \\_____/ ");
    }
    /**
     * Imprime a fala associada ao ataque especial do Estafeta.
     */
    protected void printSpecialDialogue() {
        System.out.println("Tu: \"Entrega urgente! Assina… ou leva com ela!\"");
    }
    /**
     * Devolve o bónus de dano do ataque especial do Estafeta.
     *
     * @return valor do bónus
     */
    protected int getHeroSpecialBonus() {
        return 5;
    }
}