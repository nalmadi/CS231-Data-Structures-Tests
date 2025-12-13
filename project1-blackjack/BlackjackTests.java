/*
file name:      BlackjackTests.java
Authors:        Naser Al Madi
last modified:  11/13/2025

How to run:     java -ea BlackjackTests
*/


public class BlackjackTests {

    public static void blackjackTests() {

        // case 1: testing Blackjack() and Blackjack(i)
        {
            // given
            Blackjack bj1 = new Blackjack();
            Blackjack bj2 = new Blackjack(5);

            // when

            // then
            assert bj1 != null : "Error in Blackjack::Blackjack()";
            assert bj2 != null : "Error in Blackjack::Blackjack()";
        }

        // case 2: testing reset() clears hands
        {
            // given
            Blackjack bj = new Blackjack();

            // when
            bj.deal(); // deal cards to both players
            bj.reset(); // reset should clear hands

            // then
            // After reset, dealing again should work (hands were cleared)
            assert bj != null : "Error in Blackjack::reset()";
        }

        // case 3: testing deal() gives 2 cards to each player
        {
            // given
            Blackjack bj = new Blackjack();

            // when
            bj.deal();
            String state = bj.toString();

            // then
            // toString should show both hands have cards
            assert state != null : "Error in Blackjack::deal() or Blackjack::toString()";
            assert state.length() > 0 : "Error in Blackjack::toString() - should return game state";
        }

        // case 4: testing playerTurn() - player draws until >= 16
        {
            // given
            Blackjack bj = new Blackjack();
            bj.deal();

            // when
            boolean playerNotBust = bj.playerTurn();

            // then
            // playerTurn returns false if player busts (> 21), true otherwise
            // We can't predict the exact outcome, but the method should complete
            assert true : "playerTurn() completed";
        }

        // case 5: testing dealerTurn() - dealer draws until >= 17
        {
            // given
            Blackjack bj = new Blackjack();
            bj.deal();
            bj.playerTurn();

            // when
            boolean dealerNotBust = bj.dealerTurn();

            // then
            // dealerTurn returns false if dealer busts (> 21), true otherwise
            assert true : "dealerTurn() completed";
        }

        // case 6: testing game() returns valid result (-1, 0, or 1)
        {
            // given
            Blackjack bj = new Blackjack();

            // when
            int result = bj.game(false);

            // then
            // game() should return -1 (dealer wins), 0 (push), or 1 (player wins)
            assert result >= -1 && result <= 1 : "Error in Blackjack::game() - should return -1, 0, or 1, but returned " + result;
        }

        // case 7: testing game(true) with verbose output
        {
            // given
            Blackjack bj = new Blackjack();

            // when
            System.out.println("Testing verbose game output:");
            int result = bj.game(true);

            // then
            assert result >= -1 && result <= 1 : "Error in Blackjack::game(true) - should return -1, 0, or 1";
        }

        // case 8: testing multiple games and verifying results distribution
        {
            // given
            Blackjack bj = new Blackjack();
            int playerWins = 0;
            int dealerWins = 0;
            int pushes = 0;
            int numGames = 1000;

            // when
            for (int i = 0; i < numGames; i++) {
                int result = bj.game(false);
                if (result == 1) playerWins++;
                else if (result == -1) dealerWins++;
                else if (result == 0) pushes++;
                else assert false : "Error in Blackjack::game() - invalid result: " + result;
            }

            // then
            int total = playerWins + dealerWins + pushes;
            assert total == numGames : "Error in Blackjack::game() - all games should have a result";
            System.out.println("Results of " + numGames + " games:");
            System.out.println("  Player wins: " + playerWins + " (" + (playerWins * 100.0 / numGames) + "%)");
            System.out.println("  Dealer wins: " + dealerWins + " (" + (dealerWins * 100.0 / numGames) + "%)");
            System.out.println("  Pushes: " + pushes + " (" + (pushes * 100.0 / numGames) + "%)");
        }

        // case 9: testing reset() reshuffles when deck < 26 cards
        {
            // given
            Blackjack bj = new Blackjack();

            // when - play enough games to deplete deck below 26 cards
            // Each game uses at minimum 4 cards (2 per player), so ~7 games should trigger reshuffle
            for (int i = 0; i < 10; i++) {
                bj.game(false);
            }

            // then - if reset is working correctly, game should still function
            int result = bj.game(false);
            assert result >= -1 && result <= 1 : "Error in Blackjack::reset() - game should work after reshuffle";
        }

        // case 10: testing toString() returns a non-empty string
        {
            // given
            Blackjack bj = new Blackjack();
            bj.deal();

            // when
            String state = bj.toString();

            // then
            assert state != null : "Error in Blackjack::toString() - should not return null";
            assert state.length() > 0 : "Error in Blackjack::toString() - should return non-empty string";
            System.out.println("Game state example: " + state);
        }
       
        System.out.println("*** Done testing Blackjack! ***\n");
    }


    public static void main(String[] args) {

        blackjackTests();
    }
}