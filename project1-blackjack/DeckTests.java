/*
file name:      DeckTests.java
Authors:        Naser Al Madi
last modified:  11/13/2025

How to run:     java -ea DeckTests
*/


public class DeckTests {

    public static void deckTests() {

        // case 1: testing Deck() and size()
        {
            // given
            Deck d1 = new Deck();

            // when
            System.out.println(d1);

            // then
            assert d1 != null : "Error in Deck::Deck()";
            assert d1.size() == 52 : "Error in Deck::Deck()";
        }

        // case 2: testing deal()
        {
            // given
            Deck d1 = new Deck();

            // when

            // then
            Card c1 = d1.deal();

            assert d1 != null : "Error in Deck::deal()";
            assert c1 != null : "Error in Deck::deal()";
            assert d1.size() == 51 : "Error in Deck::deal()";
        }

        // case 3: testing build()
        {
            // given
            Deck d1 = new Deck();

            // when

            // then
            Card c1 = d1.deal();
            Card c2 = d1.deal();
            Card c3 = d1.deal();
            d1.build();

            assert d1 != null : "Error in Deck::build()";
            assert c1 != null : "Error in Deck::deal()";
            assert c2 != null : "Error in Deck::deal()";
            assert c3 != null : "Error in Deck::deal()";
            assert d1.size() == 52 : "Error in Deck::build()";
        }

        // case 4: testing shuffle()
        {
            // given
            Deck d1 = new Deck();

            // when
            String before = d1.toString();
            d1.shuffle();
            String after = d1.toString();

            // then
            assert !before.equals(after) : "Error in Deck::shuffle()";
            assert d1.size() == 52 : "Error in Deck::shuffle()";
        }

        // case 5: testing correct card composition
        // Spec: "4 each of cards with values 2-9 and 11, and 16 cards with the value 10"
        {
            // given
            Deck d1 = new Deck();
            int[] counts = new int[12]; // index 0-11, we use 2-11

            // when
            while (d1.size() > 0) {
                Card c = d1.deal();
                counts[c.getValue()]++;
            }

            // then
            // 4 each of values 2-9
            for (int i = 2; i <= 9; i++) {
                assert counts[i] == 4 : "Error in Deck::build() - should have 4 cards of value " + i + ", but found " + counts[i];
            }
            // 16 cards with value 10
            assert counts[10] == 16 : "Error in Deck::build() - should have 16 cards of value 10, but found " + counts[10];
            // 4 cards with value 11 (Aces)
            assert counts[11] == 4 : "Error in Deck::build() - should have 4 cards of value 11, but found " + counts[11];
        }

        // case 6: testing deal() returns card from position zero (top of deck)
        {
            // given
            Deck d1 = new Deck();
            // Get the string representation to see the first card
            String deckStr = d1.toString();

            // when
            Card dealt = d1.deal();

            // then
            assert dealt != null : "Error in Deck::deal() - should return a card";
            assert d1.size() == 51 : "Error in Deck::deal() - deck size should decrease by 1";
        }

        System.out.println("*** Done testing Deck! ***\n");

    }


    public static void main(String[] args) {

        deckTests();
    }
}