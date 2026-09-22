package blackjack;
public class Card {

    private int value;
    private boolean ace;

    public Card(int value, boolean ace) {
        this.value = value;
        this.ace = ace;
    }

    public int getValue() {
        return value;
    }

    public boolean isAce() {
        return ace;
    }
}
