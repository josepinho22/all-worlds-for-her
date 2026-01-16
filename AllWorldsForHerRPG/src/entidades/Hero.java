package entidades;

import audio.Audio;
import itens.CombatConsumable;
import itens.Consumable;
import itens.Potion;
import itens.Weapon;
import jogo.ConsoleFX;
import jogo.WeakPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Representa o herói principal do jogo.
 * Gere combate, inventário, atributos e interações do jogador.
 */

public abstract class Hero extends Entity {

    protected int level;
    protected int gold;

    protected Weapon equippedWeapon;
    protected ArrayList<Consumable> inventory;

    protected WeakPoint weakPoint;
    protected boolean specialUsedThisFight;

    /**
     * Cria um herói com os atributos iniciais definidos.
     *
     * @param name nome do herói
     * @param maxHealth vida máxima
     * @param strength força base
     * @param gold ouro inicial
     */
    public Hero(String name, int maxHealth, int strength, int gold) {
        super(name, maxHealth, strength);

        this.level = 1;
        this.gold = Math.max(0, gold);
        this.inventory = new ArrayList<>();
        this.equippedWeapon = null;
        WeakPoint[] values = WeakPoint.values();
        this.weakPoint = values[new Random().nextInt(values.length)];
        this.specialUsedThisFight = false;
    }
    /**
     * Devolve o texto de introdução da personagem.
     *
     * @return texto de introdução
     */
    protected abstract String getIntroText();
    /**
     * Imprime o retrato ASCII da personagem.
     */
    protected abstract void printAsciiPortrait();
    /**
     * Imprime a fala associada ao ataque especial da personagem.
     */
    protected abstract void printSpecialDialogue();
    /**
     * Devolve o bónus de dano do ataque especial.
     *
     * @return valor do bónus
     */
    protected abstract int getHeroSpecialBonus();

    /**
     * Mostra a introdução da personagem e o respetivo retrato ASCII.
     */
    public void showIntro() {
        System.out.println(getIntroText());
        System.out.println();
        ConsoleFX.pause(350);
        printAsciiPortrait();
        System.out.println();
        ConsoleFX.pause(600);
    }

    /**
     * Devolve o ponto fraco do herói.
     *
     * @return ponto fraco
     */
    public WeakPoint getWeakPoint() { return weakPoint; }
    /**
     * Devolve a quantidade de ouro do herói.
     *
     * @return ouro atual
     */
    public int getGold() {
        return gold;
    }

    /**
     * Devolve o inventário do herói.
     *
     * @return lista de consumíveis
     */
    public ArrayList<Consumable> getInventory() { return inventory; }

    /**
     * Adiciona um consumível ao inventário do herói.
     *
     * @param item consumível a adicionar
     */
    public void addConsumable(Consumable item) {
        inventory.add(item);
    }

    /**
     * Equipa uma arma no herói.
     *
     * @param weapon arma a equipar
     */
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    /**
     * Tenta gastar uma determinada quantidade de ouro.
     *
     * @param amount quantidade a gastar
     * @return true se houver ouro suficiente, false caso contrário
     */
    public boolean spendGold(int amount) {
        int a = Math.max(0, amount);
        if (gold < a) return false;
        gold -= a;
        return true;
    }

    /**
     * Adiciona ouro ao herói.
     *
     * @param amount quantidade a adicionar
     */
    public void earnGold(int amount) {
        int a = Math.max(0, amount);
        gold += a;
        if (a > 0) {
            System.out.println("Ganhaste " + a + " ouro.");
            ConsoleFX.pause(300);
        }
    }

    /**
     * Aumenta a força do herói.
     *
     * @param amount quantidade a adicionar
     */
    public void increaseStrength(int amount) {
        strength += Math.max(0, amount);
    }

