package itens;

import entidades.Hero;
import entidades.NPC;

import java.util.ArrayList;
/**
 * Classe abstrata que representa um item consumível.
 * Pode ser utilizado pelo herói para aplicar um efeito.
 */
public abstract class Consumable extends Item {
    /**
     * Cria um consumível com nome, preço e restrições de utilização.
     *
     * @param name nome do consumível
     * @param price preço em ouro
     * @param allowedHeroes lista de heróis que podem usar o consumível
     */
    public Consumable(String name, int price, ArrayList<String> allowedHeroes) {
        super(name, price, allowedHeroes);
    }
    /**
     * Aplica o efeito do consumível.
     * Pode afetar o herói, o inimigo ou ambos.
     *
     * @param hero herói que utiliza o consumível
     * @param enemy inimigo afetado (pode ser null)
     */
    public abstract void use(Hero hero, NPC enemy);
}