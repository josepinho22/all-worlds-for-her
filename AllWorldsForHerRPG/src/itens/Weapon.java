package itens;

import java.util.ArrayList;
/**
 * Representa uma arma utilizável pelo herói.
 * Aumenta o dano de ataques normais e especiais.
 */
public class Weapon extends Item {

    private final int attack;
    private final int specialAttack;
    /**
     * Cria uma arma com valores de ataque normal e especial.
     *
     * @param name nome da arma
     * @param price preço em ouro
     * @param attack valor de ataque normal
     * @param specialAttack valor de ataque especial
     * @param allowedHeroes lista de heróis que podem usar a arma
     */
    public Weapon(String name, int price, int attack, int specialAttack, ArrayList<String> allowedHeroes) {
        super(name, price, allowedHeroes);
        this.attack = Math.max(0, attack);
        this.specialAttack = Math.max(0, specialAttack);
    }
    /**
     * Devolve o valor de ataque normal da arma.
     *
     * @return ataque normal
     */
    public int getAttack() { return attack; }

    /**
     * Devolve o valor de ataque especial da arma.
     *
     * @return ataque especial
     */
    public int getSpecialAttack() { return specialAttack; }

    /**
     * Mostra os detalhes da arma na consola.
     */
    @Override
    public void showDetails() {
        System.out.println(name + " | ATK: " + attack + " | ESP: " + specialAttack + " | Preço: " + price + " ouro");
    }
}