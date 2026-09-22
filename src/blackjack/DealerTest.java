package blackjack;

public class DealerTest extends student.TestCase {
    private Dealer clover;

    public void setUp() {
        clover = new Dealer();
    }


    // ----------------------------------------------------------
    /**
     * Tests the shuffle deck method
     * This one is difficult because it relies on randomness, but an unshuffled
     * deck will have 2 at indexes 0-3. Therefore, if there is not a 2 at all
     * indexes 0-3, the deck has been shuffled. It will rarely return a false 
     * test due to randomness.
     */
    public void testShuffleDeck() {
        boolean shuffled = false;
        for (int i = 0; i < 4; i++) {
            if (clover.deck[i].getValue() != 2) {
                shuffled = true;
            }
        }
        assertTrue(shuffled);
    }
    
    public void testInitialDeal() {
        clover.initialDeal();
        assertTrue(clover.dealerHandSize == 2);
    }
}
