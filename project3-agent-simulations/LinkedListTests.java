/*
file name:      LinkedListTests.java
Authors:        Rishit Chatterjee, Rana Moeez Hassan 
last modified:  11/17/2025

How to run:     java -ea LinkedListTests
*/

public class LinkedListTests {

    public static void main(String[] args) {

        // Validates constructor creates empty list
        // case 1: testing LinkedList()
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            System.out.println(ll + " != null");
            assert ll != null : "Error in LinkedList::LinkedList()";
        }

        // Confirms push-front growth updates size
        // case 2: testing add(T item)
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(i);
            }
            System.out.println(ll.size() + " == 5");
            assert ll.size() == 5 : "Error in LinkedList::add(T item) or LinkedList::size()";
        }

        // Provides visual confirmation of order
        // case 2.5: sanity checking toString()
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(i);
            }
            System.out.println("\nIf your toString is working, you should see a list that starts at 4 and goes down to 0.");
            System.out.println("I.e. it should look like this, possibly with small changes to formatting: ");
            System.out.println("Example ll:\t 4, 3, 2, 1, 0");
            System.out.println("Your ll:\t " + ll + "\n");
        }

        // Ensures positional insert respects size
        // case 3: testing add(int, T)
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 2);
            ll.add(1, 3);
            ll.add(0, 4);
            ll.add(4, 5);
            ll.add(3, 6);
            System.out.println(ll.size() + " == 6");
            assert ll.size() == 6 : "Error in LinkedList::add(int, T) or LinkedList::size()";
            for (int i = 0; i < ll.size(); i++) {
                System.out.println(ll.get(i) + " == " + i);
                assert ll.get(i) == i : "Error in LinkedList::add(int, T) or LinkedList::get(int)";
            }
        }

        // Verifies indexed insert maintains order
        // case 4: testing add(int, T) with order verification
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 4);
            ll.add(1, 2);
            ll.add(0, 0);
            ll.add(4, 5);
            ll.add(3, 3);

            for (int i = 0; i < ll.size(); i++) {
                System.out.println(ll.get(i) + " == " + i);
            }

            for (int i = 0; i < ll.size(); i++) {
                assert ll.get(i) == i : "Error in LinkedList::add(int, T) or LinkedList::get(int)";
            }
        }

        // Confirms clear empties nodes entirely
        // case 5: testing clear()
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int value : new int[]{1, 2, 3}) {
                ll.add(value);
            }
            ll.clear();
            System.out.println(ll.size() + " == 0");
            assert ll.size() == 0 : "Error in LinkedList::clear()";
            assert ll.isEmpty() : "Error in LinkedList::clear() or LinkedList::isEmpty()";
        }

        // Checks membership detection for matches
        // case 6: testing contains(Object)
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 3; i++) {
                ll.add(2 * i);
            }
            System.out.println(ll.contains(0) + " == true");
            System.out.println(ll.contains(4) + " == true");
            System.out.println(ll.contains(3) + " == false");
            assert ll.contains(0) : "Error in LinkedList::contains(Object)";
            assert ll.contains(4) : "Error in LinkedList::contains(Object)";
            assert !ll.contains(3) : "Error in LinkedList::contains(Object)";
        }

        // Validates equals semantics against variants
        // case 7: testing equals()
        {
            LinkedList<Integer> list1 = new LinkedList<Integer>();
            LinkedList<Integer> list2 = new LinkedList<Integer>();
            LinkedList<Integer> list3 = new LinkedList<Integer>();
            LinkedList<Integer> list4 = new LinkedList<Integer>();

            for (int i = 0; i < 3; i++) {
                list1.add(i);
                list2.add(i);
                list3.add(i);
                list4.add(i);
            }
            list3.add(4);
            list4.add(5);

            System.out.println(list1.equals(list2) + " == true");
            System.out.println(list2.equals(list3) + " == false");
            System.out.println(list3.equals(list4) + " == false");
            System.out.println(list4.equals("Hello") + " == false");

            assert list1.equals(list2) : "Error in LinkedList::equals(Object)";
            assert !list2.equals(list3) : "Error in LinkedList::equals(Object)";
            assert !list3.equals(list4) : "Error in LinkedList::equals(Object)";
            assert !list4.equals("Hello") : "Error in LinkedList::equals(Object)";
        }

        // Verifies random access retrieval accuracy
        // case 8: testing get(int)
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }
            System.out.println(ll.get(0) + " == 0");
            System.out.println(ll.get(3) + " == 3");
            System.out.println(ll.get(4) + " == 4");
            assert ll.get(0) == 0 : "Error in LinkedList::get(int)";
            assert ll.get(3) == 3 : "Error in LinkedList::get(int)";
            assert ll.get(4) == 4 : "Error in LinkedList::get(int)";
        }

        // Confirms emptiness toggles appropriately
        // case 9: testing isEmpty()
        {
            LinkedList<Integer> list1 = new LinkedList<Integer>();
            LinkedList<Integer> list2 = new LinkedList<Integer>();
            list2.add(5);
            System.out.println(list1.isEmpty() + " == true");
            System.out.println(list2.isEmpty() + " == false");
            assert list1.isEmpty() : "Error in LinkedList::isEmpty()";
            assert !list2.isEmpty() : "Error in LinkedList::isEmpty()";
        }

        // Ensures head removal returns front
        // case 10: testing remove()
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }
            int remove0 = ll.remove();
            int remove1 = ll.remove();
            System.out.println(remove0 + " == 0");
            System.out.println(remove1 + " == 1");
            assert remove0 == 0 : "Error in LinkedList::remove()";
            assert remove1 == 1 : "Error in LinkedList::remove()";
        }

        // Validates indexed removal re-links properly
        // case 11: testing remove(int)
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 8; i++) {
                ll.add(7 - i);
            }
            int remove0 = ll.remove(0);
            int remove3 = ll.remove(3);
            int remove5 = ll.remove(5);
            System.out.println(remove0 + " == 0");
            System.out.println(remove3 + " == 4");
            System.out.println(remove5 + " == 7");
            assert remove0 == 0 : "Error in LinkedList::remove(int)";
            assert remove3 == 4 : "Error in LinkedList::remove(int)";
            assert remove5 == 7 : "Error in LinkedList::remove(int)";
        }

        // Confirms iterator covers contiguous sequence
        // case 12: testing iterator()
        {
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 4);
            ll.add(1, 2);
            ll.add(0, 0);
            ll.add(4, 5);
            ll.add(3, 3);

            int counter = 0;
            for (int val : ll) {
                System.out.println(val + " == " + counter);
                assert val == counter : "Error in LinkedList::add(int, T) or LinkedList::iterator()";
                counter++;
            }
            assert counter == ll.size() : "Error: iterator skipped elements";
        }

        System.out.println("\n\nCongrats! You passed the lab checkpoint and full suite!\n\n");
    }
}
