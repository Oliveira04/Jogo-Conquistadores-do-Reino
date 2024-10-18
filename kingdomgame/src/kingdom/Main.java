//Augusto Coimbra de Oliveira - 2136390
//Augusto Coimbra de Oliveira - 2136390
//Joshua Lorenzo de Souza - 2158087
//Luís Felipe Rotondo Kobelnik - 2125543

package kingdom;

import kingdom.utils.Type;
import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;

// Fighters
import kingdom.characters.fighters.Fighter;
import kingdom.characters.fighters.Arcan;
import kingdom.characters.fighters.Archer;
import kingdom.characters.fighters.Warrior;

// Monsters
import kingdom.characters.monsters.Monster;
import kingdom.characters.monsters.Witch;
import kingdom.characters.monsters.Xama;
import kingdom.characters.monsters.Zombie;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    Type type = new Type();

    ArrayList<Arcan> arcans = new ArrayList<Arcan>();
    ArrayList<Archer> archers = new ArrayList<Archer>();
    ArrayList<Warrior> warriors = new ArrayList<Warrior>();

    int pedra = 0;
    int madeira = 0;
    int comida = 0;
    int populacao = 0;
    int dinheiro = 250;
    int pedraColetada = 0;
    int madeiraColetada = 0;
    int comidaColetada = 0;
    int dinheiroColetado = 0;
    private boolean quartelConstruido = false;
    private boolean bancoConstruido = false;
    private boolean pontoDoLenhadorConstruido = false;
    private boolean fazendaConstruida = false;
    private boolean mineradoraConstruida = false;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            if (mineradoraConstruida) {
                pedra += 5;
                Type.slowPrint("Produzindo recursos... (Recursos totais: " + pedra + ")\n",40);
            }

            if (fazendaConstruida) {
                comida += 5;
                Type.slowPrint("Produzindo recursos... (Recursos totais: " + comida + ")\n",40);
            }

            if (pontoDoLenhadorConstruido) {
                madeira += 5;
                Type.slowPrint("Produzindo recursos... (Recursos totais: " + madeira + ")\n",40);
            }

            if (bancoConstruido) {
                dinheiro += 5;
                Type.slowPrint("Coletando imposto... (Recursos totais: " + dinheiro + ")\n",40);
            }
        }
    };

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public static void imprimirMatriz(String[][] matriz) {
        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[0].length; y++) {
                if (matriz[x][y] != null) {
                    System.out.print(matriz[x][y]);
                } else {
                    System.out.print(" |   X   | ");
                }
            }
            System.out.println();
        }
    }

    public static int rolarDado(int sides) {
        Random random = new Random();
        return random.nextInt(sides)+1;
    }

    public void encontrouMonstro() {
        Monster monster;
        int monstroRandom = rolarDado(100);
        type.slowPrint("\nVocê encontrou ", 100);

        if (monstroRandom < 30) {
            monster = new Witch();
            type.slowPrint("uma Bruxa", 100);
        } else if (monstroRandom < 80) {
            monster = new Xama();
            type.slowPrint("um Xama", 100);
        } else {
            monster = new Zombie();
            type.slowPrint("um Zumbi", 100);
        }

        type.slowPrint(" enquanto explorava!", 100);
        if (this.arcans.size() + this.archers.size() + this.warriors.size() == 0) {
            type.slowPrint("\nInfelizmente você não tinha força suficiente para vencer e foi derrotado, capturado e morto", 100);
            System.exit(0);
        }
        type.slowPrint("\nPrepare-se para a batalha!", 100);
        type.slowPrint("\n\nVocê possui " + this.warriors.size() + " cavaleiros, " + this.archers.size() + " arqueiros e " + this.arcans.size() + " magos", 100);

        while (this.arcans.size() + this.archers.size() + this.warriors.size() != 0 && monster.getLife() > 0) {
            Fighter fighter;
            if (this.warriors.size() > 0) {
                fighter = this.warriors.getLast();
            } else if (this.archers.size() > 0) {
                fighter = this.archers.getLast();
            } else {
                fighter = this.arcans.getLast();
            }

            // Ataque do fighter
            int attackFighter = fighter.getLevel();
            int damageFighter = attackFighter - monster.getResistance();
            if (damageFighter < 0) {
                damageFighter = 0;
            }
            monster.setLife(monster.getLife() - damageFighter);
            monster.setResistance(monster.getResistance() - 1);
            if (monster.getResistance() > 0) {
                monster.setResistance(monster.getResistance() - 1);
            }

            if (monster.getLife() <= 0) {
                monster.setLife(0);
            }

            Type.slowPrint("\nNethras: Um " + fighter.getType() + " atacou o monstro, aplicando " + damageFighter + " de dano real, reduzindo sua vida para " + monster.getLife() + " e sua resistência para " + monster.getResistance(), 10);

            if (monster.getLife() == 0) {
                break;
            }

            // Ataque do monster
            int attackMonster = monster.getLevel();
            int damageMonster = attackMonster - fighter.getResistance();
            if (damageMonster < 0) {
                damageMonster = 0;
            }
            fighter.setLife(fighter.getLife() - damageMonster);
            if (fighter.getResistance() > 0) {
                fighter.setResistance(fighter.getResistance() - 1);
            }

            if (fighter.getLife() <= 0) {
                fighter.setLife(0);
            }

            Type.slowPrint("\nNethras: O " + monster.getType() + " atacou o " + fighter.getType() + ", aplicando " + damageMonster + " de dano real, reduzindo sua vida para " + fighter.getLife() + " e sua resistência para " + fighter.getResistance(), 10);

            if (fighter.getLife() == 0) {
                Type.slowPrint("\nNethras: O " + fighter.getType() + " que estava lutando foi jogar no vasco", 10);
            }

            switch (fighter.getType()) {
                case "Warrior":
                    this.warriors.removeLast();
                    if (fighter.getLife() > 0) {
                        this.warriors.add(new Warrior(fighter.getLife(), fighter.getResistance()));
                    }
                    break;
                case "Archer":
                    this.archers.removeLast();
                    if (fighter.getLife() > 0) {
                        this.archers.add(new Archer(fighter.getLife(), fighter.getResistance()));
                    }
                    break;
                case "Arcan":
                    this.arcans.removeLast();
                    if (fighter.getLife() > 0) {
                        this.arcans.add(new Arcan(fighter.getLife(), fighter.getResistance()));
                    }
                    break;
            }
        }

        if (monster.getLife() == 0) {
            Type.slowPrint("\nNethras: Suas tropas derrotaram o monstro! Parabens :)", 10);
        } else {
            Type.slowPrint("\nNethras: Você e suas tropas foram jogar no vasco\n", 10);
            Type.slowPrint("_________________________________________________________________________________________________________________________\n ", 20);
            Type.slowPrint("|                                                   ALERTA DO SISTEMA!                                             |\n ", 20);
            Type.slowPrint("|                                                   Você é muito noob                                              |\n",20);
            Type.slowPrint("_________________________________________________________________________________________________________________________", 20);
            System.exit(0);
        }
    }

    public void encontrarRecurso() {
        if(dinheiro < 10) {
            System.out.println("Pedimos desculpa senhor, mas você não possui moedas suficientes para explorar.");
        } else {
            int dadoResultado = rolarDado(25);
            int dadoRecurso = rolarDado(100);

            switch (dadoResultado) {
                case 1:
                    dinheiro -= 10;
                    int perdido = dadoRecurso;
                    if (madeira < perdido) {
                        perdido = madeira;
                        madeira = 0;
                    } else {
                        madeira -= perdido;
                    }
                    if (pedra < perdido) {
                        perdido = pedra;
                        pedra = 0;
                    } else {
                        pedra -= perdido;
                    }
                    if (comida < perdido) {
                        perdido = comida;
                        comida = 0;
                    } else {
                        comida -= perdido;
                    }
                    System.out.println("Mestre, enquanto você estava fora, o exército do Duque Supremo nos atacou e roubou parte de nossos recursos, nos ajude.");
                    if(dinheiro >= 10){
                        dinheiro -= 10;
                        madeira += dadoRecurso;
                        System.out.println("Você encontrou MADEIRA enquanto explorava!");
                    }
                    break;
                case 2:
                    if(dinheiro >= 10){
                        System.out.println("Você encontrou um ponto de coleta abandonado, mas está completamente vazio... parece que já passaram por aqui.");
                    }
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    dinheiro -= 10;
                    encontrouMonstro();
                    break;
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    dinheiro -= 10;
                    madeira += dadoRecurso;
                    System.out.println("Você encontrou MADEIRA enquanto explorava!");
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    dinheiro -= 10;
                    pedra += dadoRecurso;
                    System.out.println("Você encontrou PEDRA enquanto explorava!");
                    break;
                case 22:
                case 23:
                case 24:
                    dinheiro -= 10;
                    comida += dadoRecurso;
                    System.out.println("Você encontrou COMIDA enquanto explorava!");
                    break;
                case 25:
                    if(dinheiro >= 10){
                        dinheiro -= 10;
                        pedra = pedra + dadoRecurso;
                        comida = comida + dadoRecurso;
                        madeira = madeira + dadoRecurso;
                        System.out.println("Pelos deuses! Você encontrou um ponto de coleta abandonado, e por sorte há muitos recursos!");
                    }
                    break;
                default:
                    System.out.println("Você não encontrou nada por aqui, é melhor continuar explorando...");
            }
        }
    }

    private void sairDoJogo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Você deseja encerrar o jogo? (sim/não)");
        String confirmacao = scanner.nextLine().toLowerCase();
        if (confirmacao.equals("sim")) {
            System.out.println("Encerrando o jogo...");
            System.out.println("Nethras: Foi uma honra lutar ao seu lado, capitão... adeus!");
            System.exit(0);
        } else if (confirmacao.equals("não")) {
            System.out.println("Voltando ao jogo...");
        } else {
            System.out.println("Comando inválido. Digite 'sim' para encerrar o jogo ou 'não' para voltar ao jogo.");
            sairDoJogo();
        }
    }

    private void construirQuartel() {
        if(!quartelConstruido) {
            quartelConstruido = true;
            System.out.println("Quartel construído com sucesso!");
        } else {
            System.out.println("Você já possuir um quartel em seu Reino.");
        }
    }

    private void construirMineradora() {
        if (!mineradoraConstruida) {
            mineradoraConstruida = true;
            System.out.println("Mineradora construída!");
        } else {
            System.out.println("Você já construiu a mineradora.");
        }
    }
    private void coletarPedra() {
        if (mineradoraConstruida) {
            pedra += 100;
            System.out.println("Recursos coletados! (Recursos totais: " + pedra + ")");
        } else {
            System.out.println("Construa a mineradora primeiro.");
        }
    }

    private void construirFazenda() {
        if (!fazendaConstruida) {
            fazendaConstruida = true;
            System.out.println("Fazenda construída!");
        } else {
            System.out.println("Você já construiu a fazenda.");
        }
    }
    private void coletarComida() {
        if (fazendaConstruida) {
            comida += 100;
            System.out.println("Recursos coletados! (Recursos totais: " + comida + ")");
        } else {
            System.out.println("Construa a fazenda primeiro.");
        }
    }

    private void construirPontoLenhador() {
        if (!pontoDoLenhadorConstruido) {
            pontoDoLenhadorConstruido = true;
            System.out.println("Ponto do lenhador construído!");
        } else {
            System.out.println("Você já construiu a mineradora.");
        }
    }
    private void coletarMadeira() {
        if (pontoDoLenhadorConstruido) {
            madeira += 100;
            System.out.println("Recursos coletados! (Recursos totais: " + madeira + ")");
        } else {
            System.out.println("Construa o ponto do lenhador primeiro.");
        }
    }

    private void construirBanco() {
        if (!bancoConstruido) {
            bancoConstruido = true;
            System.out.println("Banco construído!");
        } else {
            System.out.println("Você já construiu o banco.");
        }
    }
    private void coletarBanco() {
        if (bancoConstruido) {
            dinheiro += 100;
            System.out.println("Recursos coletados! (Recursos totais: " + dinheiro + ")");
        } else {
            System.out.println("Construa o banco primeiro.");
        }
    }

    public void start() {
        this.arcans.add(new Arcan());
        this.archers.add(new Archer());
        this.archers.add(new Archer());
        this.warriors.add(new Warrior());
        this.warriors.add(new Warrior());
        this.warriors.add(new Warrior());

        Type.slowPrint("Consquistadores do reino!", 75);
        System.out.println(" ");
        Type.slowPrint("Sejam Bem-Vindos!", 75);
        System.out.println(" ");
        String[] opcs = {"start", "new game", "more"};
        String accept = Type.ask("Digite \"start\" para iniciar o jogo? ", opcs);
        if (accept.equals("start")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o nome do jogador: ");
            String playerName = scanner.nextLine();
            String[] skip = {"sim", "não"};
            String respSkip = Type.ask("Deseja pular a introdução? digite \"sim\" para pular, e \"não\" para que a história seja contada normalmente. ", skip);
            if (respSkip.equals("sim")) {
                System.out.println("Seu desejo é uma ordem, senhor... (mas a história ficou muito boa, tá?)\n");
            } else if (respSkip.equals("não")) {
                Type.slowPrint("\n???: Olá guerreiro(a) " + playerName + ", seja muito bem-vindo(a)!\n", 45);
                Type.slowPrint("???: Imagino que ainda possa estar um pouco atordoado da viagem, então vou explicar", 45);
                Type.slowPrint("......\n", 150);
                Type.slowPrint("???: Você acaba de ser invocado para outro mundo! Um plano existencial cheio de magia, reinos, cavaleiros e monstros!\n", 45);
                Type.slowPrint("???: Estamos a 24.600 anos após 2024, desde o primeiro Ciclo da chama.\n", 45);
                Type.slowPrint("???: À medida que a Primeira Chama se desvanece, Reinos Nobres começaram a entrar em conflitos políticos, buscando prosperar mais e mais ao controlar o poder das Chamas \n", 45);
                Type.slowPrint("???: E com isso o mundo desencadeou na Era das trevas.\n", 45);
                Type.slowPrint("???: Marcados por noites intermináveis, mortos-vivos desenfreados, tempo, espaço e realidade em colapso, terras desmoronando, pessoas se transformando em monstros e a escuridão que cobriu o planeta em que vivemos.\n", 45);
                Type.slowPrint("Nethras: Eu sou a Nethras, sua missão é salvar nosso mundo, impedindo que o Duque Supremo mate o líder dos Sylvaris, sendo ele, o Reino com a maior quantidade de riquezas em todo o planeta. \n", 45);
            }

            String[] opcsCham = {"sim", "não"};
            String convocacao = Type.ask("Nethras: Você aceitará o chamado divino? (sim/não) ", opcsCham);
            System.out.println(" ");
            if (convocacao.equals("não")) {
                Type.slowPrint("Nethras: Herói errado, tenha um bom dia, PRÓXIMO DA FILA?\n", 20);
                Type.slowPrint("Nethras: Eu falei que o candidato numero 23 era o mais indicado\n", 20);
                Type.slowPrint("Nethras: mas nunca me ouvem, eu vou é pedir demissão...\n", 20);
                Type.slowPrint("_________________________________________________________________________________________________________________________\n ", 20);
                Type.slowPrint("|                                                   ALERTA DO SISTEMA!                                             |\n ", 20);
                Type.slowPrint("|                                         Sua existência foi apagada por deus!                                     |\n",20);
                Type.slowPrint("_________________________________________________________________________________________________________________________", 20);

                System.exit(0);
            } else if (convocacao.equals("sim")) { // Todo o jogo acontecerá dentro deste escopo;
                Type.slowPrint("Nethras: Que bom que podemos contar com a sua ajuda!\n", 10);
                Type.slowPrint("Nethras: Para que tudo ocorra bem, você deve comandar o seu próprio reino e ser digno de fazer parte dos Reinos Nobres.\n", 10);
                Type.slowPrint("Nethras: Não se preocupe, eu estarei aqui para lhe auxiliar na jornada como um guerreiro... um líder... e um herói. Que a força esteja com você...\n", 10);
                Type.slowPrint(" \n", 10);
                Type.slowPrint("Nethras: Enfim... vamos começar com o básico, qual será o nome do seu Reino? ", 10);
                String kingdomName = scanner.nextLine();
                Type.slowPrint("Nethras: Nome interessante... digno de um verdadeiro reino poderoso!\n", 10);
                System.out.println(" ");

                String[][] matriz = new String[19][9];
                int posicaoX = matriz.length / 2;
                int posicaoY = matriz[0].length / 2;
                matriz[posicaoX][posicaoY] = " | Líder | ";

                imprimirMatriz(matriz);

                while (true) {
                    System.out.println(" ");
                    System.out.println("_________________________________________________________________________________________________________________________ ");
                    System.out.println("|                                                   CONQUISTADORES DO REINO                                             | ");
                    System.out.println("| Líder " + playerName + ", governante de " + kingdomName);
                    System.out.println("|_______________________________________________________________________________________________________________________| ");
                    System.out.println("| Recursos coletados:            | Comandos de movimento:               | Ações do jogador:                             | ");
                    System.out.println("|-----------------------------------------------------------------------------------------------------------------------| ");
                    System.out.println("| - Madeira:  " + madeira +  "                    W: cima                                Q: menu de construções           ");
                    System.out.println("| - Pedra:    " + pedra +    "                    A: esquerda                            E: coletar madeira (casa do lenhador) ");
                    System.out.println("| - População: " + populacao + "                    S: baixo                               R: coletar pedra (mineradora) ");
                    System.out.println("| - Comida:   " + comida +   "                    D: direita                             T: coletar comida (fazenda) ");
                    System.out.println("| - Dinheiro: $" + dinheiro + ",00                                                        F: coletar imposto (banco) ");
                    System.out.println("|_______________________________________________________________________________________________________________________| ");
                    System.out.println("| Exército:                                                                                                             | ");
                    System.out.println("| Z: treinar cavaleiros: " + this.warriors.size());
                    System.out.println("| X: treinar arqueiros: " + this.archers.size());
                    System.out.println("| C: treinar magos: " + this.arcans.size());
                    System.out.println("|_______________________________________________________________________________________________________________________| ");
                    System.out.print("-> ");
                    String comando = scanner.nextLine().toLowerCase();

                    switch (comando) {
                        case "z": {
                            System.out.println("Custo (comida): 140 |" + "Voce tem: " + comida);
                            System.out.println("Custo (dinheiro): 70 |" + "Voce tem: " + dinheiro);
                            System.out.println("Deseja recrutar um cavaleiro para seu exército? (sim/não)");
                            String confirmacao = scanner.nextLine().toLowerCase();
                            if(confirmacao.equals("sim")) {
                                if(comida >= 140 && dinheiro >= 70) {
                                    dinheiro = dinheiro - 70;
                                    comida = comida - 140;
                                    Type.slowPrint("Treinando cavaleiro...!\n", 50);
                                    Type.slowPrint("[||||||||||]", 200);
                                    System.out.println(" Cavaleiro treinado com sucesso!");
                                    this.warriors.add(new Warrior());
                                } else {
                                    System.out.println("Chefe, o senhor não possui recursos suficientes para recrutar esta tropa.");
                                }
                            } else if(confirmacao.equals("não")) {
                                imprimirMatriz(matriz);
                                continue;
                            }
                        }
                        case "x": {
                            System.out.println("Custo (comida): 70 |" + "Voce tem: " + comida);
                            System.out.println("Custo (dinheiro): 140 |" + "Voce tem: " + dinheiro);
                            System.out.println("Deseja recrutar um arqueiro para seu exército? (sim/não)");
                            String confirmacao = scanner.nextLine().toLowerCase();
                            if(confirmacao.equals("sim")) {
                                if(comida >= 70 && dinheiro >= 140) {
                                    dinheiro = dinheiro - 140;
                                    comida = comida - 70;
                                    Type.slowPrint("Treinando arqueiro...!\n", 50);
                                    Type.slowPrint("[||||||||||]", 150);
                                    System.out.println(" Arqueiro treinado com sucesso!");
                                    this.archers.add(new Archer());
                                } else {
                                    System.out.println("Chefe, o senhor não possui recursos suficientes para recrutar esta tropa.");
                                }
                            } else if(confirmacao.equals("não")) {
                                imprimirMatriz(matriz);
                                continue;
                            }
                        }
                        case "c": {
                            System.out.println("Custo (comida): 100 |" + "Voce tem: " + comida);
                            System.out.println("Custo (dinheiro): 100 |" + "Voce tem: " + dinheiro);
                            System.out.println("Deseja recrutar um mago para seu exército? (sim/não)");
                            String confirmacao = scanner.nextLine().toLowerCase();
                            if(confirmacao.equals("sim")) {
                                if(comida >= 100 && dinheiro >= 100) {
                                    dinheiro = dinheiro - 100;
                                    comida = comida - 100;
                                    Type.slowPrint("Treinando mago...!\n", 50);
                                    Type.slowPrint("[||||||||||]", 200);
                                    System.out.println(" Mago treinado com sucesso!");
                                    this.arcans.add(new Arcan());
                                } else {
                                    System.out.println("Chefe, o senhor não possui recursos suficientes para recrutar esta tropa.");
                                }
                            } else if(confirmacao.equals("não")) {
                                imprimirMatriz(matriz);
                                continue;
                            }
                        }
                        case "e":
                            for (int i = 0; i < matriz.length; i++) {
                                for (int j = 0; j < matriz[i].length; j++) {
                                    if (" | Lenha | ".equals(matriz[i][j])) {
                                        int madeiraProduzida = 5;
                                        madeiraColetada += madeiraProduzida;
                                    }
                                }
                            }
                            imprimirMatriz(matriz);
                            madeira += madeiraColetada;
                            Type.slowPrint("Recursos coletados! (Recursos totais: " + madeira + ")\n",45);
                            break;
                        case "r":
                            for (int i = 0; i < matriz.length; i++) {
                                for (int j = 0; j < matriz[i].length; j++) {
                                    if (" | Miner | ".equals(matriz[i][j])) {
                                        int pedrasProduzidas = 5;
                                        pedraColetada += pedrasProduzidas;
                                    }
                                }
                            }
                            imprimirMatriz(matriz);
                            pedra += pedraColetada;
                            Type.slowPrint("Recursos coletados! (Recursos totais: " + comida + ")\n",45);
                            break;
                        case "t":
                            for (int i = 0; i < matriz.length; i++) {
                                for (int j = 0; j < matriz[i].length; j++) {
                                    if (" |Fazenda| ".equals(matriz[i][j])) {
                                        int comidaProduzida = 5;
                                        comidaColetada += comidaProduzida;
                                    }
                                }
                            }
                            imprimirMatriz(matriz);
                            comida += comidaColetada;
                            Type.slowPrint("Recursos coletados! (Recursos totais: " + dinheiro + ")\n",45);
                            break;
                        case "f":
                            for (int i = 0; i < matriz.length; i++) {
                                for (int j = 0; j < matriz[i].length; j++) {
                                    if (" | Banco | ".equals(matriz[i][j])) {
                                        int dinheiroProduzido = 5;
                                        dinheiroColetado += dinheiroProduzido;
                                    }
                                }
                            }
                            imprimirMatriz(matriz);
                            dinheiro += dinheiroColetado;
                            Type.slowPrint("Recursos coletados! (Recursos totais: " + dinheiro + ")\n",45);
                            break;
                        case "w":
                            if (posicaoX > 0 && matriz[posicaoX - 1][posicaoY] == null) {
                                matriz[posicaoX][posicaoY] = null;
                                posicaoX--;
                                matriz[posicaoX][posicaoY] = " | Líder | ";
                                imprimirMatriz(matriz);
                                encontrarRecurso();
                            } else if (posicaoX == 0) {
                                System.out.println("Limite do mapa, não tem como ultrapassar essa linha.");
                            } else {
                                System.out.println("Você não pode avançar para uma célula que já possui uma construção.");
                            }
                            break;
                        case "s":
                            if (posicaoX < matriz.length - 1 && matriz[posicaoX + 1][posicaoY] == null) {
                                matriz[posicaoX][posicaoY] = null;
                                posicaoX++;
                                matriz[posicaoX][posicaoY] = " | Líder | ";
                                imprimirMatriz(matriz);
                                encontrarRecurso();
                            } else if (posicaoX == matriz.length - 1) {
                                System.out.println("Limite do mapa, não tem como ultrapassar essa linha.");
                            } else {
                                System.out.println("Você não pode avançar para uma célula que já possui uma construção.");
                            }
                            break;
                        case "a":
                            if (posicaoY > 0 && matriz[posicaoX][posicaoY - 1] == null) {
                                matriz[posicaoX][posicaoY] = null;
                                posicaoY--;
                                matriz[posicaoX][posicaoY] = " | Líder | ";
                                imprimirMatriz(matriz);
                                encontrarRecurso();
                            } else if (posicaoY == 0) {
                                System.out.println("Limite do mapa, não tem como ultrapassar essa linha.");
                            } else {
                                System.out.println("Você não pode avançar para uma célula que já possui uma construção.");
                            }
                            break;
                        case "d":
                            if (posicaoY < matriz[0].length - 1 && matriz[posicaoX][posicaoY + 1] == null) {
                                matriz[posicaoX][posicaoY] = null;
                                posicaoY++;
                                matriz[posicaoX][posicaoY] = " | Líder | ";
                                imprimirMatriz(matriz);
                                encontrarRecurso();
                            } else if (posicaoY == matriz[0].length - 1) {
                                System.out.println("Limite do mapa, não tem como ultrapassar essa linha.");
                            } else {
                                System.out.println("Você não pode avançar para uma célula que já possui uma construção.");
                                break;
                            }
                        case "q":
                            System.out.println("|-----------------------------------------------------------------------------------------| ");
                            System.out.println("| Construções:                                                                            | ");
                            System.out.println("| 1 - Muros                        | 4 - Ponto do lenhador;     | 7 - Casa pequena(+5);   | ");
                            System.out.println("| 2 - Quartel;                     | 5 - Mineradora;            | 8 - Casa média(+10);    | ");
                            System.out.println("| 3 - Centro principal do Reino;   | 6 - Fazenda;               | 9 - Mansão(+20);        | ");
                            System.out.println("|                                                                                         | ");
                            System.out.println("| 0 - Voltar                                                                              | ");
                            System.out.println("|-----------------------------------------------------------------------------------------| ");
                            System.out.println("| Informações Gerais:                                                                     | ");
                            System.out.println("| - Dinheiro: $" + dinheiro + ",00");
                            System.out.println("| - Madeira:  " + madeira);
                            System.out.println("| - Pedra:    " + pedra);
                            System.out.println("| - Comida:   " + comida );
                            System.out.println("| - População: " + populacao);
                            System.out.println("|_________________________________________________________________________________________| ");
                            System.out.print("-> ");
                            String comandoConstrucao = scanner.nextLine().toLowerCase();
                                switch(comandoConstrucao) {
                                    case "1":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 0 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 30 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 0 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 10 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if (pedra >= 30 && dinheiro >= 10) {
                                                    pedra = pedra - 30;
                                                    dinheiro = dinheiro - 10;
                                                    matriz[newX][newY] = " | Muro  | ";
                                                    Type.slowPrint("Construindo muro...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 200);
                                                    System.out.println(" Muro construído com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "2":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 0 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 50 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 50 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 40 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 50 && madeira >= 50 && dinheiro >= 40) {
                                                    pedra = pedra - 50;
                                                    madeira = madeira - 50;
                                                    dinheiro = dinheiro - 40;
                                                    matriz[newX][newY] = " |Quartel| ";
                                                    Type.slowPrint("Construindo quartel...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 350);
                                                    System.out.println(" Quartel construído com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "3":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 200 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 120 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 80 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 0 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 120 && madeira >= 200 && comida >= 80) {
                                                    pedra = pedra - 120;
                                                    madeira = madeira - 200;
                                                    comida = comida - 80;
                                                    matriz[newX][newY] = " | Banco | ";
                                                    Type.slowPrint("Construindo Centro principal do Reino (banco)...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 600);
                                                    System.out.println(" Centro principal do Reino construído com sucesso!");
                                                    System.out.println("Parabéns chefe, nossa construção principal está de pé, somos oficialmente um Reino Nobre!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "4":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 0 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 100 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 0 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 0 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 100) {
                                                    pedra = pedra - 100;
                                                    matriz[newX][newY] = " | Lenha | ";
                                                    Type.slowPrint("Construindo Ponto do lenhador...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 350);
                                                    System.out.println(" Ponto do lenhador construído com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "5":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 100 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 0 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 0 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 0 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(madeira >= 100) {
                                                    madeira = madeira - 100;
                                                    matriz[newX][newY] = " | Miner | ";
                                                    Type.slowPrint("Construindo Mineradora...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 300);
                                                    System.out.println(" Mineradora construída com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "6":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 80 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 60 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 20 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 0 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 60 && madeira >= 80 && comida >= 20) {
                                                    pedra = pedra - 60;
                                                    madeira = madeira - 80;
                                                    comida = comida - 20;
                                                    matriz[newX][newY] = " |Fazenda| ";
                                                    Type.slowPrint("Construindo Fazenda...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 120);
                                                    System.out.println(" Fazenda construída com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "7":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 50 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 50 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 150 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 0 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 50 && madeira >= 50 && comida >= 150 && dinheiro >= 50) {
                                                    pedra = pedra - 50;
                                                    madeira = madeira - 50;
                                                    comida = comida - 150;
                                                    populacao = populacao + 5;
                                                    dinheiro = dinheiro - 50;
                                                    matriz[newX][newY] = " | CasaP | ";
                                                    Type.slowPrint("Construindo Casa pequena...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 100);
                                                    System.out.println(" Casinha construída com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "8":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 150 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 150 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 350 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 150 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 150 && madeira >= 150 && comida >= 350 && dinheiro >= 150) {
                                                    pedra = pedra - 150;
                                                    madeira = madeira - 150;
                                                    comida = comida - 350;
                                                    populacao = populacao + 10;
                                                    dinheiro = dinheiro - 150;
                                                    matriz[newX][newY] = " | CasaM | ";
                                                    Type.slowPrint("Construindo Casa média...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 200);
                                                    System.out.println(" Casa construída com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "9":
                                        if (" | Líder | ".equals(matriz[posicaoX][posicaoY])) {
                                            System.out.println("Custo (madeira): 350 |" + "Voce tem: " + madeira);
                                            System.out.println("Custo (pedra): 350 |" + "Voce tem: " + pedra);
                                            System.out.println("Custo (comida): 550 |" + "Voce tem: " + comida);
                                            System.out.println("Custo (dinheiro): 350 |" + "Voce tem: " + dinheiro);
                                            System.out.println("Digite a direção na qual deseja construir (dW para cima, dA para esquerda, dS para baixo, dD para direita):");
                                            String direcaoConstrucao = scanner.nextLine().toLowerCase();
                                            int newX = posicaoX;
                                            int newY = posicaoY;
                                            switch (direcaoConstrucao) {
                                                case "dw":
                                                    newX--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "da":
                                                    newY--;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "ds":
                                                    newX++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                case "dd":
                                                    newY++;
                                                    imprimirMatriz(matriz);
                                                    break;
                                                default:
                                                    System.out.println("Direção inválida. A construção não pode ser feita.");
                                                    imprimirMatriz(matriz);
                                                    break;
                                            }
                                            if (newX >= 0 && newX < matriz.length && newY >= 0 && newY < matriz[0].length && matriz[newX][newY] == null) {
                                                if(pedra >= 350 && madeira >= 350 && comida >= 550 && dinheiro >= 350) {
                                                    pedra = pedra - 350;
                                                    madeira = madeira - 350;
                                                    comida = comida - 550;
                                                    populacao = populacao + 20;
                                                    dinheiro = dinheiro - 350;
                                                    matriz[newX][newY] = " | CasaG | ";
                                                    Type.slowPrint("Construindo Mansão...!\n", 50);
                                                    Type.slowPrint("[||||||||||]", 500);
                                                    System.out.println(" Mansão construída com sucesso!");
                                                    imprimirMatriz(matriz);
                                                } else {
                                                    System.out.println("Chefe, não temos recursos suficientes para construir, tente outra coisa.");
                                                }
                                            } else {
                                                System.out.println("Não é possível construir aqui. Escolha outra direção.");
                                            }
                                        } else {
                                            System.out.println("Você não pode construir um muro aqui. Movimente-se para uma célula vazia primeiro.");
                                        }
                                        break;
                                    case "0":
                                        break;
                                    default:
                                        System.out.println("Comando inválido. Digite outro valor.");
                                }
                            break;
                        case "sair":
                            sairDoJogo();
                        break;
                        default:
                            System.out.println("Comando inválido. Digite outro valor.");
                    }
                }
            }
        }
    }
}
