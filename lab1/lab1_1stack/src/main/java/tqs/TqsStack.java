package tqs;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TqsStack<T> {

    Stack<T> stack;

    public TqsStack() {
        stack = new Stack<T>();
    }

    public void push(T value) {
        stack.push(value);
    }

    public T pop() {
        try {
            return stack.pop();
        }
        catch (EmptyStackException e) {
            throw new NoSuchElementException();
        }
    }

    public T peek() {
        try {
            return stack.peek();
        }
        catch (EmptyStackException e) {
            throw new NoSuchElementException();
        }
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
