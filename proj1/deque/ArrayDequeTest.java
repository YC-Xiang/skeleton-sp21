package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addRemoveIsEmptySizeTest() {
        ArrayDeque<String> arrayDeque = new ArrayDeque<String>();

        assertTrue(arrayDeque.isEmpty());
        assertEquals(0, arrayDeque.size());
        arrayDeque.addFirst("b");
        arrayDeque.addFirst("a");
        arrayDeque.addLast("c");
        arrayDeque.addLast("d");
        arrayDeque.printDeque();
        assertEquals(4, arrayDeque.size());
        String a = arrayDeque.removeFirst();
        String d = arrayDeque.removeLast();
        assertEquals("a", a);
        assertEquals("d", d);
        assertEquals(2, arrayDeque.size());
        assertFalse(arrayDeque.isEmpty());

    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>();
        for (int i = 0; i < 20; i ++) { // 0-39
            arrayDeque.addFirst(19-i);
            arrayDeque.addLast(20+i);
        }

        for (int i = 0; i < 20; i ++) {
            int item = arrayDeque.removeFirst();
            int item1 = arrayDeque.removeLast();
            assertEquals(item, i);
            assertEquals(item1, 39-i);
        }
    }

    @Test
    public void randomTest() {
        ArrayDeque<Integer> L = new ArrayDeque<Integer>();
        java.util.ArrayDeque<Integer> javaDeque = new java.util.ArrayDeque<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) { // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                javaDeque.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) { // addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                javaDeque.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");
            } else if (operationNumber == 2) { // removeFirst
                if (!L.isEmpty()) {
                    int Item = L.removeFirst();
                    int Item1 = javaDeque.removeFirst();
                    assertEquals(Item, Item1);
                    System.out.println("removeFirst(" + Item + ")");
                }
            } else if (operationNumber == 3) { // removeLast
                if (!L.isEmpty()) {
                    int Item = L.removeLast();
                    int Item1 = javaDeque.removeLast();
                    assertEquals(Item, Item1);
                    System.out.println("removeLast(" + Item + ")");
                }
            } else if (operationNumber == 4) { // size
                int Size = L.size();
                int Size1= javaDeque.size();
                System.out.println("size: " + Size);
                assertEquals(Size, Size1);
            }
        }
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        // Test get on empty deque
        assertNull(ad.get(0));

        // Add elements and test get
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }

        // Test valid indices
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(i), ad.get(i));
        }

        // Test invalid index
        assertNull(ad.get(10));
        assertNull(ad.get(-1));
    }

    @Test
    public void emptyDequeOperationsTest() {
        ArrayDeque<String> ad = new ArrayDeque<>();

        // Test remove operations on empty deque
        assertNull(ad.removeFirst());
        assertNull(ad.removeLast());
        assertTrue(ad.isEmpty());
        assertEquals(0, ad.size());
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addLast("hello");
        ad.addLast("world");
        ad.addLast("!");
        for (String i : ad) {
            System.out.println(i);
        }
    }


    @Test
    public void equalsTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();

        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);

        ad2.addLast(1);
        ad2.addLast(3);
        ad2.addLast(2);

        assertFalse(ad1.equals(ad2));

        ad2.removeLast();
        ad2.removeLast();
        ad2.addLast(2);
        ad2.addLast(3);

        assertTrue(ad1.equals(ad2));
    }

    @Test
    public void equalsTest2() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);

        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);

        assertTrue(ad.equals(lld));
    }
}
