package blackjack;

import student.TestCase;

public class DealerTest extends TestCase {

    private Dealer clover;

    public void setUp() {
        clover = new Dealer();
    }

    /**
     * Tests that shuffleDeck() changes the order of the deck.
     */
    public void testShuffleDeck() {
        clover.shuffleDeck();

        boolean shuffled = false;

        for (int i = 0; i < 4; i++) {
            if (clover.deck[i].getValue() != 2) {
                shuffled = true;
            }
        }

        assertTrue(shuffled);
    }

    /**
     * Tests that initialDeal() gives the dealer two cards.
     */
    public void testInitialDeal() {
        clover.initialDeal();
        assertEquals(2, clover.dealerHandSize);
    }

    /**
     * Tests that dealDealer() adds one card to the dealer's hand.
     */
    public void testDealDealer() {
        clover.dealDealer();
        assertEquals(1, clover.dealerHandSize);
    }

    /**
     * Tests that setDeck() replaces the current deck.
     */
    public void testSetDeck() {
        Card[] customDeck = new Card[4];

        customDeck[0] = new Card(2, false);
        customDeck[1] = new Card(3, false);
        customDeck[2] = new Card(4, false);
        customDeck[3] = new Card(11, true);

        clover.setDeck(customDeck);

        assertEquals(customDeck, clover.deck);
    }
}