    /**
     * Aplica melhorias ao herói após uma vitória.
     */
    public void levelUp() {
        level++;
        maxHealth += 10;
        strength += 1;
        if (currentHealth > maxHealth) currentHealth = maxHealth;

        System.out.println("Subiste de nível!");
        ConsoleFX.pause(200);
        System.out.println("Vida máxima +10 | Força +1");
        ConsoleFX.pause(450);
    }
    /**
     * Imprime o estado atual do herói na consola.
     */
    public void printStatus() {
        System.out.println();
        System.out.println("📊 Estado do Herói");
        System.out.println("────────────────────────────────────────");
        System.out.println("❤️ Vida : " + currentHealth + "/" + maxHealth);
        System.out.println("💪 Força: " + strength);
        System.out.println("💰 Ouro : " + gold);

        if (equippedWeapon != null) {
            System.out.println("🗡️  Arma : " + equippedWeapon.getName());
        } else {
            System.out.println("🗡️  Arma : (nenhuma)");
        }

        System.out.println("🎯 Ponto fraco: " + weakPoint.getPtName());
        System.out.println("────────────────────────────────────────");
    }
    /**
     * Executa um combate completo contra um inimigo.
     *
     * @param enemy inimigo a combater
     * @return true se o herói vencer, false se morrer
     */
    public boolean attack(NPC enemy, Scanner scanner, Random random) {
        specialUsedThisFight = false;

        ConsoleFX.title("Combate ⚔️");
        System.out.println("👾 Inimigo: " + enemy.getName());
        ConsoleFX.pause(400);

        while (this.isAlive() && enemy.isAlive()) {
            ConsoleFX.section("Estado");

            System.out.println("🧍 Tu");
            System.out.println("   ❤️ Vida : " + currentHealth + "/" + maxHealth);
            System.out.println("   💪 Força: " + strength);
            System.out.println("   🎯 Ponto fraco: " + weakPoint.getPtName());

            System.out.println();

            System.out.println("👹 Inimigo");
            System.out.println("   ❤️ Vida : " + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth());

            ConsoleFX.section("Escolhe o ataque");
            ConsoleFX.option(1, "Ataque normal ⚔️");
            ConsoleFX.option(2, "Ataque especial ✨ (1x por combate)");
            ConsoleFX.option(3, "Consumível de combate 💣");
            ConsoleFX.prompt("Opção:");

            int choice = readInt(scanner);

            if (choice == 1) {
                Audio.playSfxAndWait("src/resources/audio/attack.wav", 3000);
                int weaponAtk = (equippedWeapon == null) ? 0 : equippedWeapon.getAttack();
                int damage = strength + weaponAtk;

                enemy.takeDamage(damage);

                System.out.println("\n>>> ATACASTE <<<");
                System.out.println("Dano causado: " + damage + "\n");
                ConsoleFX.pause(450);

            } else if (choice == 2) {
                if (specialUsedThisFight) {
                    System.out.println("\nJá usaste o ataque especial neste combate.\n");
                    ConsoleFX.pause(350);
                    continue;
                }

                Audio.playSfxAndWait("src/resources/audio/special.wav", 5000);

                int weaponSpecial = (equippedWeapon == null) ? 0 : equippedWeapon.getSpecialAttack();
                int damage = strength + weaponSpecial + getHeroSpecialBonus();

                System.out.println("\n>>> ATAQUE ESPECIAL <<<");
                ConsoleFX.pause(250);
                printSpecialDialogue();
                ConsoleFX.pause(250);
                System.out.println("Dano total: " + damage + "\n");

                enemy.takeDamage(damage);
                specialUsedThisFight = true;
                ConsoleFX.pause(450);

            } else if (choice == 3) {
                boolean used = useCombatConsumable(scanner, enemy);
                if (!used) {
                    System.out.println("\nNão tens consumíveis de combate.\n");
                    ConsoleFX.pause(350);
                    continue;
                }
                ConsoleFX.pause(350);

            } else {
                System.out.println("\nOpção inválida.\n");
                ConsoleFX.pause(300);
                continue;
            }

            if (!enemy.isAlive()) break;

            System.out.println(enemy.getName() + " prepara um ataque...");
            ConsoleFX.pause(650);

            int enemyDamage = enemy.getStrength();
            boolean hitWeak = random.nextInt(100) < 25;

            if (hitWeak) {
                Audio.playSfxAndWait("src/resources/audio/weakpoint.wav", 4000);
                enemyDamage *= 2;
                System.out.println("\n!!! PONTO FRACO ATINGIDO !!!");
                System.out.println("Local: " + weakPoint.getPtName());
                System.out.println("Tu: \"" + weakPoint.getHitPhrase() + "\"");
                System.out.println("Dano DUPLICADO!\n");
                ConsoleFX.pause(700);

            } else {
                Audio.playSfxAndWait("src/resources/audio/hurt.wav", 3000);            }

            this.takeDamage(enemyDamage);
            System.out.println(enemy.getName() + " atacou-te e causou " + enemyDamage + " de dano.\n");
            ConsoleFX.pause(450);
        }

        if (!this.isAlive()) {
            System.out.println("\nFoste derrotado/a...\n");
            Audio.playSfxAndWait("src/resources/audio/game_over.wav", 4000);
            return false;
        }

        // vitória
        System.out.println("================================");
        System.out.println("        COMBATE TERMINADO       ");
        System.out.println("================================");
        System.out.println("Vitória!\n");
        Audio.playSfxAndWait("src/resources/audio/victory.wav", 3000);        ConsoleFX.pause(900);

        // estado do herói após combate
        System.out.println("--- Estado do herói (após o combate) ---");
        System.out.println("Vida:  " + currentHealth + "/" + maxHealth);
        System.out.println("Força: " + strength);
        System.out.println("Ouro:  " + gold);
        System.out.println("---------------------------------------\n");
        ConsoleFX.pause(650);

        earnGold(enemy.getGoldReward());
        levelUp();

        System.out.println("\n--- Estado do herói (após level up) ---");
        System.out.println("Vida:  " + currentHealth + "/" + maxHealth);
        System.out.println("Força: " + strength);
        System.out.println("Ouro:  " + gold);
        System.out.println("================================\n");
        ConsoleFX.pause(650);

        return true;
    }

