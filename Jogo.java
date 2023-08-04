import java.util.*;

public class Jogo {
    private List<Carta> jogador1;
    private List<Carta> jogador2;

    public Jogo() {
        jogador1 = new ArrayList<>();
        jogador2 = new ArrayList<>();
    }

    public void distribuirCartas(List<Carta> baralho) {
        Collections.shuffle(baralho);
        int halfSize = baralho.size() / 2;
        jogador1.addAll(baralho.subList(0, halfSize));
        jogador2.addAll(baralho.subList(halfSize, baralho.size()));
    }

    public void jogar() {
        while (!jogador1.isEmpty() && !jogador2.isEmpty()) {
            Carta cartaJogador1 = jogador1.remove(0);
            Carta cartaJogador2 = jogador2.remove(0);

            System.out.println("Jogador 1: " + cartaJogador1 + " vs Jogador 2: " + cartaJogador2);

            int resultado = compararCartas(cartaJogador1, cartaJogador2);

            if (resultado > 0) {
                jogador1.add(cartaJogador1);
                jogador1.add(cartaJogador2);
            } else if (resultado < 0) {
                jogador2.add(cartaJogador1);
                jogador2.add(cartaJogador2);
            } else {
                List<Carta> cartasExtraJogador1 = new ArrayList<>();
                List<Carta> cartasExtraJogador2 = new ArrayList<>();
                cartasExtraJogador1.add(cartaJogador1);
                cartasExtraJogador2.add(cartaJogador2);

                realizarDesempate(cartasExtraJogador1, cartasExtraJogador2);

                jogador1.addAll(cartasExtraJogador1);
                jogador2.addAll(cartasExtraJogador2);
            }
        }

        if (jogador1.size() > jogador2.size()) {
            System.out.println("Jogador 1 venceu!");
        } else if (jogador1.size() < jogador2.size()) {
            System.out.println("Jogador 2 venceu!");
        } else {
            int totalJogador1 = calcularTotalCartas(jogador1);
            int totalJogador2 = calcularTotalCartas(jogador2);

            if (totalJogador1 > totalJogador2) {
                System.out.println("Jogador 1 venceu!");
            } else if (totalJogador1 < totalJogador2) {
                System.out.println("Jogador 2 venceu!");
            } else {
                System.out.println("Empate!");
            }
        }
    }

    private int compararCartas(Carta carta1, Carta carta2) {
        String valores = "AKQJT98765432";
        int indice1 = valores.indexOf(carta1.getValor());
        int indice2 = valores.indexOf(carta2.getValor());
        return Integer.compare(indice1, indice2);
    }

    private void realizarDesempate(List<Carta> cartasExtraJogador1, List<Carta> cartasExtraJogador2) {
        for (int i = 0; i < 3; i++) {
            if (jogador1.isEmpty() || jogador2.isEmpty()) {
                break;
            }
            Carta cartaJogador1 = jogador1.remove(0);
            Carta cartaJogador2 = jogador2.remove(0);

            cartasExtraJogador1.add(cartaJogador1);
            cartasExtraJogador2.add(cartaJogador2);
        }
        Carta maiorCartaJogador1 = Collections.max(cartasExtraJogador1, Comparator.comparingInt(this::getCartaValor));
        Carta maiorCartaJogador2 = Collections.max(cartasExtraJogador2, Comparator.comparingInt(this::getCartaValor));

        int resultado = compararCartas(maiorCartaJogador1, maiorCartaJogador2);
        if (resultado > 0) {
            cartasExtraJogador1.addAll(cartasExtraJogador2);
        } else if (resultado < 0) {
            cartasExtraJogador2.addAll(cartasExtraJogador1);
        } else {
            realizarDesempate(cartasExtraJogador1, cartasExtraJogador2);
        }
    }

    private int getCartaValor(Carta carta) {
        String valores = "AKQJT98765432";
        return valores.indexOf(carta.getValor());
    }

    private int calcularTotalCartas(List<Carta> cartas) {
        return cartas.stream().mapToInt(carta -> getCartaValor(carta) + 1).sum();
    }
            public static void main(String[] args) {
            List<Carta> baralho = new ArrayList<>();
            String[] valores = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
    
            for (String valor : valores) {
                for (int i = 0; i < 4; i++) {
                    baralho.add(new Carta(valor));
                }
            }
    
            Jogo jogo = new Jogo();
            jogo.distribuirCartas(baralho);
            jogo.jogar();
        }
    }