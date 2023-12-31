package org.example;

import java.util.HashMap;
import java.util.Map;


public class Main {
    private static Map<Results, Float> expectedResults = new HashMap<>();
    static {
        long possibleFiveCardHands = (52 * 51 * 50 * 49 * 48) / (5 * 4 * 3 * 2 * 1);
        expectedResults.put(Results.STRAIGHT_FLUSH,    40F/possibleFiveCardHands);
        expectedResults.put(Results.FOUR_OF_A_KIND,   624F/possibleFiveCardHands);
        expectedResults.put(Results.FULL_HOUSE,      3744F/possibleFiveCardHands);
        expectedResults.put(Results.FLUSH,           5108F/possibleFiveCardHands);
        expectedResults.put(Results.STRAIGHT,       10200F/possibleFiveCardHands);
        expectedResults.put(Results.THREE_OF_A_KIND,54912F/possibleFiveCardHands);
        expectedResults.put(Results.TWO_PAIR,      123552F/possibleFiveCardHands);
        expectedResults.put(Results.ONE_PAIR,     1098240F/possibleFiveCardHands);
    }

    private static final int HANDS = 50;

    public static void main(String[] args) {

        // Evaluate the player’s hand, informing them of the highest ranked poker hand that matches their hand of 5 cards.
        Map <Results, Integer> outputResultsCountMap = new HashMap<>();
        for (int i = 0; i < HANDS; i++) {
            Deck deck = new Deck();
            deck.mixTheCardsInTheDeck();
            Hand hand = new Hand(deck.dealHand(5));

            Results outputResults = Results.NOTHING;

            if (hand.isFullHouse()) {
                outputResults = Results.FULL_HOUSE;
            } else if (hand.isFourOfAKind()) {
                outputResults = Results.FOUR_OF_A_KIND;
            } else if (hand.isThreeOfAKind()) {
                outputResults = Results.THREE_OF_A_KIND;
            } else if (hand.isOnePair()) {
                outputResults = Results.ONE_PAIR;
            } else if (hand.isTwoPair()) {
                outputResults = Results.TWO_PAIR;
            } else {
                if (hand.isStraightFlush()) {
                    outputResults = Results.STRAIGHT_FLUSH;
                } else if (hand.isFlush()) {
                    outputResults = Results.FLUSH;
                } else if (hand.isStraight()) {
                    outputResults = Results.STRAIGHT;
                }

                if (hand.hasAces()) {
                    for (Card card: hand.getCards()) {
                        if (card.isAce()) {
                            card.swapTheRankOfHowAceIsPlayed();
                        }
                    }

                    hand.order();

                    if (hand.isStraightFlush()) {
                        outputResults = Results.STRAIGHT_FLUSH;
                    } else if (hand.isStraight()) {
                        outputResults = Results.STRAIGHT;
                    }
                }
            }
            incrementResultsCount(outputResults, outputResultsCountMap);
        }
        printResultsToConsole(outputResultsCountMap);
    }

    // The highest ranked poker hand that matches a player's hand of 5 cards
    private static void printResultsToConsole(Map<Results, Integer> outputResultsCountMap) {
        System.out.println("Highest ranked poker hand that matches your hand of 5 cards: ");
        float totalActualProbabilities = 0F;
        for (Results outputResults:  Results.values()) {
            String outputResultsLabel = outputResults.getLabel();
            Integer outputResultsCount = outputResultsCountMap.get(outputResults);

            if (outputResultsCount == null) {
                outputResultsCount = 0;
            }

            float actualProbability = ((float) outputResultsCount) / (float) HANDS;
            totalActualProbabilities += actualProbability;

            if (outputResults != Results.NOTHING) {
                float expectedProbability = expectedResults.get(outputResults);
                float deviation = (actualProbability - expectedProbability) / expectedProbability;
                System.out.println(outputResultsLabel + "\t\t:   " + outputResults + " of  " + HANDS + ":  actual= " + 100 * actualProbability + "%    "
                        + "expected= " + 100 * expectedProbability + "%   deviation= " + 100 * deviation + "%");
            }

            else {
                System.out.println(outputResultsLabel + "\t\t\t:   " + outputResults + " of  " + HANDS + ":  actual= " + 100 * actualProbability + "%    ");
            }
        }
        System.out.println("\n");

        System.out.printf("RANK OF THE SUITS:\n" + "1. \u2660 are the 1st highest suit.\n" + "2. \u2663 are the 2nd highest suit.\n"
                + "3. \u2666 are the 3rd highest suit.\n" + "4. \u2663 are the 4th highest suit.");
    }

    private static void incrementResultsCount(Results outputResults, Map<Results, Integer> outputResultsCountMap) {
        Integer count = outputResultsCountMap.get(outputResults);
        if (count == null) {
            outputResultsCountMap.put(outputResults, 1);
        }
        else {
            outputResultsCountMap.put(outputResults, ++count);
        }
    }
}