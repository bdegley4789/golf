//Author: Bryce Egley
public class Card {
   //1-13, 0=joker,1=Ace,11=Jack,12=Queen,13=King
   private int num;
   //h=hearts, d=diamonds, s=spades, c=clubs, j=joker
   private char suit;
   //visible true when card is flipped up, false when down
   private boolean visible;
   
   public Card(int num, char suit) {
      this.num = num;
      this.suit = suit;
      this.visible = false; 
   }
   
   public int getNum() {
      return num;
   }
   public char getSuit() {
      return suit;
   }
   public boolean getVisible() {
      return visible;
   }
   public void flip() {
      this.visible = true;
   }
   public String print() {
      return ("Rank: " + this.getNum() + " Suit: " + this.getSuit());
   }
}