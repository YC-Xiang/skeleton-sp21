package deque;

import java.util.Comparator;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void testMaxWithDefaultComparator() {
        Comparator<Integer> intComparator = Integer::compare;
        MaxArrayDeque<Integer> maxDeque = new MaxArrayDeque<>(intComparator);

        // Test with empty deque
        assertNull("Max of empty deque should be null", maxDeque.max());

        // Test with one element
        maxDeque.addFirst(10);
        assertEquals(Integer.valueOf(10), maxDeque.max());

        // Test with multiple elements
        maxDeque.addLast(5);
        maxDeque.addLast(20);
        maxDeque.addLast(15);
        assertEquals(Integer.valueOf(20), maxDeque.max());

        // Test after removing max element
        maxDeque.removeFirst(); // Removes 10
        maxDeque.removeFirst(); // Removes 5
        assertEquals(Integer.valueOf(20), maxDeque.max());

        // Remove max and test new max
        maxDeque.removeFirst(); // Removes 20
        assertEquals(Integer.valueOf(15), maxDeque.max());
    }

    @Test
    public void testMaxWithCustomComparator() {
        // Default comparator (ascending)
        Comparator<Integer> ascendingComparator = Integer::compare;
        MaxArrayDeque<Integer> maxDeque = new MaxArrayDeque<>(ascendingComparator);

        // Add elements
        maxDeque.addLast(30);
        maxDeque.addLast(10);
        maxDeque.addLast(50);
        maxDeque.addLast(40);

        // Default comparator should find maximum
        assertEquals(Integer.valueOf(50), maxDeque.max());

        // Custom comparator (descending) - should find minimum
        Comparator<Integer> descendingComparator = (a, b) -> Integer.compare(b, a);
        assertEquals(Integer.valueOf(10), maxDeque.max(descendingComparator));

        // Custom absolute value comparator - should find maximum absolute value
        Comparator<Integer> absComparator = Comparator.comparingInt(Math::abs);

        MaxArrayDeque<Integer> absDeque = new MaxArrayDeque<>(absComparator);
        absDeque.addLast(-100);
        absDeque.addLast(50);
        absDeque.addLast(25);
        absDeque.addLast(-75);

        assertEquals(Integer.valueOf(-100), absDeque.max());
    }

    @Test
    public void testWithStringComparator() {
        // String length comparator
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        MaxArrayDeque<String> stringDeque = new MaxArrayDeque<>(lengthComparator);

        stringDeque.addLast("a");
        stringDeque.addLast("bb");
        stringDeque.addLast("ccc");
        stringDeque.addLast("d");

        // Default comparator (by length)
        assertEquals("ccc", stringDeque.max());

        // Alphabetical comparator
        Comparator<String> alphabeticalComparator = String::compareTo;
        assertEquals("d", stringDeque.max(alphabeticalComparator));
    }

    @Test
    public void testEmptyDeque() {
        Comparator<Integer> intComparator = Integer::compare;
        MaxArrayDeque<Integer> emptyDeque = new MaxArrayDeque<>(intComparator);

        // Test max() with default comparator
        assertNull(emptyDeque.max());

        // Test max() with custom comparator
        Comparator<Integer> customComparator = (a, b) -> Integer.compare(b, a);
        assertNull(emptyDeque.max(customComparator));
    }
}
