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
            int value = stack.pop();
            System.out.println("Popped value: " + value);

            // then
            assert value == 3 : "Error in Stack::pop()";
            assert stack.size() == 2 : "Error in Stack::pop() size check";
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
            System.out.println("Peeked value: " + value);

            // then
            assert value == 3 : "Error in Stack::peek()";
            assert stack.size() == 3 : "Error in Stack::peek() size check";
        }
    }
}
