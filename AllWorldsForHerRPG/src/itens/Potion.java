package itens;

import entidades.Hero;
import entidades.NPC;

import java.util.ArrayList;
/**
 * Representa uma poção utilizável pelo herói.
 * Pode recuperar vida e/ou aumentar a força.
 */
public class Potion extends Consumable {

    private int healAmount;
    private int strengthBoost;
    /**
     * Cria uma poção com efeitos de cura e/ou aumento de força.
     *
     * @param name nome da poção
     * @param price preço em ouro
     * @param healAmount quantidade de vida recuperada
     * @param strengthBoost aumento temporário ou permanente de força
     * @param allowedHeroes lista de heróis que podem usar a poção
     */
    public Potion(String name, int price, int healAmount, int strengthBoost, ArrayList<String> allowedHeroes) {
        super(name, price, allowedHeroes);
        this.healAmount = Math.max(0, healAmount);
        this.strengthBoost = Math.max(0, strengthBoost);
    }
    /**
     * Devolve a quantidade de vida que a poção recupera.
     *
     * @return valor de cura
     */
    public int getHealAmount() { return healAmount; }
    /**
     * Devolve o valor de aumento de força da poção.
     *
     * @return aumento de força
     */
    public int getStrengthBoost() { return strengthBoost; }
    /**
     * Aplica os efeitos da poção no herói.
     * Pode recuperar vida e/ou aumentar a força.
     *
     * @param hero herói que utiliza a poção
     * @param enemy inimigo (não utilizado neste tipo de consumível)
     */
    @Override
    public void use(Hero hero, NPC enemy) {
        if (healAmount > 0) {
            hero.heal(healAmount);
            System.out.println("Recuperaste " + healAmount + " de vida.");
        }
        if (strengthBoost > 0) {
            hero.increaseStrength(strengthBoost);
            System.out.println("A tua força aumentou em +" + strengthBoost + ".");
        }
    }

    /**
     * Mostra os detalhes da poção na consola.
     */
    @Override
    public void showDetails() {
        System.out.println(name + " | Cura: " + healAmount + " | +Força: " + strengthBoost + " | Preço: " + price + " ouro");
    }
}