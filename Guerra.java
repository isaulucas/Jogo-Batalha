import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Guerra {

    private static final int NUM_JOGADORES = 2;
    private static final int TAMANHO_BARALHO = 6;
    private static final int CARTAS_POR_JOGADOR = TAMANHO_BARALHO / NUM_JOGADORES;

    private static List<Carta> baralho;
    private static List<Carta>[] maosJogadores;
    private static int[] placar;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Jogo da Guerra de Cartas!");

        baralho = criarBaralho();
        Collections.shuffle(baralho);

        maosJogadores = new List[NUM_JOGADORES];
        for (int i = 0; i < NUM_JOGADORES; i++) {
            maosJogadores[i] = new ArrayList<>();
        }

        for (int i = 0; i < TAMANHO_BARALHO; i++) {
            maosJogadores[i % NUM_JOGADORES].add(baralho.get(i));
        }

        placar = new int[NUM_JOGADORES];

        int turno = 1;
        while (!jogoTerminou()) {
            System.out.println("Turno " + turno);
            mostrarPlacar();

            for (int i = 0; i < NUM_JOGADORES; i++) {
                System.out.println("\nJogador " + (i + 1) + ", é a sua vez! Suas cartas:");
                mostrarCartas(maosJogadores[i]);
                System.out.print("Escolha o número da carta a jogar: ");
                int escolha = scanner.nextInt();
                maosJogadores[i].get(escolha - 1);
                scanner.nextLine();
            }

            Carta cartaJogador1 = maosJogadores[0].remove(0);
            Carta cartaJogador2 = maosJogadores[1].remove(0);

            System.out.println("Jogador 1: " + cartaJogador1);
            System.out.println("Jogador 2: " + cartaJogador2);

            int resultado = cartaJogador1.compareTo(cartaJogador2);

            if (resultado > 0) {
                placar[0]++;
                System.out.println("Jogador 1 venceu a rodada!");
            } else if (resultado < 0) {
                placar[1]++;
                System.out.println("Jogador 2 venceu a rodada!");
            } else {
                System.out.println("Empate! Vai ter uma Guerra!");
                // Implemente a lógica da Guerra aqui
            }

            System.out.println("Pressione Enter para continuar...");
            scanner.nextLine();

            turno++;
        }

        int vencedor = determinarVencedor();
        System.out.println("Jogador " + (vencedor + 1) + " venceu o jogo!");
    }

    private static boolean jogoTerminou() {
        for (int pontos : placar) {
            if (pontos >= CARTAS_POR_JOGADOR) {
                return true;
            }
        }
        return false;
    }

    private static void mostrarPlacar() {
        System.out.println("Placar: Jogador 1 (" + placar[0] + ") - Jogador 2 (" + placar[1] + ")");
    }

    private static int determinarVencedor() {
        if (placar[0] > placar[1]) {
            return 0;
        } else {
            return 1;
        }
    }

    private static List<Carta> criarBaralho() {
        List<Carta> baralho = new ArrayList<>();
        for (Naipe naipe : Naipe.values()) {
            for (int valor = 2; valor <= 14; valor++) {
                baralho.add(new Carta(valor, naipe));
            }
        }
        return baralho;
    }
    
    private static void mostrarCartas(List<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            System.out.println((i + 1) + ". " + cartas.get(i));
        }
    }
}

class Carta implements Comparable<Carta> {

    private int valor;
    private Naipe naipe;

    public Carta(int valor, Naipe naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    @Override
    public int compareTo(Carta outraCarta) {
        return Integer.compare(this.valor, outraCarta.valor);
    }

    @Override
    public String toString() {
        String nomeValor = String.valueOf(valor);
        if (valor == 11) nomeValor = "J";
        else if (valor == 12) nomeValor = "Q";
        else if (valor == 13) nomeValor = "K";
        else if (valor == 14) nomeValor = "A";

        return nomeValor + " de " + naipe;
    }
}

enum Naipe {
    PAUS, COPAS, ESPADAS, OUROS
}
