package entidades;

/**
 * Representa um inimigo ou personagem não jogável.
 * Contém atributos de combate e uma recompensa em ouro.
 */
public class NPC extends Entity {

    private final int goldReward;
    /**
     * Cria um NPC com nome, vida, força e recompensa em ouro.
     *
     * @param name nome do NPC
     * @param maxHealth vida máxima
     * @param strength força base
     * @param goldReward ouro concedido ao ser derrotado
     */
    public NPC(String name, int maxHealth, int strength, int goldReward) {
        super(name, maxHealth, strength);
        this.goldReward = Math.max(0, goldReward);
    }

    /**
     * Devolve a quantidade de ouro atribuída ao derrotar o NPC.
     *
     * @return ouro de recompensa
     */
    public int getGoldReward() {
        return goldReward;
    }
}