    /**
     * Apresenta a lista de consumíveis de combate disponíveis e permite usar um deles.
     * Remove o item do inventário e aplica o efeito no inimigo.
     *
     * @param scanner scanner para ler a escolha do utilizador
     * @param enemy inimigo que será afetado pelo consumível
     * @return true se um consumível foi usado, false se não existirem consumíveis ou se o utilizador cancelar
     */
    private boolean useCombatConsumable(Scanner scanner, NPC enemy) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof CombatConsumable) indexes.add(i);
        }
        if (indexes.isEmpty()) return false;

        System.out.println("\nConsumíveis de combate:");
        for (int k = 0; k < indexes.size(); k++) {
            int idx = indexes.get(k);
            System.out.print((k + 1) + ") ");
            inventory.get(idx).showDetails();
        }
        System.out.println("0) Cancelar");
        System.out.print("Escolha: ");

        int choice = readInt(scanner);
        if (choice == 0) return false;

        int pos = choice - 1;
        if (pos < 0 || pos >= indexes.size()) {
            System.out.println("Opção inválida.");
            ConsoleFX.pause(300);
            return false;
        }

        int realIndex = indexes.get(pos);
        Consumable item = inventory.remove(realIndex);

        Audio.playSfxAndWait("src/resources/audio/explosion.wav", 6000);
        item.use(this, enemy);
        ConsoleFX.pause(350);

        return true;
    }

    /**
     * Apresenta o menu de poções e permite usar uma poção do inventário.
     */
    public void usePotionMenu(Scanner scanner) {
        ArrayList<Integer> potionIndexes = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Potion) potionIndexes.add(i);
        }

        if (potionIndexes.isEmpty()) {
            System.out.println("Não tens poções no inventário.");
            ConsoleFX.pause(350);
            return;
        }

        System.out.println("\nPoções no inventário:");
        for (int k = 0; k < potionIndexes.size(); k++) {
            int idx = potionIndexes.get(k);
            System.out.print((k + 1) + ") ");
            inventory.get(idx).showDetails();
        }
        System.out.println("0) Não usar nada");
        System.out.print("Escolha: ");

        int choice = readInt(scanner);
        if (choice == 0) return;

        int pos = choice - 1;
        if (pos < 0 || pos >= potionIndexes.size()) {
            System.out.println("Opção inválida.");
            ConsoleFX.pause(300);
            return;
        }

        int realIndex = potionIndexes.get(pos);
        Potion p = (Potion) inventory.get(realIndex);

        // aviso de excesso de cura
        int possible = currentHealth + p.getHealAmount();
        if (p.getHealAmount() > 0 && possible > maxHealth) {
            int excess = possible - maxHealth;
            System.out.println("\nAtenção: vais desperdiçar " + excess + " de cura (excesso).");
            ConsoleFX.pause(350);
            System.out.println("Queres usar na mesma?");
            System.out.println("1) Sim");
            System.out.println("2) Não");
            System.out.print("Opção: ");
            int confirm = readInt(scanner);
            if (confirm != 1) {
                System.out.println("Não usaste a poção.");
                ConsoleFX.pause(300);
                return;
            }
        }

        inventory.remove(realIndex);
        Audio.playSfxAndWait("src/resources/audio/potion.wav", 6000);
        p.use(this, null);
        ConsoleFX.pause(250);
        printStatus();
        ConsoleFX.pause(800);
    }

    /**
     * Lê um número inteiro da consola de forma segura, repetindo o pedido até ser válido.
     *
     * @param scanner scanner a utilizar para leitura
     * @return inteiro introduzido pelo utilizador
     */
    protected int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Opção: ");
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}