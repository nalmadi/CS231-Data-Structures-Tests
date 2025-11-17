import java.util.ArrayList;

public class StackTests {
    
    public static void StackTests() {
        // case 1: testing Stack()
        {
            // given
            Stack<Integer> stack = new Stack<>();

            // when
            System.out.println(stack);

            // then
            assert stack != null : "Error in Stack::Stack()";
        }

        // case 2: testing push()
        {
            // given
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);

            // when
            System.out.println(stack);

            // then
            assert stack.size() == 3 : "Error in Stack::push()";
        }

        // case 3: testing pop()
        {
            // given
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);

            // when
            int value3 = stack.pop();
            int size1 = stack.size();
            int value2 = stack.pop();
            int value = stack.pop();    
            System.out.println("First popped value: " + value3);
            System.out.println("Second popped value: " + value2);
            System.out.println("Third popped value: " + value);
            System.out.println("Size after first pop: " + size1);
            System.out.println("Size after all pops: " + stack.size());


            // then
            assert value3 == 3 : "Error in Stack::pop()";
            assert value2 == 2 : "Error in Stack::pop()";
            assert value == 1 : "Error in Stack::pop()";
            assert size1 == 2 : "Error in Stack::pop() size check";
            assert stack.size() == 0 : "Error in Stack::pop() size check";
        }

        // case 4: testing peek()
        {
            // given
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);

            // when
            int value = stack.peek();
            int value2 = stack.peek();
            System.out.println("Peeked value: " + value);
            System.out.println("Peeked value again: " + value2);

            // then
            assert value == 3 : "Error in Stack::peek()";
            assert value2 == 3 : "Error in Stack::peek()";
            assert stack.size() == 3 : "Error in Stack::peek() size check";
        }
    }
}
