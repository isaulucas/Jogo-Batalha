import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class War {

    private static final int DECK_SIZE = 52;

    private static ArrayList<Card> deck;
    private static ArrayList<Card> player1Cards;
    private static ArrayList<Card> player2Cards;

    public static void main(String[] args) {
        // Cria um baralho de cartas
        deck = new ArrayList<>();
        for (int i = 2; i <= 14; i++) {
            deck.add(new Card(i, Suit.SPADES));
            deck.add(new Card(i, Suit.HEARTS));
            deck.add(new Card(i, Suit.DIAMONDS));
            deck.add(new Card(i, Suit.CLUBS));
        }

        // Embaralha o baralho
        Collections.shuffle(deck);

        // Distribui as cartas para os jogadores
        player1Cards = new ArrayList<>();
        player2Cards = new ArrayList<>();
        for (int i = 0; i < DECK_SIZE / 2; i++) {
            player1Cards.add(deck.remove(0));
            player2Cards.add(deck.remove(0));
        }

        // Inicia o jogo
        Scanner scanner = new Scanner(System.in);
        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            // Cada jogador joga uma carta
            Card player1Card = player1Cards.remove(0);
            Card player2Card = player2Cards.remove(0);

            // Compara as cartas
            if (player1Card.getValue() > player2Card.getValue()) {
                // O jogador 1 vence a rodada
                player1Cards.add(player1Card);
                player1Cards.add(player2Card);
            } else if (player1Card.getValue() < player2Card.getValue()) {
                // O jogador 2 vence a rodada
                player2Cards.add(player1Card);
                player2Cards.add(player2Card);
            } else {
                // Guerra!
                player1Cards.add(player1Card);
                player1Cards.add(player1Card);
                player1Cards.add(player2Card);
                player2Cards.add(player2Card);
                player2Cards.add(player2Card);
            }

            // Exibe o status do jogo
            System.out.println("Jogador 1: " + player1Cards.size());
            System.out.println("Jogador 2: " + player2Cards.size());
        }

        // Determina o vencedor
        if (player1Cards.isEmpty()) {
            System.out.println("O jogador 2 venceu!");
        } else {
            System.out.println("O jogador 1 venceu!");
        }
    }
}

class Card {

    private int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }
}

enum Suit {

    SPADES, HEARTS, DIAMONDS, CLUBS
}
