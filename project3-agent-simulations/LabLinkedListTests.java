/*
file name:      LabLinkedListTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea LabLinkedListTests
*/

public class LabLinkedListTests {

    public static void main(String[] args) {

        // Validates constructor creates empty list
        // case 1: testing LinkedList()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();

            // verify
            System.out.println(ll + " != null");

            // test
            assert ll != null : "Error in LinkedList::LinkedList()";
        }

        // Confirms push-front growth updates size
        // case 2: testing add(T item)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(i);
            }

            // verify
            System.out.println(ll.size() + " == 5");

            // test
            assert ll.size() == 5 : "Error in LinkedList::add(T item) or LinkedList::size()";
        }

        // Provides visual confirmation of order
        // case 2.5: sanity checking toString()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(i);
            }

            // verify
            System.out.println("\nIf your toString is working, you should see a list that starts at 4 and goes down to 0.");
            System.out.println("I.e. it should look like this, possibly with small changes to formatting: ");
            System.out.println("Example ll:\t 4, 3, 2, 1, 0");
            System.out.println("Your ll:\t " + ll + "\n");
        }

        // Ensures positional access returns expected
        // case 3: testing get(int)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }

            // verify
            System.out.println(ll.get(0) + " == 0");
            System.out.println(ll.get(3) + " == 3");
            System.out.println(ll.get(4) + " == 4");

            // test
            assert ll.get(0) == 0 : "Error in LinkedList::get(int)";
            assert ll.get(3) == 3 : "Error in LinkedList::get(int)";
            assert ll.get(4) == 4 : "Error in LinkedList::get(int)";
        }

        // Verifies indexed insert maintains order
        // case 4: testing add(int, T)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 4);
            ll.add(1, 2);
            ll.add(0, 0);
            ll.add(4, 5);
            ll.add(3, 3);

            // verify
            for (int i = 0; i < ll.size(); i++) {
                System.out.println(ll.get(i) + " == " + i);
            }

            // test
            for (int i = 0; i < ll.size(); i++) {
                assert ll.get(i) == i : "Error in LinkedList::add(int, T) or LinkedList::get(int)";
            }
        }

        // Checks membership detection for matches
        // case 5: testing contains(Object)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 3; i++) {
                ll.add(2 * i);
            }

            // verify
            System.out.println(ll.contains(0) + " == true");
            System.out.println(ll.contains(4) + " == true");
            System.out.println(ll.contains(3) + " == false");

            // test
            assert ll.contains(0) : "Error in LinkedList::contains(Object)";
            assert ll.contains(4) : "Error in LinkedList::contains(Object)";
            assert !ll.contains(3) : "Error in LinkedList::contains(Object)";
        }

        // Confirms head removals pop correctly
        // case 6: testing remove()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }

            int remove0 = ll.remove();
            int remove1 = ll.remove();

            // verify
            System.out.println(remove0 + " == 0");
            System.out.println(remove1 + " == 1");

            // test
            assert remove0 == 0 : "Error in LinkedList::remove()";
            assert remove1 == 1 : "Error in LinkedList::remove()";
        }

        System.out.println("\n\nCongrats! You passed the lab checkpoint!\n\n");
    }
}
