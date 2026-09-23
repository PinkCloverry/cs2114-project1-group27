package blackjack;

import student.TestCase;

public class CardTest extends TestCase {

    private Card ten;
    private Card ace;
    private Card seven;

    public void setUp() {
        ten = new Card(10, false);
        ace = new Card(11, true);
        seven = new Card(7, false);
    }

    /**
     * Tests getValue().
     */
    public void testGetValue() {
        assertEquals(10, ten.getValue());
        assertEquals(11, ace.getValue());
        assertEquals(7, seven.getValue());
    }

    /**
     * Tests isAce() when the card is an ace.
     */
    public void testIsAceTrue() {
        assertTrue(ace.isAce());
    }

    /**
     * Tests isAce() when the card is not an ace.
     */
    public void testIsAceFalse() {
        assertFalse(ten.isAce());
        assertFalse(seven.isAce());
    }
}