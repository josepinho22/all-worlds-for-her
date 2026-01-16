package jogo;

import audio.Audio;
import entidades.*;
import itens.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * Classe principal que gere o fluxo do jogo.
 * Responsável por iniciar o jogo, criar o herói, construir o labirinto e controlar a progressão entre salas.
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private Hero hero;
    private Vendor vendor;

    private final ArrayList<Room> allRooms = new ArrayList<Room>();
    private Room currentRoom;

    /**
     * Inicia o jogo: mostra a introdução, cria o herói, prepara o inventário inicial,
     * constrói o labirinto e entra no ciclo principal do jogo.
     */
    public void startGame() {
        Audio.playSfxAndWait("src/resources/audio/game_start.wav", 2000);

        ConsoleFX.title("All Worlds For Her");
        System.out.println("🌌 Bem-vindo/a ao labirinto entre mundos.");
        ConsoleFX.pause(300);
        System.out.println("💊 A cura existe... mas não no teu mundo.");
        ConsoleFX.pause(500);

        hero = createHero();
        hero.showIntro();

        hero.equipWeapon(new Weapon("Punhos", 0, 0, 0, new ArrayList<String>()));
        hero.addConsumable(new Potion("Poção pequena", 0, 15, 0, new ArrayList<String>()));

        buildMaze();
        playMaze();
    }

    /**
     * Cria o herói do jogador, permitindo escolher a personagem, dificuldade e distribuição de pontos.
     *
     * @return herói criado com atributos e ouro iniciais
     */
    private Hero createHero() {
        ConsoleFX.section("Escolha da Personagem");
        ConsoleFX.option(1, "Enfermeira 🏥");
        ConsoleFX.option(2, "Estafeta 📦");
        ConsoleFX.option(3, "Professora 📚");
        ConsoleFX.prompt("Opção:");
        int heroChoice = readInt(scanner);

        ConsoleFX.section("Dificuldade");
        ConsoleFX.option(1, "Fácil 🟢  (300 pontos · 20 ouro)");
        ConsoleFX.option(2, "Difícil 🔴 (220 pontos · 15 ouro)");
        ConsoleFX.prompt("Opção:");
        int diff = readInt(scanner);

        int totalPoints = (diff == 1) ? 300 : 220;
        int gold = (diff == 1) ? 20 : 15;

        System.out.print("\nNome da personagem: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) name = "Herói";

        int maxHealth = 0;
        int strength = 0;

        while (true) {
            int points = totalPoints;
            maxHealth = 0;
            strength = 0;

            System.out.println("\nComo queres distribuir os pontos?");
            System.out.println("1) Manual (escrever valores)");
            System.out.println("2) Incremental (um a um)");
            System.out.println("3) Automático (Defensivo / Agressivo / Balanceado)");
            System.out.print("Opção: ");
            int mode = readInt(scanner);

            if (mode == 1) {
                while (true) {
                    System.out.println("\nPontos disponíveis: " + points);
                    System.out.println("Regra: Vida custa 1 ponto | Força custa 5 pontos");
                    System.out.print("Quantos pontos queres colocar em VIDA? (0 a " + points + "): ");
                    int addHealth = readInt(scanner);

                    if (addHealth < 0 || addHealth > points) {
                        System.out.println("Valor inválido.");
                        continue;
                    }

                    int remaining = points - addHealth;
                    int maxStrengthPossible = remaining / 5;

                    System.out.print("Quantos pontos de FORÇA queres colocar? (0 a " + maxStrengthPossible + "): ");
                    int addStrength = readInt(scanner);

                    if (addStrength < 0 || addStrength > maxStrengthPossible) {
                        System.out.println("Valor inválido.");
                        continue;
                    }

                    maxHealth = addHealth;
                    strength = addStrength;

                    points = points - addHealth - (addStrength * 5);

                    if (points == 0) break;

                    System.out.println("\nAinda sobraram " + points + " pontos. Tens de gastar tudo.");
                    System.out.println("Dica: coloca o resto em VIDA.");
                }

            } else if (mode == 2) {
                while (points > 0) {
                    System.out.println("\nPontos disponíveis: " + points);
                    System.out.println("Vida: " + maxHealth + " | Força: " + strength);
                    System.out.println("1) +Vida (custa 1 ponto)");
                    System.out.println("2) +Força (custa 5 pontos)");
                    System.out.print("Opção: ");

                    int choice = readInt(scanner);

                    if (choice == 1) {
                        maxHealth += 1;
                        points -= 1;
                    } else if (choice == 2) {
                        if (points < 5) {
                            System.out.println("Não tens pontos suficientes para força.");
                        } else {
                            strength += 1;
                            points -= 5;
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }

            } else if (mode == 3) {
                System.out.println("\nEscolhe o estilo automático:");
                System.out.println("1) Defensivo (mais vida)");
                System.out.println("2) Agressivo (mais força)");
                System.out.println("3) Balanceado");
                System.out.print("Opção: ");
                int style = readInt(scanner);

                double healthRatio = (style == 1) ? 0.55 : (style == 2) ? 0.20 : 0.35;

                int baseHealth = (int) Math.round(points * healthRatio);
                if (baseHealth > points) baseHealth = points;

                int remaining = points - baseHealth;
                int autoStrength = remaining / 5;
                int leftover = remaining % 5;

                maxHealth = baseHealth + leftover;
                strength = autoStrength;
                points = 0;

                String styleName = (style == 1) ? "Defensivo" : (style == 2) ? "Agressivo" : "Balanceado";
                System.out.println("\nDistribuição automática (" + styleName + "):");
                System.out.println("Vida atribuída: " + maxHealth);
                System.out.println("Força atribuída: " + strength);
                ConsoleFX.pause(700);

            } else {
                System.out.println("Opção inválida.");
                continue;
            }

            break;
        }

        Hero h;
        if (heroChoice == 1) h = new Nurse(name, maxHealth, strength, gold);
        else if (heroChoice == 2) h = new Courier(name, maxHealth, strength, gold);
        else h = new Teacher(name, maxHealth, strength, gold);

        System.out.println("\nPersonagem criada!");
        System.out.println("Vida: " + h.getMaxHealth() + " | Força: " + h.getStrength() + " | Ouro: " + h.getGold());
        System.out.println("Ponto fraco marcado: " + h.getWeakPoint().getPtName());
        ConsoleFX.pause(900);

        return h;
    }

    /**
     * Constrói todas as salas do jogo, define eventos, inimigos, recompensas e ligações (grafo),
     * e posiciona o jogador na sala inicial.
     */
    private void buildMaze() {
        allRooms.clear();

        ArrayList<String> allHeroes = new ArrayList<String>();

        ArrayList<Item> stock = new ArrayList<Item>();
        stock.add(new Potion("Poção de Vida", 15, 30, 0, allHeroes));
        stock.add(new Potion("Poção Média", 25, 45, 0, allHeroes));
        stock.add(new Potion("Poção Grande", 40, 70, 0, allHeroes));
        stock.add(new Potion("Poção de Força", 20, 0, 2, allHeroes));
        stock.add(new Potion("Poção de Força II", 35, 0, 4, allHeroes));
        stock.add(new CombatConsumable("Bomba de Dano", 18, 25, allHeroes));
        stock.add(new CombatConsumable("Granada Improvisada", 30, 40, allHeroes));
        stock.add(new Weapon("Bastão", 18, 3, 6, allHeroes));
        stock.add(new Weapon("Faca", 20, 4, 7, allHeroes));

        vendor = new Vendor(stock);

        Room entrance = new Room("Entrada do Labirinto");

        Room shop = new Room("Loja do Mercador");
        shop.setShopRoom(true);

        Room horse = new Room("Travessia do Cavalo");
        horse.setRiskRoom(new RiskEvent(
                "Cavalo",
                "Uma ravina bloqueia o caminho. O cavalo treme sob os teus pés.",
                0.10, 0.75
        ));

        Room teenWolf = new Room("Bosque de Beacon Hills");
        teenWolf.setCombatRoom(
                "Scott McCall",
                "Não deixes o medo controlar-te. Protege o teu ponto fraco e avança.",
                "A lua escolheu-te para cair hoje.",
                new NPC("Nogitsune", 95, 12, 25),
                new Potion("Poção de Vida (Teen Wolf)", 0, 35, 0, allHeroes)
        );

        Room vampire = new Room("Galeria das Sombras");
        vampire.setCombatRoom(
                "Bonnie Bennett",
                "Mantém a mente fria. O inimigo vai tentar atingir o teu ponto fraco.",
                "A tua esperança é a primeira coisa que eu mato.",
                new NPC("Klaus Mikaelson", 120, 16, 35),
                new Potion("Poção de Força (Vampiros)", 0, 0, 3, allHeroes)
        );

        Room bike = new Room("Travessia da Mota");
        bike.setRiskRoom(new RiskEvent(
                "Mota",
                "A ponte está a cair. A mota vibra. Tens segundos para decidir.",
                0.10, 0.75
        ));

        Room arrow = new Room("Rooftop de Star City");
        arrow.setCombatRoom(
                "Oliver Queen",
                "Escolhe o alvo e termina. Não dês espaço ao inimigo.",
                "Eu vou devolver-te tudo… em dor.",
                new NPC("Slade Wilson", 140, 18, 45),
                new Potion("Poção Média (Star City)", 0, 55, 0, allHeroes)
        );

        Room spider = new Room("Cidade Fragmentada");
        spider.setCombatRoom(
                "Miles Morales",
                "Coragem é agir apesar do medo. Protege-te e continua.",
                "Eu vou esmagar o que te resta.",
                new NPC("Kingpin", 160, 20, 55),
                new Potion("Poção de Vida (Spider)", 0, 50, 0, allHeroes)
        );

        Room hippo = new Room("Travessia do Hipopótamo");
        hippo.setRiskRoom(new RiskEvent(
                "Hipopótamo",
                "Um rio dimensional ruge. Um hipopótamo surge como única passagem possível.",
                0.10, 0.75
        ));

        Room core = new Room("Núcleo do Labirinto (Boss)");
        core.setCombatRoom(
                "Voz do Labirinto",
                "Ele engana com a cara mais calma do mundo. Não acredites em nada.",
                "Eu engano, eu viro, eu parto.",
                new NPC("Loki", 180, 22, 0),
                null
        );

        // ligações por nomes (grafo)
        entrance.addConnection("Loja do Mercador");
        entrance.addConnection("Travessia do Cavalo");

        shop.addConnection("Travessia do Cavalo");

        horse.addConnection("Bosque de Beacon Hills");
        horse.addConnection("Galeria das Sombras");

        teenWolf.addConnection("Travessia da Mota");
        vampire.addConnection("Travessia da Mota");

        bike.addConnection("Rooftop de Star City");
        bike.addConnection("Cidade Fragmentada");

        arrow.addConnection("Travessia do Hipopótamo");
        spider.addConnection("Travessia do Hipopótamo");

        hippo.addConnection("Núcleo do Labirinto (Boss)");

        allRooms.add(entrance);
        allRooms.add(shop);
        allRooms.add(horse);
        allRooms.add(teenWolf);
        allRooms.add(vampire);
        allRooms.add(bike);
        allRooms.add(arrow);
        allRooms.add(spider);
        allRooms.add(hippo);
        allRooms.add(core);

        currentRoom = entrance;
    }

    /**
     * Ciclo principal do labirinto: executa a sala atual, trata derrota e vitória,
     * permite uso de poções e move o herói para a próxima sala escolhida.
     */
    private void playMaze() {
        while (true) {
            boolean ok = currentRoom.play(this, hero, scanner, random);

            if (!ok) {
                Audio.playSfxAndWait("src/resources/audio/game_over.wav", 4000);

                int option = gameOverMenu();
                if (option == 1) {
                    hero.setCurrentHealth(hero.getMaxHealth());
                    currentRoom = findRoomByName("Entrada do Labirinto");
                    continue;
                } else if (option == 2) {
                    hero = createHero();
                    hero.showIntro();
                    hero.equipWeapon(new Weapon("Punhos", 0, 0, 0, new ArrayList<String>()));
                    hero.addConsumable(new Potion("Poção pequena", 0, 15, 0, new ArrayList<String>()));
                    buildMaze();
                    continue;
                } else {
                    System.out.println("Até à próxima.");
                    return;
                }
            }

            if (currentRoom.getRoomName().equalsIgnoreCase("Núcleo do Labirinto (Boss)")) {
                Audio.playSfxAndWait("src/resources/audio/game_win.wav", 4000);

                System.out.println("\nA cura finalmente existe.");
                System.out.println("=== VITÓRIA ===");
                return;
            }

            System.out.println("\nAntes de seguires, queres usar uma poção?");
            hero.usePotionMenu(scanner);

            currentRoom.printConnections();
            System.out.print("Para onde queres ir? ");
            int choice = readInt(scanner);

            ArrayList<String> exits = currentRoom.getConnectedRoomNames();
            int idx = choice - 1;

            if (idx < 0 || idx >= exits.size()) {
                System.out.println("Escolha inválida. Ficas onde estás.");
                ConsoleFX.pause(400);
                continue;
            }

            String nextName = exits.get(idx);
            Room next = findRoomByName(nextName);

            if (next == null) {
                System.out.println("Erro: sala não encontrada (" + nextName + ").");
                ConsoleFX.pause(400);
                continue;
            }

            Audio.playSfxAndWait("src/resources/audio/door.wav", 5000);
            currentRoom = next;
        }
    }

    /**
     * Procura uma sala pelo seu nome.
     *
     * @param name nome da sala a procurar
     * @return sala encontrada ou null se não existir
     */
    private Room findRoomByName(String name) {
        for (int i = 0; i < allRooms.size(); i++) {
            Room r = allRooms.get(i);
            if (r.getRoomName().equalsIgnoreCase(name)) return r;
        }
        return null;
    }

    /**
     * Abre a loja do mercador para o herói atual.
     *
     * @param scanner scanner para leitura das opções do utilizador
     * @param random gerador de números aleatórios
     */
    public void openShop(Scanner scanner, Random random) {
        vendor.openShop(hero, scanner, random);
    }

    /**
     * Mostra o menu de fim de jogo e devolve a opção escolhida.
     *
     * @return 1 para reiniciar com a mesma personagem, 2 para reiniciar com nova personagem, 3 para sair
     */
    private int gameOverMenu() {
        System.out.println("\n=== FIM DE JOGO ===");
        System.out.println("1) Jogar novamente (mesma personagem)");
        System.out.println("2) Jogar novamente (nova personagem)");
        System.out.println("3) Fechar");
        System.out.print("Opção: ");
        int choice = readInt(scanner);
        if (choice < 1 || choice > 3) choice = 3;
        return choice;
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