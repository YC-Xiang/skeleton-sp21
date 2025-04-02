package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {
        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void getTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();

        // Test get on empty deque
        assertNull("Should return null when get is called on an empty Deque", lld.get(0));

        // Add elements and test get
        for (int i = 0; i < 10; i++) {
            lld.addLast(i);
        }

        // Test valid indices
        for (int i = 0; i < 10; i++) {
            assertEquals("Get should return correct item at index " + i, Integer.valueOf(i), lld.get(i));
        }

        // Test invalid index
        assertNull("Should return null for out of bounds index", lld.get(10));
    }

    @Test
    public void getRecursiveTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();

        // Add elements and test getRecursive
        for (int i = 0; i < 5; i++) {
            lld.addLast(i);
        }

        // Test valid indices with recursive implementation
        for (int i = 0; i < 5; i++) {
            assertEquals("getRecursive should return correct item at index " + i,
                    Integer.valueOf(i), lld.getRecursive(i));
        }
    }

    @Test
    public void addFirstLastTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();

        // Test adding elements to both ends
        for (int i = 0; i < 5; i++) {
            lld.addFirst(i);        // [4, 3, 2, 1, 0]
            lld.addLast(i + 10);    // [4, 3, 2, 1, 0, 10, 11, 12, 13, 14]
        }

        assertEquals("Deque should have 10 items", 10, lld.size());

        // Check order by removing from both ends
        for (int i = 0; i < 5; i++) {
            assertEquals("removeFirst should return items in correct order",
                    Integer.valueOf(4 - i), lld.removeFirst());
            assertEquals("removeLast should return items in correct order",
                    Integer.valueOf(14 - i), lld.removeLast());
        }

        assertTrue("Deque should be empty after removing all items", lld.isEmpty());
    }

    @Test
    public void removeFromEmptyTest() {
        LinkedListDeque<String> lld = new LinkedListDeque<>();

        // Test remove from empty deque
        assertNull("removeFirst on empty deque should return null", lld.removeFirst());
        assertNull("removeLast on empty deque should return null", lld.removeLast());

        // Add and remove to get back to empty state
        lld.addFirst("first");
        lld.removeFirst();

        assertNull("removeFirst on empty deque should return null", lld.removeFirst());
        assertNull("removeLast on empty deque should return null", lld.removeLast());
    }

    @Test
    public void iteratorTest() {
        LinkedListDeque<String> lld = new LinkedListDeque<>();
        lld.addLast("hello");
        lld.addLast("world");
        lld.addLast("!");
        for (String i : lld) {
            System.out.println(i);
        }
    }

    @Test
    public void equalsTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addLast(1);
        lld2.addLast(3);
        lld2.addLast(2);

        assertFalse(lld1.equals(lld2));

        lld2.removeLast();
        lld2.removeLast();
        lld2.addLast(2);
        lld2.addLast(3);

        assertTrue(lld1.equals(lld2));
    }

    @Test
    public void equalsTest2() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);

        assertTrue(lld.equals(ad));
    }
}
