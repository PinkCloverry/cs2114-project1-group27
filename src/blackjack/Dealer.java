package blackjack;

import java.util.Scanner;

/**
 * Dealer class responsible for handling cards and player/dealer hands.
 * 
 * @author Rin Hall
 * @version Sep 16, 2026
 */
public class Dealer {
    // ~ Fields ................................................................
    public Card[] deck;
    private Card[] playerHand;
    private int playerHandSize;
    private Card[] dealerHand;
    public int dealerHandSize;

    private int maxHandSize = 21;
    private int topCard;

    // ----------------------------------------------------------
    /**
     * Create a new Dealer object. Automatically initializes a standard deck of
     * 52 cards
     */
    // ~ Constructors ..........................................................
    public Dealer() {
        // initialize an array of 52 standards playing cards
        deck = new Card[52];
        playerHand = new Card[maxHandSize];
        playerHandSize = 0;
        dealerHand = new Card[maxHandSize];
        dealerHandSize = 0;
        for (int value = 2; value < 11; value++) {
            for (int i = 0; i < 4; i++) {
                deck[i + ((value - 2) * 4)] = new Card(value, false);
            }
        }
        for (int i = 0; i < 12; i++) {
            deck[36 + i] = new Card(10, false);
        }
        for (int i = 0; i < 4; i++) {
            deck[48 + i] = new Card(11, true);
        }
        shuffleDeck();
    }


    // ~Public Methods ........................................................
    /**
     * PlayerTurn() shows the player their cards and then prompts them for an
     * input. It compares that input to the list of valid hit and stay terms,
     * and determines which one it is. If it is invalid, it will prompt the
     * player again.
     * 
     * @function playerTurn
     * @return boolean Whether or not the player hit (true) or stayed (false)
     */
    public boolean playerTurn() {
        String[] validHit = { "Hit", "Yes", "Affirmative", "hit" };

        String[] validStay = { "Stay", "No", "Negative", "stay" };
        boolean invalidInput = true;
        boolean hit = true;

        while (invalidInput) {
            Scanner playerInput = new Scanner(System.in);
            System.out.println("The dealer's revealed card is: " + dealerHand[1]
                .getValue());
            System.out.print("Your cards are: ");
            for (int i = 0; i < playerHandSize; i++) {
                System.out.print(playerHand[i].getValue());
                if (i < playerHandSize - 1) {
                    System.out.print(", ");
                }
                else {
                    System.out.println("");
                }
            }
            System.out.println("Would you like to Hit or Stay?");

            String input = playerInput.nextLine();
            for (int i = 0; i < validHit.length; i++) {
                if (input.equals(validHit[i])) {
                    invalidInput = false;
                }
            }

            for (int i = 0; i < validStay.length; i++) {
                if (input.equals(validStay[i])) {
                    invalidInput = false;
                    hit = false;
                }
            }

            if (invalidInput) {
                System.out.println(
                    "Invalid response! To Hit, type \"Hit\", and to Stay, type "
                        + "\"Stay\"!");
            }
        }

        if (hit) {
            System.out.println("Your card is: " + dealPlayer());
            if (calculateHandValue(playerHand) > 21) {
                System.out.println("Player busts with a value of "
                    + calculateHandValue(playerHand) + "!");
                return false;
            }
            System.out.print("Your cards are: ");
            for (int i = 0; i < playerHandSize; i++) {
                System.out.print(playerHand[i].getValue());
                if (i < playerHandSize - 1) {
                    System.out.print(", ");
                }
                else {
                    System.out.println("");
                }
            }
        }
        else {
            System.out.println("You chose to stay. Your hand's value is: "
                + calculateHandValue(playerHand));
        }
        return hit;
    }


