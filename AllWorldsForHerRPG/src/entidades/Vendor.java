package entidades;

import audio.Audio;
import itens.Consumable;
import itens.Item;
import itens.Weapon;
import jogo.ConsoleFX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Representa o mercador do jogo.
 * Gera ofertas aleatórias e permite ao herói comprar armas e consumíveis.
 */
public class Vendor {
    /**
     * Cria um mercador com um stock base de itens.
     *
     * @param stock lista de itens disponíveis para venda
     */
    private final ArrayList<Item> stock;

    public Vendor(ArrayList<Item> stock) {
        this.stock = new ArrayList<Item>(stock);
    }
    /**
     * Gera uma lista de itens aleatórios permitidos para o herói.
     * A oferta tem no máximo 10 itens.
     *
     * @param hero herói que vai ver a oferta
     * @param random gerador de números aleatórios
     * @return lista de itens disponíveis para compra
     */
    public ArrayList<Item> getRandomOffer(Hero hero, Random random) {
        ArrayList<Item> copy = new ArrayList<Item>(stock);
        Collections.shuffle(copy, random);

        ArrayList<Item> offer = new ArrayList<Item>();
        for (int i = 0; i < copy.size(); i++) {
            Item it = copy.get(i);
            if (it.isAllowedFor(hero)) offer.add(it);
            if (offer.size() == 10) break;
        }
        return offer;
    }
    /**
     * Abre o menu da loja e permite ao herói comprar itens com ouro.
     * Itens comprados podem ser equipados (armas) ou colocados no inventário (consumíveis).
     *
     * @param hero herói que interage com a loja
     * @param scanner scanner para leitura das opções do utilizador
     * @param random gerador de números aleatórios para gerar a oferta
     */
    public void openShop(Hero hero, Scanner scanner, Random random) {
        while (true) {
            ArrayList<Item> offer = getRandomOffer(hero, random);

            ConsoleFX.title("Loja do Mercador 🛒");
            System.out.println("💰 Ouro disponível: " + hero.getGold());
            ConsoleFX.section("Itens à venda");

            for (int i = 0; i < offer.size(); i++) {
                System.out.print("🛍️  " + (i + 1) + ") ");
                offer.get(i).showDetails();
            }

            System.out.println("🚪 0) Sair");
            ConsoleFX.prompt("Escolhe um item: ");
            int choice = readInt(scanner);

            if (choice == 0) {
                System.out.println("Saíste da loja.");
                ConsoleFX.pause(500);
                return;
            }

            int idx = choice - 1;
            if (idx < 0 || idx >= offer.size()) {
                System.out.println("Opção inválida.");
                ConsoleFX.pause(400);
                continue;
            }

            Item item = offer.get(idx);

            if (!hero.spendGold(item.getPrice())) {
                System.out.println("Não tens ouro suficiente.");
                ConsoleFX.pause(450);
                continue;
            }

            Audio.playSfxAndWait("src/resources/audio/buy.wav", 3000);
            if (item instanceof Weapon) {
                hero.equipWeapon((Weapon) item);
                System.out.println("Compraste e equipaste: " + item.getName());
                ConsoleFX.pause(500);
            } else if (item instanceof Consumable) {
                hero.addConsumable((Consumable) item);
                System.out.println("Compraste: " + item.getName() + " (inventário)");
                ConsoleFX.pause(500);
            } else {
                System.out.println("Item comprado.");
                ConsoleFX.pause(350);
            }
        }
    }
    /**
     * Lê um número inteiro da consola de forma segura, repetindo o pedido até ser válido.
     *
     * @param scanner scanner a utilizar para leitura
     * @return inteiro introduzido pelo utilizador
     */
    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Opção: ");
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}