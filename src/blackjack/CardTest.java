package blackjack;

public class CardTest {

    public static void main(String[] args) {

        // Test getValue() with a normal card
        Card ten = new Card(10, false);

        if (ten.getValue() == 10) {
            System.out.println("PASS: getValue()");
        } else {
            System.out.println("FAIL: getValue()");
        }

        // Test isAce() with an ace
        Card ace = new Card(11, true);

        if (ace.isAce()) {
            System.out.println("PASS: isAce() true");
        } else {
            System.out.println("FAIL: isAce() true");
        }

        // Test isAce() with a non-ace
        Card seven = new Card(7, false);

        if (!seven.isAce()) {
            System.out.println("PASS: isAce() false");
        } else {
            System.out.println("FAIL: isAce() false");
        }

        System.out.println("Testing complete.");
    }
}
