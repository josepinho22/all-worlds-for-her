package itens;

import entidades.Hero;
import entidades.NPC;

import java.util.ArrayList;
/**
 * Representa um consumível de combate.
 * Aplica dano instantâneo a um inimigo quando utilizado.
 */
public class CombatConsumable extends Consumable {

    private int instantAttack;
    /**
     * Cria um consumível de combate com dano instantâneo.
     *
     * @param name nome do consumível
     * @param price preço em ouro
     * @param instantAttack dano instantâneo causado
     * @param allowedHeroes lista de heróis que podem usar o item
     */
    public CombatConsumable(String name, int price, int instantAttack, ArrayList<String> allowedHeroes) {
        super(name, price, allowedHeroes);
        this.instantAttack = Math.max(0, instantAttack);
    }
    /**
     * Aplica o efeito do consumível no inimigo.
     * Causa dano instantâneo durante o combate.
     *
     * @param hero herói que utiliza o consumível
     * @param enemy inimigo afetado
     */
    @Override
    public void use(Hero hero, NPC enemy) {
        if (enemy == null) return;
        enemy.takeDamage(instantAttack);
        System.out.println("Usaste " + name + " e causaste " + instantAttack + " de dano instantâneo.");
    }
    /**
     * Mostra os detalhes do consumível de combate.
     */
    @Override
    public void showDetails() {
        System.out.println(name + " | Dano instantâneo: " + instantAttack + " | Preço: " + price + " ouro");
    }
}