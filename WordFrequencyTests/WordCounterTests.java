/*
file name:      WordCounterTests.java
Authors:        Samuel
last modified:  11/14/2025

How to run:     java -ea WordCounterTests
*/

import java.util.ArrayList;

public class WordCounterTests {

    public static void wordCounterTests() {

        // CASE 1: empty WordCounter
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");

            // WHEN
            int total = wc.totalWordCount();
            int unique = wc.uniqueWordCount();
            System.out.println(total + " == 0");
            System.out.println(unique + " == 0");

            // THEN
            assert total == 0 : "Error in WordCounter::totalWordCount() for empty";
            assert unique == 0 : "Error in WordCounter::uniqueWordCount() for empty";
        }

        // CASE 2: add one word
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words = new ArrayList<>();
            words.add("apple");

            // WHEN
            wc.buildMap(words);
            int total = wc.totalWordCount();
            int count = wc.getCount("apple");
            System.out.println(total + " == 1");
            System.out.println(count + " == 1");

            // THEN
            assert total == 1 : "Error in WordCounter::totalWordCount() after add";
            assert count == 1 : "Error in WordCounter::getCount() after add";
        }

        // CASE 3: multiple words
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words = new ArrayList<>();
            words.add("apple");
            words.add("banana");
            words.add("apple");

            // WHEN
            wc.buildMap(words);
            int total = wc.totalWordCount();
            int countApple = wc.getCount("apple");
            int countBanana = wc.getCount("banana");
            int countCherry = wc.getCount("cherry");
            System.out.println(total + " == 3");
            System.out.println(countApple + " == 2");
            System.out.println(countBanana + " == 1");
            System.out.println(countCherry + " == 0");

            // THEN
            assert total == 3 : "Error in WordCounter::totalWordCount() multiple words";
            assert countApple == 2 : "Error in WordCounter::getCount() apple";
            assert countBanana == 1 : "Error in WordCounter::getCount() banana";
            assert countCherry == 0 : "Error in WordCounter::getCount() missing word";
        }

        // CASE 4: clearMap
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words = new ArrayList<>();
            words.add("apple");
            wc.buildMap(words);

            // WHEN
            wc.clearMap();
            int total = wc.totalWordCount();
            int unique = wc.uniqueWordCount();
            System.out.println(total + " == 0");
            System.out.println(unique + " == 0");

            // THEN
            assert total == 0 : "Error in WordCounter::clearMap() totalWordCount";
            assert unique == 0 : "Error in WordCounter::clearMap() uniqueWordCount";
        }

        // CASE 5: getFrequency
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words = new ArrayList<>();
            words.add("apple");
            words.add("banana");
            words.add("apple");
            wc.buildMap(words);

            // WHEN
            int freqApple = wc.getFrequency("apple");
            int freqBanana = wc.getFrequency("banana");
            int freqCherry = wc.getFrequency("cherry");
            System.out.println(freqApple + " == 0 (integer division)");
            System.out.println(freqBanana + " == 0");
            System.out.println(freqCherry + " == 0");

            // THEN
            assert freqApple == 0 : "Error in WordCounter::getFrequency() apple";
            assert freqBanana == 0 : "Error in WordCounter::getFrequency() banana";
            assert freqCherry == 0 : "Error in WordCounter::getFrequency() missing word";
        }

        System.out.println("*** Done testing WordCounter! ***\n");
    }

    public static void main(String[] args) {
        wordCounterTests();
    }
}
