package jogo;

import audio.Audio;
import entidades.Hero;
import entidades.NPC;

import itens.Potion;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Representa uma sala do labirinto.
 * Cada sala tem um nome, ligações (grafo por nomes) e pode ser de diferentes tipos:
 * loja, risco ou combate.
 */
public class Room {

    private final String roomName;
    private final ArrayList<String> connectedRoomNames;

    private boolean cleared;

    // tipos de sala
    private boolean shopRoom;
    private boolean riskRoom;
    private RiskEvent riskEvent;

    // conteúdo (sala de combate)
    private String allyName;
    private String allyDialogue;
    private String enemyDialogue;
    private NPC enemy;
    private Potion rewardPotion;

    /**
     * Cria uma sala com um nome e inicializa a lista de ligações.
     *
     * @param roomName nome da sala
     */
    public Room(String roomName) {
        this.roomName = roomName;
        this.connectedRoomNames = new ArrayList<>();
        this.cleared = false;
    }

    /**
     * Devolve o nome da sala.
     *
     * @return nome da sala
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Devolve a lista de nomes das salas ligadas a esta sala.
     *
     * @return lista de nomes das salas conectadas
     */
    public ArrayList<String> getConnectedRoomNames() {
        return connectedRoomNames;
    }

    /**
     * Adiciona uma ligação desta sala para outra sala, identificada pelo nome.
     *
     * @param roomName nome da sala de destino
     */
    public void addConnection(String roomName) {
        connectedRoomNames.add(roomName);
    }

    /**
     * Define se esta sala é uma sala de loja.
     *
     * @param value true para marcar como loja, false caso contrário
     */
    public void setShopRoom(boolean value) {
        this.shopRoom = value;
    }

    /**
     * Configura a sala como sala de risco, associando um evento de risco.
     *
     * @param event evento de risco a executar ao entrar na sala
     */
    public void setRiskRoom(RiskEvent event) {
        this.riskRoom = true;
        this.riskEvent = event;
    }

    /**
     * Configura a sala como sala de combate, definindo aliado, diálogos, inimigo e recompensa.
     *
     * @param allyName nome do aliado presente na sala
     * @param allyDialogue fala do aliado
     * @param enemyDialogue fala do inimigo
     * @param enemy inimigo a combater
     * @param rewardPotion poção de recompensa (pode ser null)
     */
    public void setCombatRoom(String allyName, String allyDialogue, String enemyDialogue, NPC enemy, Potion rewardPotion) {
        this.allyName = allyName;
        this.allyDialogue = allyDialogue;
        this.enemyDialogue = enemyDialogue;
        this.enemy = enemy;
        this.rewardPotion = rewardPotion;
    }

    /**
     * Executa a lógica da sala atual.
     * Pode correr um evento de risco, abrir a loja ou iniciar um combate.
     *
     * @param game instância do jogo (para acesso à loja)
     * @param hero herói do jogador
     * @param scanner scanner para ler escolhas do utilizador
     * @param random gerador de números aleatórios
     * @return true se o jogador sobreviver/continuar, false se morrer
     */
    public boolean play(Game game, Hero hero, Scanner scanner, Random random) {
        System.out.println("\n\n########################################");
        System.out.println("SALA: " + roomName.toUpperCase());
        System.out.println("########################################\n");
        ConsoleFX.pause(250);

        if (riskRoom) {
            return riskEvent.execute(scanner, random);
        }

        if (shopRoom) {
            System.out.println("Um mercador aparece entre portais cintilantes...");
            Audio.playSfxAndWait("src/resources/audio/shop.wav", 3000);

            game.openShop(scanner, random);

            cleared = true;
            return true;
        }

        if (cleared) {
            System.out.println("A sala está silenciosa. Já não há nada aqui.");
            ConsoleFX.pause(350);
            return true;
        }

        if (allyName != null && !allyName.isBlank()) {
            System.out.println(allyName + ": \"" + allyDialogue + "\"\n");
            ConsoleFX.pause(700);
        }

        if (enemy == null) {
            System.out.println("Não há inimigos nesta sala.");
            cleared = true;
            ConsoleFX.pause(350);
            return true;
        }

        System.out.println(enemy.getName() + ": \"" + enemyDialogue + "\"\n");
        ConsoleFX.pause(700);

        boolean won = hero.attack(enemy, scanner, random);
        if (!won) return false;

        ConsoleFX.pause(300);

        if (rewardPotion != null) {
            System.out.println("\nRecompensa encontrada: " + rewardPotion.getName());
            rewardPotion.showDetails();
            ConsoleFX.pause(200);

            System.out.println("\n1) Usar agora");
            System.out.println("2) Guardar no inventário");
            System.out.print("Opção → ");
            int c = readInt(scanner);

            if (c == 1) {
                Audio.playSfxAndWait("src/resources/audio/potion.wav", 5000);
                ConsoleFX.pause(250);
                hero.printStatus();
                ConsoleFX.pause(800);
            } else {
                hero.addConsumable(rewardPotion);
                System.out.println("Guardaste no inventário.");
            }
            ConsoleFX.pause(450);
        }

        cleared = true;
        return true;
    }

    /**
     * Mostra na consola as saídas disponíveis desta sala (ligações do grafo).
     */
    public void printConnections() {
        System.out.println("\n--- Saídas disponíveis ---");
        for (int i = 0; i < connectedRoomNames.size(); i++) {
            System.out.println((i + 1) + ") " + connectedRoomNames.get(i));
        }
        System.out.println();
    }

    /**
     * Lê um número inteiro da consola de forma segura.
     *
     * @param scanner scanner a utilizar para leitura
     * @return inteiro introduzido pelo utilizador
     */
    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Opção → ");
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}