package jogo;

import audio.Audio;

import java.util.Random;
import java.util.Scanner;

/**
 * Representa um evento de risco durante a progressão no labirinto.
 * O jogador deve escolher entre duas opções com diferentes probabilidades de morte.
 */
public class RiskEvent {

    private final String transportName;
    private final String description;
    private final double slowDeathChance; // ex: 0.10
    private final double fastDeathChance; // ex: 0.75

    /**
     * Cria um evento de risco associado a um meio de transporte.
     *
     * @param transportName nome do meio de transporte
     * @param description descrição narrativa do evento
     * @param slowDeathChance probabilidade de morte ao escolher ir devagar
     * @param fastDeathChance probabilidade de morte ao escolher ir depressa
     */
    public RiskEvent(String transportName, String description, double slowDeathChance, double fastDeathChance) {
        this.transportName = transportName;
        this.description = description;
        this.slowDeathChance = slowDeathChance;
        this.fastDeathChance = fastDeathChance;
    }

    /**
     * Executa o evento de risco, apresentando opções ao jogador
     * e determinando o resultado com base em probabilidades.
     *
     * @param scanner scanner para leitura das escolhas do utilizador
     * @param random gerador de números aleatórios
     * @return true se o jogador sobreviver, false se morrer
     */
    public boolean execute(Scanner scanner, Random random) {
        ConsoleFX.title("Evento de Risco 🧨");
        System.out.println("🚗 Transporte: " + transportName);

        System.out.println(description);
        ConsoleFX.pause(500);

        ConsoleFX.section("Decisão");
        ConsoleFX.option(1, "Ir mais devagar 🐢");
        ConsoleFX.option(2, "Ir mais depressa ⚡");
        ConsoleFX.prompt("Opção:");
        int choice = readInt(scanner);

        System.out.println("\nA travessia começa...");
        Audio.playSfxAndWait("src/resources/audio/transition.wav", 4000);
        double roll = random.nextDouble();

        if (choice == 1) {
            if (roll < slowDeathChance) {
                System.out.println("Caíste durante a travessia... foi fatal.");
                Audio.playSfxAndWait("src/resources/audio/game_over.wav", 4000);
                return false;
            }
            System.out.println("Passaste com cuidado e sobreviveste.");
            ConsoleFX.pause(500);
            return true;
        }

        if (choice == 2) {
            if (roll < fastDeathChance) {
                System.out.println("Foste rápido demais... e isso custou-te a vida.");
                Audio.playSfxAndWait("src/resources/audio/game_over.wav", 4000);
                return false;
            }
            System.out.println("Contra todas as probabilidades, passaste vivo.");
            ConsoleFX.pause(500);
            return true;
        }

        System.out.println("Opção inválida. Hesitaste... mas passaste por milagre.");
        ConsoleFX.pause(400);
        return true;
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