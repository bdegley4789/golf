//Author: Bryce Egley
//Date Started: March 26, 2017
//This program will play the card game of Golf
//Info on Golf Card Game https://en.wikipedia.org/wiki/Golf_(card_game)
import java.util.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;

public class GolfMain {

   public static void main(String[] args) {
      
      Scanner scanner = new Scanner(System.in);
      //Create list of cards
      ArrayList<Card> num = new ArrayList<Card>();
      for (int i = 0; i < 108; i++) {
         Card card = makeCard(i);
         num.add(card);
      }

      //To check if all cards are present. Uncomment to print
      //printCards(num);
      
      //Convert to Deck
      Stack<Card> deck = convertToDeck(num);
      //Shuffle Deck
      deck = shuffle(deck);
      
      //To check if Deck has been Shuffled. Uncomment to print
      //printDeck(deck);
      
      int playersSize = validInput(1,5,"How many players?(Enter Whole Number 1-5): ");
      //keep track of scores all games
      int[] scores = new int[playersSize];
     //Start While Loop for game
     char gameEnd = 'y';
     while (gameEnd == 'y' || gameEnd == 'Y') {
         //keep track of current game score
         int[] gameScore = new int[playersSize];
         //PLayer Flip up count
         int[] playerFlip = new int[playersSize];
         for (int i = 0; i < playersSize; i++) {
            playerFlip[i] = 2;
         }
         //Create Players
         ArrayList<Player> table = new ArrayList<Player>();
         for (int i = 0; i < playersSize; i++) {
            ArrayList<Card> hand = new ArrayList<Card>();
            Player player = new Player(hand);
            table.add(player);
         }
         //Deal
         for (int i = 0; i < 6; i++) {
            for (int j = 0; j < playersSize; j++) {
               table.get(j).takeCard(deck.pop());
            }
         }
         //How game will print
         for (int j = 0; j < playersSize; j++) {
            System.out.println("Player " + (j+1));
            table.get(j).gamePrint();
         }
         //Flip 2 Cards
         playerFlip(table.get(0));
         computerFlip(table);
         
         //How game will print
         for (int j = 0; j < playersSize; j++) {
            System.out.println("Player " + (j+1));
            table.get(j).gamePrint();
         }
         //Show Top Discard Card
         Stack<Card> discard = new Stack<Card>(); 
         discard.push(deck.pop());
         System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
              
         int end = 0;
         while (end == 0) {
            //if statement for if someone reaches 6 to finish all players
            char choice = choose();
            //Flip up facedown card
            if (choice == 'f') {
               int flip2 = validInput(1,6,"Enter Card to Flip: ");
               while (table.get(0).getHand().get(flip2-1).getVisible() != false) {
                  System.out.println("ERROR: Card already flipped.");
                  flip2 = validInput(1,6,"Enter Card to Flip: ");
               }
               table.get(0).flip(flip2-1);
               playerFlip[0]++;
               System.out.println("Player Flip " + playerFlip[0]);
               //Put top card onto discard pile
               discard.push(deck.pop());
               System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());

            //Draw card on top of deck
            } else if (choice == 't') {
               //Do you want to take this card (y/n)?
               System.out.println("You drew a " + "Rank: " + deck.peek().getNum() + " Suit: " + deck.peek().getSuit()); 
               char take = yn("Do you want to take this card (y/n)? : ");
               //Yes
               if (take == 'y') {
                  //Where do you want to place it?
                  int flip2 = validInput(1,6,"Where do you want to place it: ");
                  if (table.get(0).getHand().get(flip2-1).getVisible() == false) {
                     //Take card at index out of hand place, put top of deck in hand
                     Card temp = table.get(0).switchCard(flip2-1,deck.pop());
                     //Flip Card
                     table.get(0).flip(flip2-1);
                     //Put card on top of discard
                     discard.push(temp);
                     playerFlip[0]++;
                     System.out.println("Player Flip " + playerFlip[0]);
                     System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
                  //This else is for if player chooses a position already flipped up so it doesn't increment playerFlip
                  } else {
                     //Take card at index out of hand place, put top of deck in hand
                     Card temp = table.get(0).switchCard(flip2-1,deck.pop());
                     //Flip Card
                     table.get(0).flip(flip2-1);
                     //Put card on top of discard
                     discard.push(temp);
                     System.out.println("Player Flip " + playerFlip[0]);
                     System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
                  }
                  
               //No
               } else {
               char tFlip = yn("Do you want to flip a card?(y/n): ");
               //Discard and flip?
               if (tFlip == 'y') {
                  int flip2 = validInput(1,6,"Enter Card to Flip: ");
                  while (table.get(0).getHand().get(flip2-1).getVisible() != false) {
                     System.out.println("ERROR: Card already flipped.");
                     flip2 = validInput(1,6,"Enter Card to Flip: ");
                  }
                  table.get(0).flip(flip2-1);
                  playerFlip[0]++;
                  System.out.println("Player Flip " + playerFlip[0]);
                  //Put top card onto discard pile
                  discard.push(deck.pop());
                  System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
               }
               discard.push(deck.pop());
               }
            //Take card on discard pile
            } else {
               //Where do you want to place it?
                  int flip2 = validInput(1,6,"Where do you want to place it: ");
                  if (table.get(0).getHand().get(flip2-1).getVisible() == false) {
                     //Take card at index out of hand place, put top of deck in hand
                     Card temp = table.get(0).switchCard(flip2-1,discard.pop());
                     //Flip Card
                     table.get(0).flip(flip2-1);
                     //Put card on top of discard
                     discard.push(temp);
                     playerFlip[0]++;
                     System.out.println("Player Flip " + playerFlip[0]);
                     System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
                  } else {
                     //Take card at index out of hand place, put top of deck in hand
                     Card temp = table.get(0).switchCard(flip2-1,discard.pop());
                     //Flip Card
                     table.get(0).flip(flip2-1);
                     //Put card on top of discard
                     discard.push(temp);
                     System.out.println("Player Flip " + playerFlip[0]);
                     System.out.println("Discard Pile: " + "Rank: " + discard.peek().getNum() + " Suit: " + discard.peek().getSuit());
                  }
            }
            //Have AI's do their turns
            computerFlipOne(table);
            
            //Check if anyone went out, 6 cards flipped up
            for (int z = 0; z < playersSize; z++) {
               if (playerFlip[z] == 6) {
                  end = 1;   
               }
            } 
            //Print cards 
            for (int j = 0; j < playersSize; j++) {
               System.out.println("Player " + (j+1));
               table.get(j).gamePrint();
            }  
         }
         //Calculate scores
         for(int jm = 0; jm < playersSize; jm++) {
            for (int zs = 0; zs < 6; zs+=2) {
               if (table.get(jm).specCard(zs) != table.get(jm).specCard(zs+1)){
                  gameScore[jm] += table.get(jm).specCard(zs) + table.get(jm).specCard(zs+1);
               }   
            }
         }
         
         System.out.println("Scores for this round:");
         //Print this rounds scores
         for (int km = 0; km < playersSize; km++) {
            System.out.println("Player " + km + ": " + gameScore[km]);
         }
         System.out.println("Current Score:");
         //Add current Game Score to total running score
         for (int lm = 0; lm < playersSize; lm++) {
            scores[lm] += gameScore[lm];
            System.out.println("Player " + lm + ": " + scores[lm]);
         }
         
         //Ask user to play again or end game
         System.out.print("Would you like to play again? Enter y or n: ");
         Scanner scanChoose = new Scanner(System.in);
         gameEnd = scanChoose.next().charAt(0);
      }
   }
   
   //Ask player to flip, take discard, or take top of deck card
   public static char choose() {
      System.out.println("Enter f to flip one of your cards, t to take card on top of deck, d to take discard pile card");
      Scanner scanChoose = new Scanner(System.in);
      char choice = scanChoose.next().charAt(0);
      while (choice != 'f' && choice != 't' && choice != 'd') {
         System.out.println("ERROR! Enter f,t or d!");
         choice = scanChoose.next().charAt(0);
      }
      return choice;
   }
   
   //Ask player for y or n input
   public static char yn(String message) {
      System.out.println(message);
      Scanner scanChoose = new Scanner(System.in);
      char choice = scanChoose.next().charAt(0);
      while (choice != 'y' && choice != 'n') {
         System.out.println("ERROR! Enter y or n!");
         choice = scanChoose.next().charAt(0);
      }
      return choice;
   }
   
   //Player Flips up 2 cards
   public static void playerFlip(Player player) {
      System.out.println("Which of your cards would you like to flip?");
      int flip1 = validInput(1,6,"Enter 1st Card to Flip: ");
      player.flip(flip1-1);
      int flip2 = validInput(1,6,"Enter 2nd Card to Flip: ");
      while (player.getHand().get(flip2-1).getVisible() != false) {
         System.out.println("ERROR: Card already flipped.");
         flip2 = validInput(1,6,"Enter 2nd Card to Flip: ");
      }
      player.flip(flip2-1);
   }
   
   //Computer flips 2 random cards up
   public static void computerFlip(ArrayList<Player> table) {
      for (int j = 0; j < 2; j++) {
         for (int i = 1; i < table.size(); i++) {
            int compFlip1 = randInt(0,6-1);
            while (table.get(i).getHand().get(compFlip1).getVisible() != false) {
               compFlip1 = randInt(0,6-1);
            }
            table.get(i).flip(compFlip1);
         }
      }
   }
   
   //Computer flips 2 random cards up
   public static void computerFlipOne(ArrayList<Player> table) {
      for (int j = 0; j < 1; j++) {
         for (int i = 1; i < table.size(); i++) {
            int compFlip1 = randInt(0,6-1);
            while (table.get(i).getHand().get(compFlip1).getVisible() != false) {
               compFlip1 = randInt(0,6-1);
            }
            table.get(i).flip(compFlip1);
         }
      }
   }
   
   
   //Ensure user types in valid input
   public static int validInput(int min, int max, String message) {
      Scanner scanner = new Scanner(System.in);
      int value = 0;
      while (value < min || value > max) {
         System.out.print(message);
         while (!scanner.hasNextInt()) {
            System.out.println("ERROR: Not a number! Enter a number.");
            System.out.print(message);
            scanner.next();
         }
         value = scanner.nextInt();
         if (value < min || value > max) {
            System.out.println("ERROR: Number out of range.");
         }
      }
      return value;
   }
   
   //Print Cards to check all cards are present.
   //Should be 108 cards
   public static void printCards(ArrayList<Card> num) {
      for (int i = 0; i < num.size(); i++) {
         System.out.print((i+1) + ".");
         System.out.print("Rank: " + num.get(i).getNum());
         System.out.println(" Suit: " + num.get(i).getSuit());
      }
   }
   
   //1-13, 0=joker,1=Ace,11=Jack,12=Queen,13=King
   //h=hearts, d=diamonds, s=spades, c=clubs, j=joker
   //108 cards, for 2 decks 52+2 Jokers. 54*2=108
   //Return card(suit,number)
   public static Card makeCard(int i) {
      char suit;
      int num;
      if (i > 103) {
         suit = 'j';
         num = 0;
      } else {
         int suitFind = i / 13;
         num = i % 13 + 1;
         if (suitFind > 3) {
            suitFind = suitFind - 4;
         }
         if (suitFind == 0) {
            suit = 'h';
         } else if (suitFind == 1) {
            suit = 'd';
         } else if (suitFind == 2) {
            suit = 's';
         } else {
            suit = 'c';
         }
      }
      Card card = new Card(num,suit);
      return card;
   }
   
   /*DECK METHODS*/
   //Convert ArrayList to stack
   public static Stack<Card> convertToDeck(ArrayList<Card> num) {
      Stack<Card> deck = new Stack<Card>();
      for (int i = 0; i < num.size(); i++) {
         deck.push(num.get(i));  
      }
      return deck;
   }
   //Shuffle the Deck of Cards
   public static Stack<Card> shuffle(Stack<Card> temp) {
      ArrayList<Card> num = new ArrayList<Card>();
      num = convertToArrList(temp);
      Stack<Card> deck = new Stack<Card>();
      int randomNum;
      while (num.size() > 0) {
         randomNum = randInt(0,num.size()-1);
         deck.push(num.get(randomNum));
         num.remove(randomNum);
      }
      return deck;
   }
   //Convert Deck to ArrayList
   public static ArrayList<Card> convertToArrList(Stack<Card> deck) {
      ArrayList<Card> num = new ArrayList<Card>();
      for (int i = 0; i < deck.size(); i++) {
         num.add(deck.pop());
         i--;
      }
      return num;
   }
   //Print Deck
   public static void printDeck(Stack<Card> deck) {
      int j = 0;
      for (int i = 0; i < deck.size(); i++) {
         System.out.print((j+1) + ".");
         System.out.print("Rank: " + deck.peek().getNum());
         System.out.println(" Suit: " + deck.pop().getSuit());
         j++;
         i--;
      }
   }
   /*END OF DECK METHODS*/
   
   //Random Range, min max are exclusive (1,3)=1,2,3
   public static int randInt(int min, int max) {
      Random ran = new Random();
      int x = ran.nextInt(max-min+1) + min;
      return x;
   }
}
   