//Author: Bryce Egley
import java.util.Stack;
import java.util.ArrayList;

public class Player {
   //1-13, 0=joker,1=Ace,11=Jack,12=Queen,13=King
   private ArrayList<Card> hand;

   //initialize players hand
   public Player(ArrayList<Card> newHand) {
      this.hand = newHand;
   }
   
   //Main acces to hand
   public ArrayList<Card> getHand() {
      return hand;
   }
   
   //Give Card to player
   public void takeCard(Card card) {
      this.hand.add(card);
   }
   
   //Flip Card
   public void flip(int i) {
      this.hand.get(i).flip();
   }
   
   //Remove Card at index, return to be placed on discard deck
   public Card removeCard(int i) {
      Card temp = this.hand.get(i);
      this.hand.remove(i);
      return temp;
   }
   
   //Switch card at index, with new card
   public Card switchCard(int i, Card card) {
      Card temp = this.hand.get(i);
      this.hand.remove(i);
      this.hand.add(i,card);
      return temp;
   }
   
   //How many cards player has
   public int size() {
      return this.getHand().size();
   }
   
   //Print current cards
   public void print() {
      for (int i = 0; i < this.size(); i++) {
         System.out.println((i+1) + ":" + this.getHand().get(i).print());
      }
   }
   
   //Print current cards
   public void gamePrint() {
      for (int i = 0; i < this.size(); i++) {
         if (this.getHand().get(i).getVisible() == true) {
            System.out.print((i+1) + ":" + this.getHand().get(i).print() + " ");
         } else {
            System.out.print((i+1) + ":" + "Rank: " + "?" + " Suit: " + "?" + " ");
         }
         if (i % 2 == 1) {
            System.out.println();
         }
      }
   }
}