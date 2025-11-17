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
            int unique = wc.uniqueWordCount();
            System.out.println(total + " == 1");
            System.out.println(count + " == 1");
            System.out.println(unique + " == 1");

            // THEN
            assert total == 1 : "Error in WordCounter::totalWordCount() after add";
            assert count == 1 : "Error in WordCounter::getCount() after add";
            assert unique == 1 : "Error in WordCounter::uniqueWordCount() after add";
        }

        // CASE 3: multiple words and missing words
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
            int unique = wc.uniqueWordCount();
            System.out.println(total + " == 3");
            System.out.println(countApple + " == 2");
            System.out.println(countBanana + " == 1");
            System.out.println(countCherry + " == 0");
            System.out.println(unique + " == 2");

            // THEN
            assert total == 3 : "Error in WordCounter::totalWordCount() multiple words";
            assert countApple == 2 : "Error in WordCounter::getCount() apple";
            assert countBanana == 1 : "Error in WordCounter::getCount() banana";
            assert countCherry == 0 : "Error in WordCounter::getCount() missing word";
            assert unique == 2 : "Error in WordCounter::uniqueWordCount()";
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
            int countApple = wc.getCount("apple");
            System.out.println(total + " == 0");
            System.out.println(unique + " == 0");
            System.out.println(countApple + " == 0");

            // THEN
            assert total == 0 : "Error in WordCounter::clearMap() totalWordCount";
            assert unique == 0 : "Error in WordCounter::clearMap() uniqueWordCount";
            assert countApple == 0 : "Error in WordCounter::clearMap() getCount after clear";
        }

        // CASE 5: getFrequency with multiple words (BST backend)
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words = new ArrayList<>();
            words.add("apple");
            words.add("banana");
            words.add("apple");
            wc.buildMap(words); // counts: apple=2, banana=1, total=3

            // WHEN
            double freqApple = wc.getFrequency("apple");
            double freqBanana = wc.getFrequency("banana");
            double freqCherry = wc.getFrequency("cherry");
            System.out.println("freqApple = " + freqApple + " ~= 0.666...");
            System.out.println("freqBanana = " + freqBanana + " ~= 0.333...");
            System.out.println("freqCherry = " + freqCherry + " == 0.0");

            // THEN
            assert Math.abs(freqApple - (2.0 / 3.0)) < 1e-6 : "Error in WordCounter::getFrequency() apple";
            assert Math.abs(freqBanana - (1.0 / 3.0)) < 1e-6 : "Error in WordCounter::getFrequency() banana";
            assert freqCherry == 0.0 : "Error in WordCounter::getFrequency() missing word should be 0.0";
        }

        // CASE 6: HashMap backend behaves like BST backend
        {
            // GIVEN
            ArrayList<String> words = new ArrayList<>();
            words.add("dog");
            words.add("cat");
            words.add("dog");
            words.add("dog");

            WordCounter wcBST = new WordCounter("BST");
            WordCounter wcHash = new WordCounter("HashMap");

            // WHEN
            wcBST.buildMap(words);
            wcHash.buildMap(words);

            int totalBST = wcBST.totalWordCount();
            int totalHash = wcHash.totalWordCount();

            int countDogBST = wcBST.getCount("dog");
            int countDogHash = wcHash.getCount("dog");

            int uniqueBST = wcBST.uniqueWordCount();
            int uniqueHash = wcHash.uniqueWordCount();

            double freqDogBST = wcBST.getFrequency("dog");
            double freqDogHash = wcHash.getFrequency("dog");

            System.out.println("BST total = " + totalBST + ", Hash total = " + totalHash);
            System.out.println("BST countDog = " + countDogBST + ", Hash countDog = " + countDogHash);
            System.out.println("BST unique = " + uniqueBST + ", Hash unique = " + uniqueHash);
            System.out.println("BST freqDog = " + freqDogBST + ", Hash freqDog = " + freqDogHash);

            // THEN
            assert totalBST == totalHash : "Mismatch totalWordCount() between BST and HashMap";
            assert countDogBST == countDogHash : "Mismatch getCount() for 'dog' between BST and HashMap";
            assert uniqueBST == uniqueHash : "Mismatch uniqueWordCount() between BST and HashMap";
            assert Math.abs(freqDogBST - freqDogHash) < 1e-9 : "Mismatch getFrequency() for 'dog' between BST and HashMap";
        }

        // CASE 7: reuse after clearMap
        {
            // GIVEN
            WordCounter wc = new WordCounter("BST");
            ArrayList<String> words1 = new ArrayList<>();
            words1.add("one");
            words1.add("one");
            wc.buildMap(words1);

            // WHEN
            wc.clearMap();
            ArrayList<String> words2 = new ArrayList<>();
            words2.add("two");
            wc.buildMap(words2);

            int total = wc.totalWordCount();
            int countOne = wc.getCount("one");
            int countTwo = wc.getCount("two");
            int unique = wc.uniqueWordCount();

            System.out.println("total = " + total + " == 1");
            System.out.println("countOne = " + countOne + " == 0");
            System.out.println("countTwo = " + countTwo + " == 1");
            System.out.println("unique = " + unique + " == 1");

            // THEN
            assert total == 1 : "Error in WordCounter reuse: totalWordCount() not reset correctly";
            assert countOne == 0 : "Error in WordCounter reuse: old words not cleared";
            assert countTwo == 1 : "Error in WordCounter reuse: new word count incorrect";
            assert unique == 1 : "Error in WordCounter reuse: uniqueWordCount() incorrect";
        }

        System.out.println("*** Done testing WordCounter! ***\n");
    }

    public static void main(String[] args) {
        wordCounterTests();
    }
}