    /**
     * @function dealerTurn() decides what the dealer does on their turn
     * @return boolean Whether or not the player hit (true) or stayed (false)
     */
    public boolean dealerTurn() {
        boolean hit = true;
        if (calculateHandValue(playerHand) > 21) {
            return false;
        }

        System.out.print("The dealer's cards are ");
        for(int i = 0; i < dealerHandSize; i++) {
            System.out.print(dealerHand[i].getValue());
            if(i < dealerHandSize-1) {
                System.out.print(", ");
            }
            else {
                System.out.println(". For a value of: " + calculateHandValue(dealerHand));
            }
        }
        
        int handValue = calculateHandValue(dealerHand);
        if (handValue < 17) {
            System.out.println("The dealer has chosen to hit and draws a " + dealDealer() + "!");
            handValue = calculateHandValue(dealerHand);
            if (handValue > 21) {
                System.out.println("The dealer busts with a value of " + handValue + "!");
                return false;
            }
        }
        else {
            System.out.println("The dealer has chosen to stay.");
            hit = false;
        }

        return hit;
    }


    /**
     * @function calculateHandValue returns the value of the given hand
     * @param hand
     *            The hand to evaluate
     * @return The value of the hand
     */
    public int calculateHandValue(Card[] hand) {
        int sum = 0;
        int aceCount = 0;
        int handSize;
        if (hand.equals(dealerHand)) {
            handSize = dealerHandSize;
        }
        else {
            handSize = playerHandSize;
        }

        for (int i = 0; i < handSize; i++) {
            sum += hand[i].getValue();
            if (hand[i].isAce()) {
                aceCount++;
            }

        }

        while (aceCount > 0 && sum > 21) {
            sum -= 10;
            aceCount--;
        }

        return sum;
    }


    // ----------------------------------------------------------
    /**
     * shuffleDeck will randomize the order of cards in the dealer's deck
     * 
     * It creates a new, empty deck, the same length as the original deck
     * Then it randomly selects a card from the deck and adds it to the
     * temporary deck
     * It takes that card and switches it with the card at the end of the deck
     * to prevent it from being selected again
     * Then it selects a new card from every card but the last one, etc. etc.
     * Should take O(5n+4) time
     */
    public void shuffleDeck() {
        Card[] tempDeck = new Card[deck.length];
        Card tempHold;
        int randomIndex = 0;
        int lastCardIndex = 0;

        for (int i = deck.length; i > 0; i--) {
            randomIndex = (int)(Math.random() * i);
            tempDeck[lastCardIndex] = deck[randomIndex];
            lastCardIndex++;

            tempHold = deck[randomIndex];
            deck[randomIndex] = deck[i - 1];
            deck[i - 1] = tempHold;
        }
        deck = tempDeck;
        topCard = deck.length - 1;
        System.out.println("Deck shuffled");
    }


    // ----------------------------------------------------------
    /**
     * Sets deck to be the imported deck
     * 
     * @param importedDeck
     *            The deck the player defined themself
     */
    public void setDeck(Card[] importedDeck) {

        deck = importedDeck;
        topCard = deck.length - 1;
    }


    // ----------------------------------------------------------
    /**
     * Deals a card to the player
     * 
     * @return value The numerical value of the card
     */
    public int dealPlayer() {
        int value = deck[topCard].getValue();

        playerHand[playerHandSize] = deck[topCard];
        playerHandSize++;
        topCard--;

        return value;
    }


    // ----------------------------------------------------------
    /**
     * Deals a card to the dealer
     * 
     * @return value The numerical value of the card
     */
    public int dealDealer() {
        int value = deck[topCard].getValue();

        dealerHand[dealerHandSize] = deck[topCard];
        dealerHandSize++;
        topCard--;

        return value;

    }


    // ----------------------------------------------------------
    /**
     * Deals the starting hand to player and dealer
     */
    public void initialDeal() {
        dealPlayer();
        dealDealer();
        dealPlayer();
        dealDealer();
    }

    /**
     * Calculates who wins the game. If the player busts, the player immediately loses
     * 
     * @return boolean true if the player wins, false if the dealer wins
     */
    public boolean determineWinner() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);
        if (playerValue > 21) {
            return false;
        }
        if (dealerValue > 21) {
            return true;
        }
        return playerValue >= dealerValue;
    }
}
