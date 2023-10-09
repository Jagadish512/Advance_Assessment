

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.example.Deck;
import org.junit.Test;

public class DeckTest {

    @Test
    public void testDeckConstructor() {
        Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck();
        deck.mixTheCardsInTheDeck();
        assertEquals(52, deck.getCards().size());
        assertNotEquals(new Deck(), deck);
    }

    @Test
    public void testEquals1() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertEquals(deck1, deck2);
    }

    @Test
    public void testEquals2() {
        Deck deck1 = new Deck();
        assertEquals(deck1, deck1);
    }

    @Test
    public void testEquals3() {
        Deck deck1 = new Deck();
        assertNotEquals(deck1, null);
    }

    @Test
    public void testEquals4() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck1.mixTheCardsInTheDeck();
        assertNotEquals(deck1, deck2);
    }

    @Test
    public void testEquals5() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck1.dealHand(1);
        assertNotEquals(deck1, deck2);
    }

    @Test
    public void testEquals6() {
        Deck deck1 = new Deck();
        assertNotEquals(deck1, "string");
    }
}
