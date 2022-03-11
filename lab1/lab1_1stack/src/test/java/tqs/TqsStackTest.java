package tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    TqsStack<String> tqsStack;

    @BeforeEach
    void setup() {
        tqsStack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction")
    @Test
    void emptyOnConst() {
        assertTrue(tqsStack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    void size0onConst() {
        assertEquals(0, tqsStack.size());
    }

    @DisplayName("After n>0 pushes to an empty stack, stack is not empty and its size is n")
    @Test
    void push() {
        tqsStack.push("first");
        tqsStack.push("second");

        assertAll(
                () -> assertEquals(2, tqsStack.size()),
                () -> assertFalse(tqsStack.isEmpty())
        );
    }

    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    void pop() {
        tqsStack.push("first");
        tqsStack.push("second");
        assertEquals("second", tqsStack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value return is x, but size stays the same")
    @Test
    void peek() {
        tqsStack.push("first");
        tqsStack.push("second");
        assertAll(
                () -> assertEquals("second", tqsStack.peek()),
                () -> assertEquals(2, tqsStack.size())
        );
    }

    @DisplayName("If the size is n, then after n pops the stack is empty and has size 0")
    @Test
    void popAll() {
        tqsStack.push("first");
        tqsStack.push("second");
        tqsStack.push("third");

        tqsStack.pop();
        tqsStack.pop();
        tqsStack.pop();

        assertAll(
                () -> assertTrue(tqsStack.isEmpty()),
                () -> assertEquals(0, tqsStack.size())
        );
    }

    @DisplayName("Popping from an empty stack throws a NoSuchElementException")
    @Test
    void popEmpty() {
        assertThrows(NoSuchElementException.class, () -> tqsStack.pop());
    }

    @DisplayName("Peeking into an empty stack throws a NoSuchElementException")
    @Test
    void peekEmpty() {
        assertThrows(NoSuchElementException.class, () -> tqsStack.peek());
    }

    @DisplayName("For bounded stacks only, pushing to a full stack throws an IllegalStateException")
    @Test
    void pushBounded() {
    }
}