package entidades;
/**
 * Classe base abstrata para todas as entidades do jogo.
 * Contém atributos e comportamentos comuns como vida e força.
 */
public abstract class Entity {

    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int strength;

    /**
     * Cria uma nova entidade com nome, vida máxima e força.
     *
     * @param name nome da entidade
     * @param maxHealth vida máxima
     * @param strength força base
     */
    public Entity(String name, int maxHealth, int strength) {
        this.name = name;
        this.maxHealth = Math.max(1, maxHealth);
        this.currentHealth = this.maxHealth;
        this.strength = Math.max(1, strength);
    }

    /**
     * Verifica se a entidade ainda está viva.
     *
     * @return true se a vida atual for superior a zero
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Aplica dano à entidade, reduzindo a vida atual.
     *
     * @param damage quantidade de dano a aplicar
     */
    public void takeDamage(int damage) {
        int dmg = Math.max(0, damage);
        currentHealth -= dmg;
        if (currentHealth < 0) currentHealth = 0;
    }

    /**
     * Cura a entidade, aumentando a vida atual até ao máximo.
     *
     * @param amount quantidade de vida a recuperar
     */
    public void heal(int amount) {
        int a = Math.max(0, amount);
        currentHealth += a;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }

    /**
     * Devolve o nome da entidade.
     *
     * @return nome
     */
    public String getName() { return name; }

    /**
     * Devolve a vida máxima da entidade.
     *
     * @return vida máxima
     */
    public int getMaxHealth() { return maxHealth; }

    /**
     * Devolve a vida atual da entidade.
     *
     * @return vida atual
     */
    public int getCurrentHealth() { return currentHealth; }

    /**
     * Devolve a força da entidade.
     *
     * @return força
     */
    public int getStrength() { return strength; }

    /**
     * Define a vida atual da entidade, garantindo que fica dentro dos limites.
     *
     * @param value novo valor de vida
     */
    public void setCurrentHealth(int value) {
        currentHealth = Math.max(0, Math.min(maxHealth, value));
    }
}