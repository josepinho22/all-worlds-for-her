package itens;

import entidades.Hero;
import java.util.ArrayList;
/**
 * Classe abstrata que representa um item do jogo.
 * Pode ser comprado na loja e utilizado ou equipado pelo herói.
 */
public abstract class Item {

    protected String name;
    protected int price;
    protected ArrayList<String> allowedHeroes;
    /**
     * Cria um item com nome, preço e restrições de utilização.
     *
     * @param name nome do item
     * @param price preço em ouro
     * @param allowedHeroes lista de heróis que podem usar o item
     */
    public Item(String name, int price, ArrayList<String> allowedHeroes) {
        this.name = name;
        this.price = Math.max(0, price);
        this.allowedHeroes = (allowedHeroes == null) ? new ArrayList<>() : allowedHeroes;
    }
    /**
     * Devolve o nome do item.
     *
     * @return nome do item
     */
    public String getName() { return name; }
    /**
     * Devolve o preço do item em ouro.
     *
     * @return preço do item
     */
    public int getPrice() { return price; }
    /**
     * Verifica se o item pode ser utilizado pelo herói.
     *
     * @param hero herói a verificar
     * @return true se o item for permitido, false caso contrário
     */
    public boolean isAllowedFor(Hero hero) {
        if (allowedHeroes.isEmpty()) return true;
        String heroType = hero.getClass().getSimpleName();
        return allowedHeroes.contains(heroType);
    }
    /**
     * Mostra os detalhes básicos do item na consola.
     */
    public void showDetails() {
        System.out.println(name + " | Preço: " + price + " ouro");
    }
}