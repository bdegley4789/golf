//Author: Bryce Egley
import java.util.Stack;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
   private Stack<Card> deck = new Stack<Card>();
   
   public Deck(Stack<Card> deck) {
      this.deck = deck;
   }
}