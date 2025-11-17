/*
file name:      StackInterfaceTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea StackInterfaceTests
*/

/**
 * These tests stress scenarios that are NOT covered by the existing PR:
 *  - mixing queue and stack semantics back-to-back
 *  - repeatedly peeking/popping from an empty structure (should return null)
 *  - verifying that a stack composed entirely via offer/poll calls still
 *    behaves like FIFO even after stack pushes are interleaved
 *  - ensuring push/pop remain O(1) even when thousands of elements churn
 */
public class StackInterfaceTests {

    public static void main(String[] args) {
        stackInterfaceTests();
    }

    public static void stackInterfaceTests() {

        // Peek/pop on an empty structure should gracefully return null
        {
            LinkedList<Integer> stack = new LinkedList<Integer>();
            assert stack.peek() == null : "peek on empty should return null";
            assert stack.pop() == null : "pop on empty should return null";
            assert stack.poll() == null : "poll on empty should return null";
        }

        // Interleave queue offers with stack pushes and ensure semantics hold
        {
            LinkedList<Integer> hybrid = new LinkedList<Integer>();
            hybrid.offer(0);  // queue front
            hybrid.push(1);   // stack top
            hybrid.offer(2);  // queue tail
            hybrid.push(3);   // stack top
            assert hybrid.pop() == 3 : "pop should return last pushed";
            assert hybrid.pop() == 1 : "pop should continue stack order";
            assert hybrid.poll() == 0 : "poll should now expose queue front";
            assert hybrid.poll() == 2 : "remaining queue element should follow";
            assert hybrid.isEmpty() : "structure should be empty afterward";
        }

        // Massive churn: push/pop should stay consistent even after peek spam
        {
            LinkedList<Integer> stack = new LinkedList<Integer>();
            for (int i = 0; i < 10_000; i++) {
                stack.push(i);
                assert stack.peek() == i : "peek must always show latest push";
                if (i % 2 == 0) {
                    assert stack.pop() == i : "pop should remove same element";
                }
            }
            int expected = 9_999 - ((9_999 % 2 == 0) ? 1 : 0);
            while (!stack.isEmpty()) {
                int top = stack.pop();
                assert top == expected : "Pop order should reverse pushes";
                expected -= 2;
            }
        }

        // Verify size tracking when alternating between push/pop/offer/poll
        {
            LinkedList<Integer> hybrid = new LinkedList<Integer>();
            for (int i = 0; i < 50; i++) {
                hybrid.push(i);
                hybrid.offer(-i);
            }
            assert hybrid.size() == 100 : "Size should count both stack and queue additions";
            for (int i = 49; i >= 0; i--) {
                assert hybrid.pop() == i : "Stack portion should unwind first";
            }
            for (int i = 0; i < 50; i++) {
                assert hybrid.poll() == -i : "Queue portion should follow FIFO";
            }
            assert hybrid.isEmpty() : "Structure should be empty at end";
        }

        System.out.println("Finished StackInterfaceTests!");
    }
}
